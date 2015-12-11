package fr.vincentmasselis.daggersample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.Component;
import fr.vincentmasselis.daggersample.MyApplication;
import fr.vincentmasselis.daggersample.R;
import fr.vincentmasselis.daggersample.manager.ManagerComponent;
import fr.vincentmasselis.daggersample.manager.post.PostManager;
import fr.vincentmasselis.daggersample.ui.utils.UiModule;
import fr.vincentmasselis.daggersample.ui.utils.UiScope;


public class MyActivity extends AppCompatActivity {

    @UiScope
    @Component(dependencies = ManagerComponent.class, modules = UiModule.class)
    public interface MyActivityComponent {
        void inject(MyActivity activity);
    }

    @Inject
    PostManager mPostManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMyActivity_MyActivityComponent.builder()
                .managerComponent(MyApplication.getManagerComponent(getApplication()))
                .uiModule(new UiModule(this))
                .build()
                .inject(this);

        setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().findFragmentById(R.id.my_fragment_container) == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.my_fragment_container, MyFragment.newInstance()).commit();
        }
    }
}
