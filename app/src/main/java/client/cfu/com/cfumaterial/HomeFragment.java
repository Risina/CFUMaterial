package client.cfu.com.cfumaterial;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import client.cfu.com.base.CFAdvertisementDataHandler;
import client.cfu.com.base.CFHttpManager;
import client.cfu.com.base.CFMinorDataHandler;
import client.cfu.com.base.CFUserSessionManager;
import client.cfu.com.constants.CFConstants;
import client.cfu.com.entities.CFAdvertisement;
import client.cfu.com.util.CFPopupHelper;
import client.cfu.com.util.EndlessScrollListener;


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
    private static final String ARG_PARAM1 = "isFav";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;

    private List<CFAdvertisement> adList;
    //    private List<CFAdvertisement> favAdList;
    View view;

    boolean isFavourites;
    ProgressBar pb;

    long from;
    long to;

    boolean stopScrolling;
    GridViewAdapter adapter;

    DataAsyncTask task;

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(boolean isFavourites) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isFavourites);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adList = new ArrayList<>();
        if (getArguments() != null) {
            isFavourites = getArguments().getBoolean(ARG_PARAM1);
        }
        from = 0;
        to = 10;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = super.onCreateView(inflater, container, savedInstanceState);

        pb = (ProgressBar) view.findViewById(R.id.spinner);
        pb.setVisibility(View.VISIBLE);

        if (isFavourites) {

            if (CFUserSessionManager.isUserLoggedIn(getActivity().getApplicationContext())) {
                new FavouriteAsyncTask().execute();
            } else {
                pb.setVisibility(View.GONE);
                CFPopupHelper.showToast(getActivity().getApplicationContext(), "You need to login to add/view favourites");
            }

        } else {
            task = new DataAsyncTask(from, to);
            task.execute();
            from = to+1;
            to = from+10;
        }

        createList(view);


        return view;
    }

    public void createList(View view) {

        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        adapter = new GridViewAdapter(adList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = (String) view.getTag();
                DetailActivity.launch((BaseActivity) getActivity(), view.findViewById(R.id.image), url, adList.get(i));
            }
        });
        gridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                if(!stopScrolling && !isFavourites){
                    pb.setVisibility(View.VISIBLE);
                    task = new DataAsyncTask(from, to);
                    task.execute();
                    from = to+1;
                    to = from+10;
                }
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

    private class GridViewAdapter extends BaseAdapter {

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
            return adList.size();
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

            String path = items.get(i).getImageLocation();
            String fileName = path.substring(path.lastIndexOf("/") + 1);

            String imageUrl = CFConstants.SERVICE_ROOT + "CFUDBService/GetImageServlet?img=" + fileName.replace("\"", "");
            view.setTag(imageUrl);

            ImageView image = (ImageView) view.findViewById(R.id.image);
            Picasso.with(view.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher)
                    .into(image);

            TextView text = (TextView) view.findViewById(R.id.text);
            TextView textPrice = (TextView) view.findViewById(R.id.textPrice);
            TextView textLocation = (TextView) view.findViewById(R.id.textLocation);


            text.setText(items.get(i).getId().toString());
            textPrice.setText(CFConstants.CURRENCY + Long.toString(items.get(i).getPrice()));
            textLocation.setText(items.get(i).getUserId().getLocationId().getLocationString());

            return view;
        }
    }

    private class DataAsyncTask extends AsyncTask<String, String, String> {

        long from;
        long to;

        public DataAsyncTask(long from, long to) {
            this.from = from;
            this.to = to;
        }

        @Override
        protected String doInBackground(String... params) {


            CFAdvertisementDataHandler adh = new CFAdvertisementDataHandler();
//                adList = adh.getAdvertisements();
            List<CFAdvertisement> list = adh.getAdvertisementsByRange(this.from, this.to);

            if (list.size() > 0) {
                adList.addAll(list);
            }

            return CFConstants.STATUS_OK;


        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals(CFConstants.STATUS_OK)) {
                adapter.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
//                createList(view);
            } else {
                CFPopupHelper.showAlertOneButton(getActivity(), "Server is not available. Please check your connection and restart the application").show();
            }

        }
    }

    private class FavouriteAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            CFAdvertisementDataHandler adh = new CFAdvertisementDataHandler();
            List<Long> list = adh.getFavourites(CFUserSessionManager.getUserId(getActivity().getApplicationContext()));

            adList = new ArrayList<>();
            for (Long adId : list) {
                adList.add(adh.getAdvertisementById(adId));
            }

            if (adList.size() > 0) {
                return CFConstants.STATUS_OK;
            } else {
                stopScrolling = true;
            }

            return CFConstants.STATUS_ERROR;
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equals(CFConstants.STATUS_OK)) {
                createList(view);
            } else {
                CFPopupHelper.showToast(getActivity().getApplicationContext(), "You don't have any favourites.");
            }
            pb.setVisibility(View.GONE);
//            CFPopupHelper.showProgressSpinner(HomeActivity.this, View.GONE);
        }
    }

}
