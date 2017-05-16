package fr.unice.polytech.ihmandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import fr.unice.polytech.ihmandroid.R;

import static android.content.ContentValues.TAG;

/**
 * Created by MSI on 16/05/2017.
 */

public class MyAccountNotConnectedFragment extends Fragment {


    SignInButton button;
    GoogleApiClient.OnConnectionFailedListener connectionListener;
    GoogleApiClient gac;
    int RC_SIGN_IN;

    public MyAccountNotConnectedFragment() {
    }


    public static Fragment newInstance(){
        return new MyAccountNotConnectedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_account_no_connected, container, false);
        button = (SignInButton) rootView.findViewById(R.id.connect_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestEmail()
                .build();
        gac = new GoogleApiClient.Builder(this.getContext())
                .enableAutoManage(this.getActivity(), connectionListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        return rootView;
    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }


    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(gac);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            authentificationWithGoogle(acct);

        } else {
            // Signed out, show unauthenticated UI.

        }
    }

    private void authentificationWithGoogle(GoogleSignInAccount acct) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        MyAccountConnectedFragment fragment = MyAccountConnectedFragment.newInstance();
        fragment.setAccount(acct);
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();

    }

}
