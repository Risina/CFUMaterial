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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import client.cfu.com.entities.CFAdvertisement;
import client.cfu.com.entities.CFEntityHelper;


public class DetailActivity extends BaseActivity {

    public static final String EXTRA_IMAGE = "DetailActivity:image";
    public static final String ADVERTISEMENT = "DetailActivity:advertisement";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView image = (ImageView) findViewById(R.id.image);
        ViewCompat.setTransitionName(image, EXTRA_IMAGE);
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(image);
        createAdvertisement(getIntent().getStringExtra(ADVERTISEMENT));
    }

    public void createAdvertisement(String jsonString)
    {
        TextView title = (TextView)findViewById(R.id.textViewTitle);
        TextView description = (TextView)findViewById(R.id.textViewDescription);
        TextView brand = (TextView)findViewById(R.id.textViewBrand);
        TextView condition = (TextView)findViewById(R.id.textViewCondition);
        TextView engine = (TextView)findViewById(R.id.textViewEngine);
        TextView fuel = (TextView)findViewById(R.id.textViewFuel);
        TextView mileage = (TextView)findViewById(R.id.textViewMileage);
        TextView model = (TextView)findViewById(R.id.textViewModel);
        TextView modelYear = (TextView)findViewById(R.id.textViewModelYear);
        TextView price = (TextView)findViewById(R.id.textViewPrice);
        TextView transmission = (TextView)findViewById(R.id.textViewTransmission);
        TextView location = (TextView)findViewById(R.id.textViewLocation);

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            CFAdvertisement ad = CFEntityHelper.getAdvertisementFromJSON(jsonObject, true);

            title.setText(ad.getTitle());
            description.setText(ad.getDescription());
            brand.setText(ad.getBrandId().getBrandString());
            condition.setText(ad.getConditionId().getConditionString());
            engine.setText(Integer.toString(ad.getEngineCapacity()));
            fuel.setText(ad.getFuelTypeId().getFuelTypeString());
            mileage.setText(Long.toString(ad.getMilage()));
            model.setText(ad.getModel());
            modelYear.setText(Long.toString(ad.getMilage()));
            price.setText(Long.toString(ad.getPrice()));
            transmission.setText(ad.getTransmissionTypeId().getTransmissionTypeString());
            location.setText(ad.getUserId().getLocationId().getLocationString());
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }



    @Override protected int getLayoutResource() {
        return R.layout.activity_detail;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void launch(BaseActivity activity, View transitionView, String url, CFAdvertisement ad) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, EXTRA_IMAGE);
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, url);
        intent.putExtra(ADVERTISEMENT, ad.toJson());
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
