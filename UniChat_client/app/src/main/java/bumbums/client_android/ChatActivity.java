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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import aboullaite.Data;
import aboullaite.User;
import aboullaite.util.BackPressCloseHandler;
import aboullaite.util.Constants;
import bumbums.client_android.Adapter.ChatViewAdapter;
import bumbums.client_android.Adapter.CurrentUserAdapter;

public class ChatActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Data> {
    private Client mClient;
    private RecyclerView mCurrentUserView;
    private CurrentUserAdapter mCurrentUserAdapter;
    private RecyclerView mChatView;
    private ChatViewAdapter mChatViewAdapter;
    private Button mSendBtn;
    private EditText mMsg;
    private LinearLayout mNotification;
    private ImageView mNotiDelBtn;
    private TextView mNotiTextView;
    private ArrayList<Data> mChatDatas;
    private ArrayList<User> mCurrentUser;
    private BackPressCloseHandler backPressCloseHandler;

    private final int CHAT_LOADER = 2;

    private boolean isFirstInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mClient = Client.getInstance();
        mCurrentUser = new ArrayList<>();
        mChatDatas = new ArrayList<>();
        mCurrentUserView = (RecyclerView)findViewById(R.id.rv_current_user);
        mChatView = (RecyclerView)findViewById(R.id.rv_chat_view);
        mCurrentUserAdapter = new CurrentUserAdapter(mCurrentUser);
        mChatViewAdapter = new ChatViewAdapter(mChatDatas,new User(mClient.getEmail(),mClient.getNickname()));
        mSendBtn = (Button)findViewById(R.id.btn_send);
        mMsg = (EditText)findViewById(R.id.et_msg);
        mNotiTextView = (TextView)findViewById(R.id.tv_noti);
        mNotification  =(LinearLayout)findViewById(R.id.ll_noti);
        mNotiDelBtn = (ImageView)findViewById(R.id.iv_del);

        backPressCloseHandler = new BackPressCloseHandler(this);
        isFirstInput = true;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        setUpRecyclerView();

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Data> loginLoader = loaderManager.getLoader(CHAT_LOADER);
        if (loginLoader == null) {
            loaderManager.initLoader(CHAT_LOADER, null, this).forceLoad();
        } else {
            loaderManager.restartLoader(CHAT_LOADER, null, this);
        }
        Toast.makeText(this,getString(R.string.input_nickname),Toast.LENGTH_SHORT).show();

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String msg = mMsg.getText().toString();
                        if(isFirstInput){
                            mClient.setNickname(msg);
                            isFirstInput = false;
                        }
                        String sendor = mClient.getNickname();
                        Data data = new Data(Constants.TYPE_MSG,msg,null);
                        data.setSendorEmail(mClient.getEmail());
                        data.setSendorNickName(mClient.getNickname());
                        mClient.sendMessage(data);
                    }
                }).start();
                mMsg.setText("");
            }
        });
        mNotiDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotification.setVisibility(View.INVISIBLE);
            }
        });
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
                mClient.setListener(new Client.OnMessageReceived() {
                    @Override
                    public void messageReceived(Data data) {
                        deliverResult(data);
                    }
                });

            }
            @Override
            public Data loadInBackground() {
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Data> loader, Data data) {
        if(data!=null){
            int type = data.getType();
            if(type == Constants.TYPE_MSG || type ==Constants.TYPE_WHISPER){
                Log.d("#####DATA",data.toString());
                updateMsg(data);
            }
            else if(type == Constants.TYPE_USER_LIST){
                Log.d("#####UPDATER",data.toString());
                updateUser(data.getUserList());
            }
            else if(type ==Constants.TYPE_NOTIFICATION){
                Log.d("#####NOTI",data.toString());
                updateNoti(data.getMsg());
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<Data> loader) {

    }


    public void updateMsg(final Data data){
        ((ChatActivity) this).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mChatDatas.add(data);
                mChatViewAdapter.notifyDataSetChanged();
                mChatView.smoothScrollToPosition(mChatDatas.size()-1);
            }
        });
    }

    public void updateUser(final ArrayList<User> currentUser){
        ((ChatActivity) this).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCurrentUser.clear();
                mCurrentUser.addAll(currentUser);
                mCurrentUserAdapter.notifyDataSetChanged();
                mCurrentUserView.smoothScrollToPosition(currentUser.size()-1);
            }
        });

    }

    public void updateNoti(final String text){
        ((ChatActivity)this).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNotiTextView.setText(text);
                mNotification.setVisibility(View.VISIBLE);

            }
        });
    }


    @Override
    public void onBackPressed() {

        if(backPressCloseHandler.onBackPressed()){ //만약 두번 누른거라면
            //클라이언트 종료
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mClient.stopClient();
                }
            }).start();
        }
        else{

        }

    }
}
