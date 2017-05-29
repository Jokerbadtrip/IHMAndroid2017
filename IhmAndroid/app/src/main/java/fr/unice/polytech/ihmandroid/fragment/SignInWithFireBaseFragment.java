package fr.unice.polytech.ihmandroid.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fr.unice.polytech.ihmandroid.R;

import static android.content.ContentValues.TAG;

/**
 * Created by MSI on 26/05/2017.
 */

public class SignInWithFireBaseFragment extends Fragment {


    private EditText email, password;
    private Button connect, passwordForgotten;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public SignInWithFireBaseFragment() {
    }

    public static Fragment newInstance(){
        return new SignInWithFireBaseFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.sign_in_with_firebase, container, false);
        findViewById(rootView);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null){
                    Log.d("Account : ", "user is connected");

                }
                else {

                    Log.d("Account :", "user is disconnected");
                }
            }
        };

        return rootView;
    }

    private void findViewById(View view){
        email = (EditText) view.findViewById(R.id.account_email);
        password = (EditText) view.findViewById(R.id.user_password);
        connect = (Button) view.findViewById(R.id.connect_button);
        progressBar = (ProgressBar) view.findViewById(R.id.sign_in_progress_bar);
        passwordForgotten = (Button) view.findViewById(R.id.password_forgotten);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                signIn();
            }
        });

        passwordForgotten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Mot de passe oublié");
                final EditText email = new EditText(getContext());
                email.setHint("Courriel");

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);

                email.setLayoutParams(lp);
                alertDialog.setView(email);

                alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.setPositiveButton("Réinitialiser le mot de passe", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newEmail = email.getText().toString().trim();

                        if (TextUtils.isEmpty(newEmail)){
                            email.setError("Le champ est vide");
                        }
                        else{
                            mAuth.sendPasswordResetEmail(newEmail);
                            dialog.cancel();
                        }
                    }
                });

            }
        });
    }

    private void signIn(){
        String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            this.email.setError("Champ requis");
            return;
        }else{
            this.email.setError(null);
        }

        if (TextUtils.isEmpty(password)){
            this.password.setError("Champ requis");
        }else {
            this.password.setError(null);
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        progressBar.setVisibility(View.GONE);

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(getContext(), R.string.connection_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            Fragment fragment = MyAccountConnectedFragment.newInstance();
                            ft.replace(R.id.content_frame, fragment);
                            ft.addToBackStack(null);
                            ft.commit();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
