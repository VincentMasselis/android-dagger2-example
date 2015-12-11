package fr.vincentmasselis.daggersample.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.Component;
import fr.vincentmasselis.daggersample.MyApplication;
import fr.vincentmasselis.daggersample.R;
import fr.vincentmasselis.daggersample.manager.ManagerComponent;
import fr.vincentmasselis.daggersample.manager.post.PostManager;
import fr.vincentmasselis.daggersample.model.Post;
import fr.vincentmasselis.daggersample.ui.utils.UiModule;
import fr.vincentmasselis.daggersample.ui.utils.UiScope;

public class MyFragment extends Fragment {

    /**
     * Fonctionne exactement de la même manière que
     * {@link fr.vincentmasselis.daggersample.ui.MyActivity.MyActivityComponent}
     */
    @UiScope
    @Component(dependencies = ManagerComponent.class, modules = UiModule.class)
    public interface MyFragmentComponent {
        void inject(MyFragment fragment);
    }

    @Inject
    PostManager mPostManager;
    @Inject
    Context mContext;

    TextView mMainTextView;


    private Loader<Pair<Throwable, Post>> mOneLoader;
    private Loader<Pair<Throwable, Collection<Post>>> mFiveLoader;

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DaggerMyFragment_MyFragmentComponent.builder()//Voir dans MyActivity#onCreate(Bundle) pour comprendre
                .managerComponent(MyApplication.getManagerComponent(getActivity().getApplication()))
                .uiModule(new UiModule(context))
                .build()
                .inject(this);
        mOneLoader = getLoaderManager().initLoader(42, null, mOnePostLoaderCallback);
        mFiveLoader = getLoaderManager().initLoader(43, null, mFivePostLoaderCallback);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mMainTextView = (TextView) root.findViewById(R.id.main_tv);
        Button buttonOne = (Button) root.findViewById(R.id.load_hundred_btn);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOneLoader.forceLoad();
            }
        });
        Button buttonFive = (Button) root.findViewById(R.id.load_five_btn);
        buttonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFiveLoader.forceLoad();
            }
        });
        return root;
    }

    private LoaderManager.LoaderCallbacks<Pair<Throwable, Post>> mOnePostLoaderCallback = new LoaderManager.LoaderCallbacks<Pair<Throwable, Post>>() {
        @Override
        public Loader<Pair<Throwable, Post>> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<Pair<Throwable, Post>>(mContext) {
                @Override
                public Pair<Throwable, Post> loadInBackground() {
                    try {
                        return new Pair<>(null, mPostManager.getPost().get(15, TimeUnit.SECONDS));
                    } catch (Throwable e) {
                        return new Pair<>(e, null);
                    }
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<Pair<Throwable, Post>> loader, Pair<Throwable, Post> data) {
            if (data.first == null) {
                if (mMainTextView != null) {
                    Post post = data.second;
                    mMainTextView.setText(String.format("%sTitle : %s\nBody : %s\n\n", mMainTextView.getText(), post.getTitle(), post.getBody()));
                }
            } else {
                Throwable e = data.first;
                Log.e("MyFragment", "Impossible de charger un post", e);
                Toast.makeText(loader.getContext(), "Impossible de charger un post", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<Pair<Throwable, Post>> loader) {

        }
    };

    private LoaderManager.LoaderCallbacks<Pair<Throwable, Collection<Post>>> mFivePostLoaderCallback = new LoaderManager.LoaderCallbacks<Pair<Throwable, Collection<Post>>>() {
        @Override
        public Loader<Pair<Throwable, Collection<Post>>> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<Pair<Throwable, Collection<Post>>>(mContext) {
                @Override
                public Pair<Throwable, Collection<Post>> loadInBackground() {
                    try {
                        return new Pair<>(null, mPostManager.getPosts().get(15, TimeUnit.SECONDS));
                    } catch (Throwable e) {
                        return new Pair<>(e, null);
                    }
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<Pair<Throwable, Collection<Post>>> loader, Pair<Throwable, Collection<Post>> data) {
            if (data.first == null) {
                if (mMainTextView != null) {
                    Collection<Post> posts = data.second;
                    for (Post post : posts) {
                        mMainTextView.setText(String.format("%sTitle : %s\nBody : %s\n\n", mMainTextView.getText(), post.getTitle(), post.getBody()));
                    }
                }
            } else {
                Throwable e = data.first;
                Log.e("MyFragment", "Impossible de charger un post", e);
                Toast.makeText(loader.getContext(), "Impossible de charger un post", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<Pair<Throwable, Collection<Post>>> loader) {

        }
    };
}