# Dagger2 App example in the "Omelette du fromage" langage
Projet Android en Français qui montre un exemple d'utilisation de Dagger2.

## Pourquoi ?
* Pour aider la communauté Française des developpeurs Android à comprendre les concepts complexes que Dagger2 utilise (surtout les Components sur lequel je me suis arraché les cheuveux)
* Parce que la doc offielle est très bien fichue mais qu'il faut s'accorcher pour tout comprendre.
* Pour montrer autre chose que l'hatibuel exemple de la machine à café que tout le monde utilise.
* Pour mettre en valeur cette librairie très performante et très bien fichue (Je suis passé par RoboGuice avant, c'est le jour et la nuit).
* Parce que Dagger2 ne s'utilise pas vraiment comme la plupart des librairies d'IOC. La plupart des développeurs mettent toute leur application dans le même graphe, Dagger2 facilite et pousse la création de multitude de graphes pour découper votre application et cloisoner les instances des unes des autres.

## Avant-propos
Avant de se lancer dans Dagger2, vous devez avant tout chose connaître les principes fondamentaux qui régissent le fonctionnement de l'IOC.

Dans ce projet des mots barbares comme "Injection", "Graphe de données" ou "Binding" sont écrits et si vous se savez pas ce qu'ils signifient, je vous invite à trouver quelques articles qui en parlent.

## L'IOC à la sauce Dagger2
Maintenant que vous connaissez les principes de base de l'IOC ainsi que son mode de fonctionnement, je vais vous expliquer comment cela se traduit dans Dagger2.

Pour commencer, un graphe de données est représenté dans Dagger2 par une interface que vous écrivez et qui est annotée @Component. Un exemple :
```java
@Singleton
@Component(modules = {EndpointModule.class, ManagerModule.class, ApplicationModule.class})
public interface ManagerComponent {
  /* ... */
}
```
Ici je déclare un graphe de donnée nommé ManagerComponent qui utilise 3 modules EndpointModule, ManagerModule et ApplicationModule.

Déclarer un module est assez simple, il suffit de créer une classe et de l'annoter avec @Module :
```java
@Module
public class PostModule {
  /* ... */
}
```
Dans la plupart des libraires d'IOC que l'on peut croiser, le binding interface-implémentation est réalisé dans un fichier XML (Spring IOC il y a quelques années) ou dans le code comme le fait Guice avec la méthode bind(Interface.class, implementation). Dans Dagger2, le binding est un peu plus complexe à mettre en place, vous trouverez un exemple dans la classe ManagerModule.

Voici à quoi ça ressemble :
```java
@Module
public class PostModule {

  @Provides
  @Singleton
  PostManager postManager(PostManagerImpl postManagerImpl) {
    return postManagerImpl;
  }
}
```

Pour ce faire, il faut écrire une méthode dans un module. Cette méthode peut avoir 1 ou plusieurs implémentations en paramètre et en retour l'interface que l'on veut implémenter. Et voila c'est comme ça que l'on défini un binding.
Comme vous pouvez le voir dans cet exemple, j'ai ajouté l'annotation @Singleton qui permet permet à l'instance de PostManager d'être unique dans le graphe de donné qui utilise ce module.

Maintenant que vous avez fait le lien entre les concepts de l'IOC et Dagger2, il est temps d'utiliser nos graphes de données.

## Créer un graph de données
Comme dit plus haut, un graph de données est déclaré par une interface, pour pouvoir créer une instance de ce graph, il faut utiliser les classes que Dagger2 nous a généré :
```java
public class MyApplication extends Application {
  @Override
  public void onCreate() {
    DaggerManagerComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
  }
}
```

Et voila !, le graphe de données est créé, à l'intérieur différents objets sont instanciés (ou attendent de l'être), ils vivent leur vie, ils sont "bindés", "Providés", etc... et vous pouvez en faire ce que vous voulez. Dans notre application d'exemple, ce graph est stocké en tant que membre de MyApplication pour être réutilisé plus tard par le graph MyActivityComponent et MyFragmentComponent.

## S'injecter des membres
Voilà le moment critique, le moment où tout le travail réalisé prend tout son sens, l'injection de dépendance. Rien de bien compliqué, regardez par vous même  : 
```java
@Inject
public PostManagerImpl(PostEndpoint postEndpoint) {
  this.mPostEndpoint = postEndpoint;
}
```
"C'est tout ?" Oui c'est tout ! Créez votre contructeur, ajoutez l'annotation @Inject, ajoutez en paramètre la dépendance que vous souhaitez obtenir et garder là comme membre. Rien de plus simple.

