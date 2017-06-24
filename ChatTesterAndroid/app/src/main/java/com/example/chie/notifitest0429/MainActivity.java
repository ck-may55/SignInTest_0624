package com.example.chie.notifitest0429;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
//
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.chie.notifitest0429.Constants;
import com.google.firebase.iid.FirebaseInstanceId;

import com.example.chie.notifitest0429.ChatPage;



public class MainActivity extends Activity implements StartPage.OnFragmentInteractionListener, ChatPage.OnFragmentInteractionListener {
    private static final String TAG = "MainActivity";

    // ユーザ固定
    private String userId = "AB0012-6";
    private String token;

    //ログイン成功時に取得したUIDを保存
    public static String uid;

    //Authentication機能を使うのに必要
    private FirebaseAuth mAuth;

    //ログイン状態を追うためのリスナー
    private FirebaseAuth.AuthStateListener mAuthListener;

    //サインインに必要なメールアドレスとパスワード
    //private String mEmail = "star318.ss@yawnchat.webapp";
    //private String mPassword = "999999";
    private String mEmail = "hoge@yawnchat.webapp";
    private String mPassword = "fugafuga";

    private TextView textFlag;
    private TextView textToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        boolean fromTray = false;
        setContentView(R.layout.activity_main);

        // 起動がアイコンからか、システムトレイ経由かどうかをチェック
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
                // システムトレイ経由だったらfromTrayをtrueにセットする
                fromTray = true;
            }
            Log.d(TAG, "getExtras() is not null !!");
        }

        // 初期画面をオープン
        openStartPage(fromTray);
    }


    /**
     *　　初期画面のFragmentを開く
     */
    private void openStartPage(boolean fromTray) {

        Log.d("MainActivity", "openStartPage 1");

        // 初期画面のFragmentを開くトランザクション
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // 初期画面Fragmentの作成とセット
        StartPage startPage = new StartPage();
        transaction.add(R.id.container, startPage);
        // トランザクションのコミット
        transaction.commit();

        // 起動がシステムトレイ（通知）経由かどうかを初期画面Fragmentに伝える
        Bundle bundle = new Bundle();
        bundle.putBoolean("FROM_TRAY", fromTray);
        startPage.setArguments(bundle);

        Log.d("MainActivity", "openStartPage 2");
    }

    /**
     *　　チャット画面のFragmentを開く
     */
    public void openChat() {

        //setContentView(R.layout.activity_main);

        Log.d("MainActivity", "openChat 1");

        //　チャットページFragmentへの切り替え
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ChatPage chatPage = new ChatPage();
        transaction.replace(R.id.container, chatPage);
        // 初期画面に戻るためのスタックをセットする
        transaction.addToBackStack(null);
        transaction.commit();

        Log.d("MainActivity", "openChat 2");

        //  画面トップのツールバーの表示を変更
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("サポート・チャット");
        Button backButton = (Button) findViewById(R.id.left_back_button);
        backButton.setVisibility(View.VISIBLE);
        Button nextButton = (Button) findViewById(R.id.right_next_button);
        nextButton.setVisibility(View.GONE);

        Log.d("MainActivity", "openChat 3");
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }

    // チャット画面から初期画面に戻る処理
    public void onBack() {

        // スタックを一つ戻して（pop）初期画面に戻る
        getFragmentManager().popBackStack();

        // ツールバーの設定
        Button backButton = (Button) findViewById(R.id.left_back_button);
        backButton.setVisibility(View.GONE);
        Button nextButton = (Button) findViewById(R.id.right_next_button);
        nextButton.setVisibility(View.VISIBLE);
    }
}