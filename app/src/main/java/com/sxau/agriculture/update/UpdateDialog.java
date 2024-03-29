package com.sxau.agriculture.update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.qiniu.android.utils.StringUtils;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.utils.StringUtil;

public class UpdateDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.newUpdateAvailable);
        builder.setMessage(StringUtil.subStringToNoMark(getArguments().getString(Constants.APK_UPDATE_CONTENT)))
                .setPositiveButton(R.string.dialogPositiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        goToDownload();
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.dialogNegativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


    private void goToDownload() {
    	Intent intent=new Intent(getActivity().getApplicationContext(),ExeDownloadService.class);
    	intent.putExtra(Constants.APK_DOWNLOAD_URL, getArguments().getString(Constants.APK_DOWNLOAD_URL));
        Log.e("APK_DOWNLOAD_URL",""+getArguments().getString(Constants.APK_DOWNLOAD_URL));
    	getActivity().startService(intent);
    }
}
