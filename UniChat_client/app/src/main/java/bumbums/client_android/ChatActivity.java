package bumbums.client_android;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import aboullaite.Data;
import bumbums.client_android.Adapter.ChatViewAdapter;
import bumbums.client_android.Adapter.CurrentUserAdapter;

public class ChatActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Data> {
    private Client mClient;
    private RecyclerView mCurrentUserView;
    private CurrentUserAdapter mCurrentUserAdapter;
    private RecyclerView mChatView;
    private ChatViewAdapter mChatViewAdapter;

    private final int CHAT_LOADER = 2;
    LoaderManager loaderManager = getSupportLoaderManager();
    Loader<Data> loginLoader = loaderManager.getLoader(CHAT_LOADER);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mCurrentUserView = (RecyclerView)findViewById(R.id.rv_current_user);
        mChatView = (RecyclerView)findViewById(R.id.rv_chat_view);
        mCurrentUserAdapter = new CurrentUserAdapter();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        setUpRecyclerView();
        Intent intent = getIntent();
        mClient = (Client)intent.getSerializableExtra(getString(R.string.client_extra));
        if (loginLoader == null) {
            loaderManager.initLoader(CHAT_LOADER, null, this).forceLoad();
        } else {
            loaderManager.restartLoader(CHAT_LOADER, null, this);
        }
    }

    public void setUpRecyclerView() {
        mCurrentUserView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mCurrentUserView.setAdapter(mCurrentUserAdapter);
        mCurrentUserView.setHasFixedSize(true);
        mChatView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mChatView.setAdapter(mChatViewAdapter);
        mChatView.setHasFixedSize(true);
    }

    @Override
    public Loader<Data> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Data>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                Log.d("#####","onstartLoding");
            }

            @Override
            public Data loadInBackground() {
                Log.d("#####","doinBack");
                mClient.setListener(new Client.OnMessageReceived() {
                    @Override
                    public void messageReceived(Data data) {
                        deliverResult(data);
                    }
                });
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Data> loader, Data data) {
        if(data!=null){
            Log.d("#####","들어온데이터"+data.getMsg());
        }

    }

    @Override
    public void onLoaderReset(Loader<Data> loader) {

    }
}
