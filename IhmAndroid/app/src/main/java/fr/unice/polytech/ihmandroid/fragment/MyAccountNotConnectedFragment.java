package fr.unice.polytech.ihmandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.model.User;

import static android.content.ContentValues.TAG;

/**
 * Created by MSI on 16/05/2017.
 */

public class MyAccountNotConnectedFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {


    private SignInButton googleButton;
    private Button registerButton;
    private Button signInButton;
    private LoginButton facebookButton;
    private CallbackManager mCallbackManager;


    private FirebaseAuth auth;
    private GoogleApiClient mGoogleApiClient;
    private final int RC_SIGN_IN = 9001;

    private final String TAG = "NotConnectedAccount";



    public MyAccountNotConnectedFragment() {
    }


    public static Fragment newInstance(){
        return new MyAccountNotConnectedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_account_not_connected, container, false);

        findViewById(rootView);

        auth = FirebaseAuth.getInstance();

        facebookButton.setFragment(this);
        facebookButton.setReadPermissions("email", "public_profile");
        mCallbackManager = CallbackManager.Factory.create();
        facebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess : "+loginResult);
                firebaseAuthWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, error.toString());
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                .enableAutoManage(this.getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        return rootView;
    }

    private void findViewById(View rootView) {

        googleButton = (SignInButton) rootView.findViewById(R.id.connect_with_google_button);
        signInButton = (Button) rootView.findViewById(R.id.connect_with_firebase_button);
        registerButton = (Button) rootView.findViewById(R.id.register_with_firebase_button);
        facebookButton = (LoginButton) rootView.findViewById(R.id.connect_with_facebook_button);

    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithFirebase();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpWithFirebase();
            }
        });


    }

    private void signInWithFirebase(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Fragment fragment = SignInWithFireBaseFragment.newInstance();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    private void signUpWithFirebase(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Fragment fragment = SignUpWithFirebaseFragment.newInstance();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void signInWithGoogle(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);

    }

    private void firebaseAuthWithFacebook(AccessToken token){
        Log.d(TAG, "firebaseAuthWithFacebook : " + token);


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential).addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d(TAG, "facebook:onComplete : " + task.isSuccessful());
                if (!task.isSuccessful()){
                    Toast.makeText(getContext(), R.string.connection_failed,
                            Toast.LENGTH_SHORT).show();
                }
                else{

                    writeNewUser();


                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    Fragment fragment = MyAccountConnectedFragment.newInstance();
                    ft.replace(R.id.content_frame, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });

    }

    private void writeNewUser(){
        FirebaseUser firebaseUser = auth.getCurrentUser();
        User user = new User(null, null, null, 0);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        if(mDatabase.child("users").child(firebaseUser.getUid()).equals(firebaseUser.getUid())){
            return;
        }else{
            mDatabase.child("users").child(firebaseUser.getUid()).setValue(user);
        }

    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct){
        Log.d(TAG, "firebaseAuthWithGoogle : "+acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()){
                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                    Toast.makeText(getContext(), R.string.connection_failed,
                            Toast.LENGTH_SHORT).show();
                }else{

                    writeNewUser(acct);

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    Fragment fragment = MyAccountConnectedFragment.newInstance();
                    ft.replace(R.id.content_frame, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }

            }
        });
    }

    private void writeNewUser(GoogleSignInAccount account){
        User user = new User(account.getFamilyName(), account.getGivenName(), null, 0);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if(mDatabase.child("users").child(firebaseUser.getUid()).equals(firebaseUser.getUid())){
            return;
        }else{
            mDatabase.child("users").child(firebaseUser.getUid()).setValue(user);
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.

            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d(TAG,"connected to : " +acct.getDisplayName());
            firebaseAuthWithGoogle(acct);



        } else {
            Log.e(TAG, "connection error");
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed : "+connectionResult);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onPause() {
        super.onPause();

        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }
}
