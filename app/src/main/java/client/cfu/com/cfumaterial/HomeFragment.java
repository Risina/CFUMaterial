package client.cfu.com.cfumaterial;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import client.cfu.com.base.CFAdvertisementDataHandler;
import client.cfu.com.base.CFMinorDataHandler;
import client.cfu.com.constants.CFConstants;
import client.cfu.com.entities.CFAdvertisement;
import client.cfu.com.util.CFPopupHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private List<CFAdvertisement> adList;
    View view;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
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

        view = super.onCreateView(inflater, container, savedInstanceState);;
        new DataAsyncTask().execute();
        return view;
    }

    public void createList(View view)
    {
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setAdapter(new GridViewAdapter(adList));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = (String) view.getTag();
                DetailActivity.launch((BaseActivity)getActivity(), view.findViewById(R.id.image), url, adList.get(i));
            }
        });
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
        return R.layout.fragment_home;
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


        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return "Item " + String.valueOf(i + 1);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

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
            createList(view);
//            CFPopupHelper.showProgressSpinner(HomeActivity.this, View.GONE);
//            spinner.setVisibility(View.GONE);
        }
    }


//    private class MinorDataAsyncTask extends AsyncTask<String, String, String> {
//        @Override
//        protected void onPreExecute() {
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            CFConstants.LOCATIONS = CFMinorDataHandler.getLocations();
//            CFConstants.BODY_TYPES = CFMinorDataHandler.getBodyTypes();
//            CFConstants.BRANDS = CFMinorDataHandler.getBrands();
//            CFConstants.CONDITION_TYPES = CFMinorDataHandler.getConditions();
//            CFConstants.FUEL_TYPES = CFMinorDataHandler.getFuelTypes();
//            CFConstants.TRANSMISSION_TYPES = CFMinorDataHandler.getTransmissionTypes();
//            CFConstants.VEHICLE_TYPES = CFMinorDataHandler.getVehicleTypes();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
////            startActivities();
//            CFPopupHelper.showProgressSpinner(HomeActivity.this, View.GONE);
////            spinner.setVisibility(View.GONE);
//        }
//    }

}
