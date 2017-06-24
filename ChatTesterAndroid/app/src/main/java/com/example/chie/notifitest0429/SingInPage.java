package com.example.chie.notifitest0429;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.R.attr.button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SingInPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SingInPage#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * サインインフォーム
 * まだ一度もサインインしていない場合、falgment_sign_in_pageを先に表示する
 *
 */
public class SingInPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "SignInPage";

    //ユーザID
    private String userId;
    //ログイン成功時に取得するUID
    private String uid;
    //Authentication機能を使うのに必要
    private FirebaseAuth mAuth;
    //ログイン状態を追うためのリスナー
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText editTextId;
    private EditText editTextPass;
    private Button buttonSignIn;


    private OnFragmentInteractionListener mListener;

    public SingInPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingInPage.
     */
    // TODO: Rename and change types and number of parameters
    public static SingInPage newInstance(String param1, String param2) {
        SingInPage fragment = new SingInPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //added by kubota
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sing_in_page, container, false);

        //added by kubota
        //view関連
        buttonSignIn = (Button) view.findViewById(R.id.button_sign_in);
        editTextId = (EditText) view.findViewById(R.id.edit_text_id);
        editTextPass = (EditText) view.findViewById(R.id.edit_text_pass);

        // 認証が終わった時（変更された時）に呼ばれるクロージャを設定
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            // 認証が変更された時に呼ばれる関数
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                // 認証が完了
                if (user != null) {
                    //サインイン済みであれば、
                    // サインインしたアカウントに対応するUIDをString型変数uidに保存
                    if (user.getUid() != null) {
                        uid = user.getUid().toString();
                        Constants.UID = uid;
                    }
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    //サインアウト時
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        //added by kubota
        //Firebaseにサインイン
        //signIn(mEmail, mPassword);
    }
    //added by kubota
    // Firebaseへのサインインを担う部分
    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // サインイン成功時
                            Log.d(TAG, "signInWithEmail:success");
                        } else {
                            // サインイン失敗時
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
