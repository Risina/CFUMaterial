package client.cfu.com.cfumaterial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import client.cfu.com.base.CFAdvertisementDataHandler;
import client.cfu.com.base.CFUserSessionManager;
import client.cfu.com.constants.CFConstants;
import client.cfu.com.entities.CFAdvertisement;
import client.cfu.com.entities.CFAdvertisementObj;
import client.cfu.com.util.CFPopupHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AdSubmissionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AdSubmissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdSubmissionFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ImageView imageview;
    Bitmap bp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdSubmissionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdSubmissionFragment newInstance(String param1, String param2) {
        AdSubmissionFragment fragment = new AdSubmissionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AdSubmissionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = super.onCreateView(inflater, container, savedInstanceState);

        imageview = (ImageView)view.findViewById(R.id.carImage);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });

        final FormEditText titleText = (FormEditText)view.findViewById(R.id.titleText);
        final EditText descriptionText = (EditText)view.findViewById(R.id.descriptionText);
        final EditText modelText = (EditText)view.findViewById(R.id.modelText);
        final EditText modelYearText = (EditText)view.findViewById(R.id.modelYearText);
        final EditText engineCapacityText = (EditText)view.findViewById(R.id.engineCapacityText);
        final EditText mileageText = (EditText)view.findViewById(R.id.mileageText);

        final Spinner brandSpinner = (Spinner)view.findViewById(R.id.brand_spinner);
        final Spinner bodyTypeSpinner = (Spinner)view.findViewById(R.id.bodyType_spinner);
        final Spinner transmissionSpinner = (Spinner)view.findViewById(R.id.transmission_spinner);
        final Spinner conditionSpinner = (Spinner)view.findViewById(R.id.condition_spinner);
        final Spinner fuelTypeSpinner = (Spinner)view.findViewById(R.id.fuelType_spinner);
        final Spinner vehicleTypeSpinner = (Spinner)view.findViewById(R.id.vehicleType_spinner);

        Button submitButton = (Button)view.findViewById(R.id.submitButton);

        brandSpinner.setAdapter(new ListAdapter(getActivity(), CFConstants.BRANDS));
        bodyTypeSpinner.setAdapter(new ListAdapter(getActivity(), CFConstants.BODY_TYPES));
        transmissionSpinner.setAdapter(new ListAdapter(getActivity(), CFConstants.TRANSMISSION_TYPES));
        conditionSpinner.setAdapter(new ListAdapter(getActivity(), CFConstants.CONDITION_TYPES));
        fuelTypeSpinner.setAdapter(new ListAdapter(getActivity(), CFConstants.FUEL_TYPES));
        vehicleTypeSpinner.setAdapter(new ListAdapter(getActivity(), CFConstants.VEHICLE_TYPES));


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FormEditText[] allFields    = { titleText};


                boolean allValid = true;
                for (FormEditText field: allFields) {
                    allValid = field.testValidity() && allValid;
                }

                if (allValid) {
                    CFAdvertisementObj ad = new CFAdvertisementObj();
                    ad.setTitle(titleText.getText().toString());
                    ad.setDescription(descriptionText.getText().toString());
                    ad.setModel(modelText.getText().toString());
                    ad.setModelYear(Short.parseShort(modelYearText.getText().toString()));
                    ad.setEngineCapacity(Integer.parseInt(engineCapacityText.getText().toString()));
                    ad.setMilage(Long.parseLong(mileageText.getText().toString()));
                    ad.setBrandId(safeLongToInt(brandSpinner.getSelectedItemId()) + 1);
                    ad.setBodyTypeId(safeLongToInt(bodyTypeSpinner.getSelectedItemId()) + 1);
                    ad.setTransmissionTypeId(safeLongToInt(transmissionSpinner.getSelectedItemId()) + 1);
                    ad.setFuelTypeId(safeLongToInt(fuelTypeSpinner.getSelectedItemId()) + 1);
                    ad.setConditionId(safeLongToInt(conditionSpinner.getSelectedItemId()) + 1);
                    ad.setVehicleTypeId(safeLongToInt(vehicleTypeSpinner.getSelectedItemId()) + 1);
                    ad.setUserId((int) CFUserSessionManager.getUserId(getActivity().getApplicationContext()));

                    if(bp!= null){
                        ad.setImage(bp);
                    }

                    ad.setImageName(ad.getTitle()+UUID.randomUUID());

                    new AdSubmissionAsyncTask(getActivity().getApplicationContext(), ad).execute();
                } else {
                    // EditText are going to appear with an exclamation mark and an explicative message.
                }

            }
        });


        return view;
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    public void open(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null)
        {
            bp = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(bp);
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_ad_submission;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class ListAdapter extends BaseAdapter implements SpinnerAdapter {

        List<JSONObject> allLists;
        Context context;
        LayoutInflater inflater;

        public ListAdapter(List<JSONObject> list){
            allLists = list;
        }

        public ListAdapter(Context context, List<JSONObject> items) {
            super();
            this.context = context;
            this.allLists = items;

            inflater = (LayoutInflater)context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return allLists.size();
        }

        @Override
        public Object getItem(int position) {
            try {
                return allLists.get(position).get("string");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            View v = view;
            if (v == null) {
//                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.minor_data, null);

            }

            if (allLists.get(position) != null) {
                TextView textView = (TextView) v.findViewById(R.id.txt);
                try {

                    textView.setText((String) allLists.get(position).get("string"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return v;
        }

    }

    private class AdSubmissionAsyncTask extends AsyncTask<String, String, Boolean> {
        Context appContext;
        CFAdvertisementObj adObj;

        public AdSubmissionAsyncTask(Context applicationContext, CFAdvertisementObj adObj)
        {
            appContext = applicationContext;
            this.adObj = adObj;
        }
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... params) {
            CFAdvertisementDataHandler dataHandler = new CFAdvertisementDataHandler();
            return dataHandler.addAdvertisement(adObj);
        }

        @Override
        protected void onPostExecute(Boolean result) {

            String toastText;
            if(result)
            {
                toastText = getResources().getString(R.string.successful);
            }
            else {
                toastText = getResources().getString(R.string.failed);
            }

            CFPopupHelper.showToast(appContext, toastText);
        }
    }


}
