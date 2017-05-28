package fr.unice.polytech.ihmandroid;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fr.unice.polytech.ihmandroid.fragment.MyAccountConnectedFragment;
import fr.unice.polytech.ihmandroid.fragment.MyAccountNotConnectedFragment;
import fr.unice.polytech.ihmandroid.fragment.CategoriesViewFragment;
import fr.unice.polytech.ihmandroid.fragment.PromotedViewFragment;
import fr.unice.polytech.ihmandroid.fragment.StoreListFragment;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displayView(R.id.nav_promoted);

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem shareButton = menu.findItem(R.id.share_button);
        ShareActionProvider mShareActionProvider;
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareButton);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displayView(item.getItemId());
        return true;
    }




    public void displayView(int itemId){

        Fragment fragment = null;
        String title = getString(R.string.app_name);


        if (itemId == R.id.nav_stores) {
            Log.d("Navigation :", "displaying stores");
            fragment = StoreListFragment.newInstance();
            title  = "Magasins";
        } else if (itemId == R.id.nav_products) {
            Log.d("Navigation :", "displaying products");
            fragment = CategoriesViewFragment.newInstance();
            title = "Produits";
        } else if (itemId == R.id.nav_offers) {
            Log.d("Navigation :", "displaying offers");
        } else if (itemId == R.id.nav_account) {
            Log.d("Navigation :", "displaying account");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user==null){
                fragment = MyAccountNotConnectedFragment.newInstance();
            }
            else {
                fragment = MyAccountConnectedFragment.newInstance();
            }

            title="Mon compte";
        } else if (itemId == R.id.nav_promoted){
            Log.d("Navigation :", "displaying promoted products");
            fragment = PromotedViewFragment.newInstance();
            title = "Produits phares";
        } else if (itemId == R.id.nav_share) {

        } else if (itemId == R.id.nav_send) {

        }



        if (fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }

}
