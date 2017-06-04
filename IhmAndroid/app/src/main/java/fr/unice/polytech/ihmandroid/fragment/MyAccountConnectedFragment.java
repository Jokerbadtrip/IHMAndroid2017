package fr.unice.polytech.ihmandroid.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.unice.polytech.ihmandroid.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by MSI on 16/05/2017.
 */

public class MyAccountConnectedFragment extends Fragment {



    private DatabaseReference mDataBase;
    private TextView accountName, accountFirtName, accountAddress, accountFidelityPoints, accountEmail;
    private Button modifyAddress, modifyEmail, modifyPassword, sponsorship, logout;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private String address, firstname, name;
    private Integer  fidelityPoints;

    private int REQUEST_INVITE = 9002;

    private final String TAG = "MyAccountConnected";

    private final Integer fidelityPointsPerInvite = 10;



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
        user = mAuth.getCurrentUser();
        mDataBase = FirebaseDatabase.getInstance().getReference();




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
        logout = (Button) view.findViewById(R.id.logout_button);

    }

    private void readUserDataFromDatabase(){

        mDataBase.child("users").child(user.getUid())
                .child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue(String.class);
                if (name != null) {
                    accountName.setText(name);
                    Log.d(TAG, "Reading user name : " +name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Reading user name : " +databaseError.toException().toString());
            }
        });

        mDataBase.child("users").child(user.getUid())
                .child("firstname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firstname = dataSnapshot.getValue(String.class);

                if (firstname!=null){
                    accountName.setText(firstname);
                    Log.d(TAG,"Reading user firstname : " + firstname);
                }
                else if (firstname == null){
                    accountName.setText(user.getDisplayName());
                    Log.d(TAG, "Reading user firstname : "+ user.getDisplayName());
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Reading user firstname : "+ databaseError.toException().toString());
            }
        });

        mDataBase.child("users").child(user.getUid())
                .child("address").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                address = dataSnapshot.getValue(String.class);

                if (address!=null) {
                    accountAddress.setText(address);
                    Log.d(TAG, "Reading user address : " +address);
                }
                else if (getString(R.string.address_missing).equals(address)){
                    accountAddress.setTextColor(Color.RED);
                    Log.d(TAG, "Reading user address : no address was specified");
                }

                else {
                    accountAddress.setText(getString(R.string.address_missing));
                    accountAddress.setTextColor(Color.RED);
                    Log.d(TAG, "Reading user address : no address was specified");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Reading user address : " + databaseError.toException().toString());
            }
        });

        mDataBase.child("users").child(user.getUid())
                .child("fidelityPoints").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fidelityPoints = dataSnapshot.getValue(Integer.class);
                if (fidelityPoints!=null) {
                    accountFidelityPoints.setText(String.valueOf(fidelityPoints));
                    Log.d(TAG, "Reading fidelity points : "+ fidelityPoints.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Reading fidelity points : "+databaseError.toException().toString());
            }
        });

    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        readUserDataFromDatabase();
        accountEmail.setText(user.getEmail());

        modifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(user.getEmail());
                Toast.makeText(getContext(),
                        "Vous allez recevoir un e-mail pour réinitialiser votre mot de passe." +
                                " Veuillez vous reconnecter", Toast.LENGTH_LONG).show();
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

                alertDialog.show();

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

                        if (TextUtils.isEmpty(newAddress)) {
                            address.setError("Le champ est vide");
                        } else {

                            DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();
                            mDataBase.child("users").child(mAuth.getCurrentUser().getUid()).child("address").setValue(newAddress);
                            dialog.cancel();

                        }
                    }
                });
                alertDialog.show();

            }
        });


        sponsorship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firstname != null && fidelityPoints != null) {
                    Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invite_title))
                            .setMessage(getString(R.string.invite_message) + "\n" +
                                    firstname.substring(0, 3) + fidelityPoints)
                            .setCustomImage(Uri.parse(getString(R.string.invite_image)))
                            .build();
                    startActivityForResult(intent, REQUEST_INVITE);
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnect();
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "OnActivityResult : requestCode : "+requestCode + " resultCode : "+resultCode);

        if (requestCode == REQUEST_INVITE){
            if (resultCode == RESULT_OK){
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids){
                    Log.d(TAG, "OnActivityResult : sent invitation : "+id);
                }

                FirebaseUser user = mAuth.getCurrentUser();
                mDataBase.child("users").child(user.getUid()).child("fidelityPoints")
                        .setValue(fidelityPoints + fidelityPointsPerInvite*ids.length);

            }
        }
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
