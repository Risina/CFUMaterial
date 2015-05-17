/*
 * Copyright (C) 2014 Antonio Leiva Gordillo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.cfu.com.cfumaterial;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import client.cfu.com.base.CFAdvertisementDataHandler;
import client.cfu.com.base.CFHttpManager;
import client.cfu.com.base.CFMinorDataHandler;
import client.cfu.com.base.CFUserSessionManager;
import client.cfu.com.constants.CFConstants;
import client.cfu.com.entities.CFAdvertisement;
import client.cfu.com.entities.CFUser;
import client.cfu.com.util.CFPopupHelper;


public class HomeActivity extends BaseActivity {

    private DrawerLayout drawer;
//    private List<CFAdvertisement> adList;
    ListView mDrawerList;
    RelativeLayout layout;
    boolean doubleBackToExitPressedOnce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.ic_ab_drawer);

        mDrawerList = (ListView)findViewById(R.id.menuList);
        layout = (RelativeLayout)findViewById(R.id.drawerPane);

        boolean isLoggedIn = CFUserSessionManager.isUserLoggedIn(getApplicationContext());


        drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

//        CFPopupHelper.showProgressSpinner(this, View.VISIBLE);
        setListAdapter(isLoggedIn);

        updateProfile();

        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected)
        {
            new ServerAvailabilityTask().execute();
        }
        else {
            AlertDialog alert = CFPopupHelper.showAlertOneButton(HomeActivity.this, "Please turn on internet");
            alert.show();
        }
    }

    public void loadMinorData()
    {
        new MinorDataAsyncTask().execute();
    }

    @Override
    public void updateProfile()
    {
        TextView userName = (TextView)findViewById(R.id.userNameProfile);
        TextView userEmail = (TextView)findViewById(R.id.emailProfile);

        CFUser currentUser = CFUserSessionManager.getUser(getApplicationContext());

        if(currentUser!=null){
            userName.setText(currentUser.getUName());
            userEmail.setText(currentUser.getEmail());
        }
        else
        {
            userName.setText("Welcome Guest!");
            userEmail.setText("");
        }

    }

    public void setListAdapter(boolean isLoggedIn)
    {   final String[] listItems;
        if(!isLoggedIn)
        {
            listItems = new String[]{
                    getResources().getString(R.string.home),
                    getResources().getString(R.string.submitAd),
                    getResources().getString(R.string.favourites),
                    getResources().getString(R.string.login)
            };
        }
        else {
            listItems = new String[]{
                    getResources().getString(R.string.home),
                    getResources().getString(R.string.submitAd),
                    getResources().getString(R.string.favourites),
                    getResources().getString(R.string.logOut),
                    getResources().getString(R.string.my_ads)
            };
        }

        ArrayAdapter adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        mDrawerList.setAdapter(adapter);
        displayView(0, "");
        mDrawerList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                for(int a = 0; a < parent.getChildCount(); a++)
//                {
//                    parent.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
//                }

                displayView(position, listItems[position]);
            }
        });
    }


    public void displayView(int position, String tag) {
        Fragment fragment = null;
        switch (position)
        {
            case 0:
                fragment = HomeFragment.newInstance(CFConstants.FRAGMENT_HOME);
                break;
            case 1:
                if(CFConstants.minorDataLoaded()) {
                    fragment = new AdSubmissionFragment();
                }
                else {
                    CFPopupHelper.showToast(getApplicationContext(), getString(R.string.please_wait));
                }

                break;
            case 2:
                fragment = HomeFragment.newInstance(CFConstants.FRAGMENT_FAVOURITES);
                break;
            case 3:
                if(tag.equals(getResources().getString(R.string.login)))
                {
                    fragment = new LoginFragment();
                    setListAdapter(true);

                }
                else {
                    CFUserSessionManager.logoutUser(getApplicationContext());
                    updateProfile();
                    displayView(0,"");
                    setListAdapter(false);
                    closeDrawer();
                }
                break;
            case 4:
                fragment = HomeFragment.newInstance(CFConstants.FRAGMENT_USERADS);
                break;
        }

        if (fragment != null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
//            setTitle(navMenuTitles[position]);
            drawer.closeDrawer(layout);
        } else {

            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void closeDrawer(){
        drawer.closeDrawer(layout);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(Gravity.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
////Save the fragment's instance
//        getSupportFragmentManager().putFragment(outState, "mContent", mContent);
//
//
//    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private class MinorDataAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            CFConstants.LOCATIONS = CFMinorDataHandler.getLocations();
            CFConstants.BODY_TYPES = CFMinorDataHandler.getBodyTypes();
            CFConstants.BRANDS = CFMinorDataHandler.getBrands();
            CFConstants.CONDITION_TYPES = CFMinorDataHandler.getConditions();
            CFConstants.FUEL_TYPES = CFMinorDataHandler.getFuelTypes();
            CFConstants.TRANSMISSION_TYPES = CFMinorDataHandler.getTransmissionTypes();
            CFConstants.VEHICLE_TYPES = CFMinorDataHandler.getVehicleTypes();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
//            startActivities();
//            CFPopupHelper.showProgressSpinner(HomeActivity.this, View.GONE);
//            spinner.setVisibility(View.GONE);
        }
    }

    private class ServerAvailabilityTask extends AsyncTask<String, String, Boolean> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... params) {

            return CFHttpManager.isServerAvailable();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result) {
                loadMinorData();
            }
            else {
//                CFPopupHelper.showAlertOneButton(HomeActivity.this, "Server is not available. Please check your connection and restart the application").show();
            }
        }
    }
}