Comme Guice, il est également possible de "nommer" un binding avec une annotation.
Je m'explique, vous avez une interface StringParser qui est implémenté par 2 classes différentes, si vous bindez StringParser avec Implémentation1 et StringParser avec Implémentation2 vous bindez 2 fois l'interface sur 2 implémentation différentes ce qui est problématique ! Dagger2 ne sait pas laquelle choisir et bloquera la compilation de votre projet.
Pour corriger cela, vous pouvez "nommer" les binding avec une annotation que vous aurez vous même écrite. Exemple :
```java
@Provides
@DefaultJson
public StringParser<Post> defaultJsonStringPostParser(DefaultJsonPostParser defaultJsonPostParser) {
  return defaultJsonPostParser;
}

@Provides
@FastJson
public StringParser<Post> fastJsonStringPostParser(FastJsonPostParser postParser) {
  return postParser;
}
```

Ici je binde 2 fois StringParser<Post> mais la première fois avec @DefaultJson, la deuxième avec @FastJson. De ce fait, quand je veux récupérer une instance de StringParser<Post> je dois préciser avec la même annotation quelle implémentation je veux obtenir :
```java
@Inject
public PostEndpointImpl(@DefaultJson Provider<StringParser<Post>> postParserProvider) {
  mPostParserProvider = postParserProvider;
}
```
ou 
```java
@Inject
public PostEndpointImpl(@FastJson Provider<StringParser<Post>> postParserProvider) {
  mPostParserProvider = postParserProvider;
}
```
## Injecter un objet dans un graph après sa création
Le framework Android ne facilite pas toujours les choses, je pense par exemple aux Activity. Le constructeur est appelé automatique par le Framework ce qui fait que l'Activity est créée dans aucun graphe de données.
Or, nous, on voudrais qu'elle soit créée dans un graphe de données afin que l'on puisse s'injecter dans l'Activity des objets. Pour ce faire, il nécessaire d'injecter les membres de l'Activity à a posteriori de l'instanciation de l'Activity.
Pour faire cela il faut déclarer une méthode de ce type :
```java
@Component
public interface MyActivityComponent {
  void inject(MyActivity activity);
}
```
Il faut que la méthode retourne un type void et qu'elle ait un paramètre qui corresponde à la classe de l'objet que l'on veut injecter.
Ensuite déclarez tout les membres de votre classe que vous voulez injecter avec l'annotation @Inject :
```java
@Inject
PostManager mPostManager;
```
Puis il ne reste plus qu'injecter MyActivity, il faut récupérer l'instance du graph préalablement créé (qui peut être stocké dans MyApplication par exemple) puis faire appel à la méthode inject :
```java
component.inject(this);
```

Et voila, votre membre mPostManager contient désormais une instance (à condition que votre graph de donnée déclare un binding de cette interface à une implémentation bien évidement)

NB : Petite limitation, comme c'est Dagger2 qui va mettre une instance dans votre membre, vous ne pouvez pas mettre ce membre en privé, ni protégée, la visibilité doit être au minimum package.

## Structure de l'application
L'application dispose d'un rôle simple : Télécharger des objets "Post" d'un web service, soit de façon unitaire, soit par bloc de 100 puis afficher leur contenu dans une TextView

Pour l'exemple, je me suis imposé des containtes :
* Je veux 1 graph de données qui contient toutes la logique métier (nommé manager) et les DAO dans le même graphe de données en instance unique (Singleton).
* Je veux qu'un graph de données soit créé pour chaque écran et que chaque élément du graph puisse accéder au Context de l'écran. Par exemple, tout les objets créés dans le graph (MyActivityComponent) de MyActivity doivent pour s'injecter le Context de l'Activity.
* Je veux pouvoir changer d'implementation juste en modifiant une annotation.
* Je veux pouvoir changer d'implementation au Runtime.

Voici un schéma (non standardisé) de la structures des graphes, modules et bindings de l'application :
![Structure Dagger2 de l'application](https://raw.githubusercontent.com/VincentMasselis/dagger2-example/master/doc/dagger_architecture.png)
* En bleu : Modules
* En vert : Component
* En jaune : Bindings

## Conclusion
Voilà tout est dit. J'espère que ce projet d'exemple pourra vous aider dans la création d'applications propres et bien structurés.
Mot de la fin : Bon courage.

## Remerciements
Merci à [quentin7b](https://github.com/quentin7b) pour la relecture des commantaires.