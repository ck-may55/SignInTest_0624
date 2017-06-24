package com.example.chie.notifitest0429;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChatPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatPage.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatPage newInstance(String param1, String param2) {
        ChatPage fragment = new ChatPage();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_chat_page, container, false);

        Log.d("ChatPage", "onCreateView 1");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_page, container, false);

        //
        WebView chatWebView = (WebView)view.findViewById(R.id.chat_view);

        chatWebView.getSettings().setUseWideViewPort(true);
        chatWebView.getSettings().setLoadWithOverviewMode(true);

        Log.d("ChatPage", "onCreateView 2");

        //
        String url = getString(R.string.chatpage_url);
        //
        chatWebView.loadUrl(url);

        Log.d("ChatPage", "onCreateView 3");

        //jacascriptを許可する
        chatWebView.getSettings().setJavaScriptEnabled(true);

        Log.d("ChatPage", "onCreateView 4");

        // 戻るボタンのセット
        setBackButton();

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onBack();
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

    private void setBackButton() {
        android.widget.Button left_button = (Button) getActivity().findViewById(R.id.left_back_button);

        left_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("ChatPage", "onClick back button");

                if (mListener != null) {
                    //
                    mListener.onBack();
                }
            }
        });
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
        void onBack();
        void onFragmentInteraction(Uri uri);
    }
}
