package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.model.User;

/**
 * Created by MSI on 26/05/2017.
 */

public class SignUpWithFirebaseFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    private EditText name, firstname, address, email, password;
    private Button resetButton, confirmButton;
    private ProgressBar progressBar;

    private final String error = "Champ requis";

    public SignUpWithFirebaseFragment() {
    }

    public static Fragment newInstance(){
        return new SignUpWithFirebaseFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sign_up_with_firebase, container, false);
        findViewById(rootView);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        name = (EditText) view.findViewById(R.id.new_user_name);
        firstname = (EditText) view.findViewById(R.id.new_user_firstname);
        address = (EditText) view.findViewById(R.id.new_user_address);
        email = (EditText) view.findViewById(R.id.new_user_email);
        password = (EditText) view.findViewById(R.id.new_user_password);
        resetButton = (Button) view.findViewById(R.id.reset_button);
        confirmButton = (Button) view.findViewById(R.id.confirm_button);
        progressBar = (ProgressBar) view.findViewById(R.id.sign_up_progress_bar);

    }

    private void setError(EditText text){
        text.setError(error);
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

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                firstname.setText("");
                email.setText("");
                address.setText("");
                password.setText("");
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    createAccount();


            }
        });






    }

    public void createAccount(){
        final String firstname = this.firstname.getText().toString().trim();
        final String name = this.name.getText().toString().trim();
        final String address = this.address.getText().toString().trim();
        String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();



        if (TextUtils.isEmpty(firstname)){
            setError(this.firstname);
            return;
        }
        else{
            this.firstname.setError(null);
        }

        if (TextUtils.isEmpty(name)){
            setError(this.name);
            return;
        }
        else {
            this.name.setError(null);
        }

        if (TextUtils.isEmpty(address)){
            setError(this.address);
            return;
        }
        else{
            this.address.setError(null);
        }

        if (TextUtils.isEmpty(email)){
            setError(this.email);
            return;
        }
        else{
            this.email.setError(null);
        }

        if (TextUtils.isEmpty(password)){
            setError(this.password);
            return;
        }
        else {
            this.password.setError(null);
        }

        if (password.length()<6){
            Toast.makeText(getContext(),
                    "Le mot de passe doit être d'une longueur d'au moins 6 caractères",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);

                        Log.d("Account : ", "createUserWithEmail:onComplete :" +task.isSuccessful());

                        if (!task.isSuccessful()){
                            Toast.makeText(getContext(),
                                    R.string.connection_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{

                            writeNewUser(mAuth.getCurrentUser().getUid(), firstname, name, address);

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

    private void writeNewUser(String userId, String firstname, String name, String address){

        User user = new User(firstname, name, address, 0);

        mDatabase.child("users").child(userId).setValue(user);

    }

}
