package client.cfu.com.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.widget.ProgressBar;
import android.widget.Toast;

import client.cfu.com.cfumaterial.R;


/**
 * Created by Risina on 4/14/15.
 */
public class CFPopupHelper extends DialogFragment{

    private static ProgressBar spinner;
    public static void showToast(Context applicationContext, String text){
        Toast.makeText(applicationContext, text,
                Toast.LENGTH_LONG).show();
    }

    public static AlertDialog showAlertOneButton(final Activity activity, String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(text)
                .setTitle(R.string.app_name);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                activity.getApplicationContext().startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });

        return builder.create();
    }

    public static AlertDialog showAlertTwoButtons(final Activity activity, String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(text)
                .setTitle(R.string.app_name);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                activity.getApplicationContext().startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        return builder.create();
    }

    public static void showProgressSpinner(Activity activity, int status){
        ProgressBar pb = (ProgressBar)activity.findViewById(R.id.spinner);
        pb.setVisibility(status);
    }

}
