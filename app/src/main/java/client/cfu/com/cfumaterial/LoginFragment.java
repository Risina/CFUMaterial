package client.cfu.com.cfumaterial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import client.cfu.com.base.CFUserSessionManager;
import client.cfu.com.constants.CFConstants;
import client.cfu.com.util.CFPopupHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
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

        View view = super.onCreateView(inflater, container, savedInstanceState);

        SharedPreferences prefs = getActivity().getSharedPreferences("com.cfu.user", Activity.MODE_PRIVATE);
        String userEmail = prefs.getString("userEmail", null);
        String userPassword = prefs.getString("userPw", null);

//        if(userEmail != null && userPassword != null) {
//            Intent intent = new Intent(this, FrontActivity.class);
//            startActivity(intent);
//        }
//        else {
            final EditText email = (EditText)view.findViewById(R.id.editTextEmail);
            final EditText password = (EditText)view.findViewById(R.id.editTextPassword);
            Button button = (Button)view.findViewById(R.id.loginButton);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String emailText = email.getText().toString();
                    String passwordText = password.getText().toString();
                    LoginAsyncTask asyncTask = new LoginAsyncTask(getActivity().getApplicationContext());
                    asyncTask.execute(emailText, passwordText);
                }
            });
//        }


        // Inflate the layout for this fragment
        return view;
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

    private class LoginAsyncTask extends AsyncTask<String, String, String> {
        Context appContext;

        public LoginAsyncTask(Context applicationContext)
        {
            appContext = applicationContext;
        }
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            CFUserSessionManager sessionManager = CFUserSessionManager.getInstance(appContext);
            return sessionManager.createUserLoginSession(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
//            loadLocations();
//            spinner.setVisibility(View.GONE);
//            SharedPreferences prefs = getSharedPreferences("com.cfu.user", MODE_PRIVATE);
//            String userE = prefs.getString("userEmail", "email");
//            String pw = prefs.getString("userPw", "userPw");
            String toastString = "";
            if(result.equals(CFConstants.STATUS_OK)) {
                toastString = getString(R.string.login_successful);
            }
            else {
                toastString = getString(R.string.login_failed);
            }
            CFPopupHelper.showToast(appContext, toastString);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_login;
    }


}
