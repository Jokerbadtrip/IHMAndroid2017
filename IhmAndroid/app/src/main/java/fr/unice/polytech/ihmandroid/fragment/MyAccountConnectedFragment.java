package fr.unice.polytech.ihmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import fr.unice.polytech.ihmandroid.R;

/**
 * Created by MSI on 16/05/2017.
 */

public class MyAccountConnectedFragment extends Fragment {


    GoogleSignInAccount account;
    TextView accountName;

    public MyAccountConnectedFragment() {
    }

    public static MyAccountConnectedFragment newInstance(){
        return new MyAccountConnectedFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_account_connected, container, false);
        accountName =  (TextView) rootView.findViewById(R.id.account_name);
        return rootView;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        accountName.setText(account.getDisplayName());
    }


    public void setAccount(GoogleSignInAccount account){
        this.account=account;
    }


}
