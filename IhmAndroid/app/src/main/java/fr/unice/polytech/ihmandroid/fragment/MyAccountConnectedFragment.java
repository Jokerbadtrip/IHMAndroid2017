package fr.unice.polytech.ihmandroid.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.unice.polytech.ihmandroid.R;

/**
 * Created by MSI on 16/05/2017.
 */

public class MyAccountConnectedFragment extends Fragment {



    private DatabaseReference mDataBase;
    private TextView accountName, accountFirtName, accountAddress, accountFidelityPoints, accountEmail;
    private Button modifyAddress, modifyEmail, modifyPassword, sponsorship;

    private FirebaseAuth mAuth;

    private String address, firstname, name;
    private Integer  fidelityPoints;


    public MyAccountConnectedFragment() {
    }

    public static MyAccountConnectedFragment newInstance(){
        return new MyAccountConnectedFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_account_connected, container, false);
        findViewById(rootView);

        mAuth = FirebaseAuth.getInstance();






        return rootView;
    }

    private void findViewById(View view){

        accountName =  (TextView) view.findViewById(R.id.account_name);
        accountFirtName = (TextView) view.findViewById(R.id.account_firstname);
        accountAddress = (TextView) view.findViewById(R.id.account_address);
        accountFidelityPoints = (TextView) view.findViewById(R.id.fidelity_points);
        accountEmail = (TextView) view.findViewById(R.id.account_email);
        modifyAddress = (Button) view.findViewById(R.id.modify_address);
        modifyEmail = (Button) view.findViewById(R.id.modify_email);
        modifyPassword = (Button) view.findViewById(R.id.modify_password);
        sponsorship = (Button) view.findViewById(R.id.sponsorship_button);

    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDataBase = FirebaseDatabase.getInstance().getReference();
        mDataBase.child("users").child(user.getUid())
                .child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue(String.class);
                if (name==null) return;
                accountName.setText(name);
                Log.d("Reading user name", name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Reading user name", databaseError.toException().toString());
            }
        });

        mDataBase.child("users").child(user.getUid())
                .child("firstname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firstname = dataSnapshot.getValue(String.class);
                if (firstname==null) {
                    accountFirtName.setText(user.getDisplayName());
                    Log.d("Reading user firstname", user.getDisplayName());
                }
                else {
                    accountFirtName.setText(firstname);
                    Log.d("Reading user firstname", firstname);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Reading user firstname", databaseError.toException().toString());
            }
        });

        mDataBase.child("users").child(user.getUid())
                .child("address").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                address = dataSnapshot.getValue(String.class);
                if (address!=null) {
                    accountAddress.setText(address);
                    Log.d("Reading user address", address);
                }
                else {
                    accountAddress.setText("Veillez à modifier votre adresse pour passer une commande");

                    accountAddress.setTextColor(Color.RED);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Reading user address", databaseError.toException().toString());
            }
        });

        mDataBase.child("users").child(user.getUid())
                .child("fidelityPoints").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fidelityPoints = dataSnapshot.getValue(Integer.class);
                if (fidelityPoints!=null) {
                    accountFidelityPoints.setText(String.valueOf(fidelityPoints));
                    Log.d("Reading fidelity points", fidelityPoints.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Reading fidelity points", databaseError.toException().toString());
            }
        });

        accountEmail.setText(user.getEmail());

        modifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(user.getEmail());
                Toast.makeText(getContext(), "Vous allez recevoir un e-mail pour réinitialiser votre mot de passe. Veuillez vous reconnecter", Toast.LENGTH_LONG).show();
                disconnect();
            }
        });

        modifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Modifier l'adresse e-mail");

                final EditText email = new EditText(getContext());
                email.setHint("Courriel");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                email.setLayoutParams(lp);
                alertDialog.setView(email);
                alertDialog.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newEmail = email.getText().toString().trim();

                        if (TextUtils.isEmpty(newEmail)){

                            email.setError("Le champ ne peut pas être vide");

                        }else{
                            user.updateEmail(newEmail);
                            user.sendEmailVerification();
                            dialog.cancel();
                            disconnect();



                        }


                    }
                });

                alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

            }
        });


        modifyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Modifier l'adresse");

                final EditText address = new EditText(getContext());
                address.setHint("Adresse postale");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                address.setLayoutParams(lp);

                alertDialog.setView(address);

                alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newAddress = address.getText().toString().trim();

                        if (TextUtils.isEmpty(newAddress)){
                            address.setError("Le champ est vide");
                        }
                        else{

                            DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();
                            mDataBase.child("users").child(mAuth.getCurrentUser().getUid()).child("address").setValue(newAddress);
                            dialog.cancel();

                        }
                    }
                });
            }
        });


    }


    private void disconnect(){

        mAuth.signOut();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Fragment fragment = MyAccountNotConnectedFragment.newInstance();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();


    }




}
