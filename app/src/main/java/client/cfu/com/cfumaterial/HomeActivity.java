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
import android.app.FragmentManager;
import android.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import client.cfu.com.base.CFAdvertisementDataHandler;
import client.cfu.com.base.CFMinorDataHandler;
import client.cfu.com.base.CFUserSessionManager;
import client.cfu.com.constants.CFConstants;
import client.cfu.com.entities.CFAdvertisement;
import client.cfu.com.util.CFPopupHelper;


public class HomeActivity extends BaseActivity {

    private DrawerLayout drawer;
    private List<CFAdvertisement> adList;
    ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.ic_ab_drawer);

        mDrawerList = (ListView)findViewById(R.id.menuList);

        boolean isLoggedIn = CFUserSessionManager.isUserLoggedIn(getApplicationContext());


        setListAdapter(isLoggedIn);

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

        CFPopupHelper.showProgressSpinner(this, View.VISIBLE);
        new DataAsyncTask().execute();
        new MinorDataAsyncTask().execute();

//        new Drawer()
//                .withActivity(this)
//                .addDrawerItems(
//
//                )
//                .withDrawerGravity(Gravity.END)
//        .build();
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
                    getResources().getString(R.string.logOut)
            };
        }

        ArrayAdapter adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(position, listItems[position]);
            }
        });

    }

    public void createList()
    {
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new GridViewAdapter(adList));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = (String) view.getTag();
                DetailActivity.launch(HomeActivity.this, view.findViewById(R.id.image), url, adList.get(i));
            }
        });
    }

    public void displayView(int position, String tag) {
        Fragment fragment = null;

        switch (position)
        {
            case 0:
                finish();
                startActivity(getIntent());
                break;
            case 1:
                fragment = new AdSubmissionFragment();
                break;
            case 3:
                if(tag.equals(getResources().getString(R.string.login)))
                {
                    fragment = new LoginFragment();
                    setListAdapter(true);

                }
                else {
                    CFUserSessionManager.logoutUser(getApplicationContext());
                    setListAdapter(false);
                    closeDrawer();

                }
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
            drawer.closeDrawer(mDrawerList);
        } else {

            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void closeDrawer(){
        drawer.closeDrawer(mDrawerList);
    }


    @Override protected int getLayoutResource() {
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

    private static class GridViewAdapter extends BaseAdapter {

        Context context;
        List<CFAdvertisement> items;

//        public GridViewAdapter(){};
        public GridViewAdapter(List<CFAdvertisement> items) {
            super();
//            super(context, textViewResourceId, items);
//            this.context = context;
            this.items = items;
        }


        @Override public int getCount() {
            return 10;
        }

        @Override public Object getItem(int i) {
            return "Item " + String.valueOf(i + 1);
        }

        @Override public long getItemId(int i) {
            return i;
        }

        @Override public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.grid_item, viewGroup, false);
            }

            String imageUrl = CFConstants.SERVICE_ROOT+"CFUDBService/images/cfu/"+String.valueOf(i + 1)+".jpg";
            view.setTag(imageUrl);

            ImageView image = (ImageView) view.findViewById(R.id.image);
            Picasso.with(view.getContext())
                    .load(imageUrl)
                    .into(image);

            TextView text = (TextView) view.findViewById(R.id.text);
            text.setText(items.get(i).getTitle());

            return view;
        }
    }

    private class DataAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            CFAdvertisementDataHandler adh = new CFAdvertisementDataHandler();
            adList = adh.getAdvertisements();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            createList();
//            CFPopupHelper.showProgressSpinner(HomeActivity.this, View.GONE);
//            spinner.setVisibility(View.GONE);
        }
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
            CFPopupHelper.showProgressSpinner(HomeActivity.this, View.GONE);
//            spinner.setVisibility(View.GONE);
        }
    }
}
