package com.sxau.agriculture.update;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.JsonObject;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.IVersionUpdate;
import com.sxau.agriculture.utils.RetrofitUtil;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UpdateChecker extends Fragment {

    //private static final String NOTIFICATION_ICON_RES_ID_KEY = "resId";
    private static final String NOTICE_TYPE_KEY = "type";
    private static final String APP_UPDATE_SERVER_URL = "app_update_server_url";
    //private static final String SUCCESSFUL_CHECKS_REQUIRED_KEY = "nChecks";
    private static final int NOTICE_NOTIFICATION = 2;
    private static final int NOTICE_DIALOG = 1;
    private static final String TAG = "UpdateChecker";

    private FragmentActivity mContext;
    private Thread mThread;
    private int mTypeOfNotice;
    private MyHandler handler=new MyHandler();
    private String getApkUrl;
    private int getApkCode;
    private  String updateMessage;

    /**
     * Show a Dialog if an update is available for download. Callable in a
     * FragmentActivity. Number of checks after the dialog will be shown:
     * default, 5
     *
     * @param fragmentActivity Required.
     */
    public static void checkForDialog(FragmentActivity fragmentActivity, String url) {
        FragmentTransaction content = fragmentActivity.getSupportFragmentManager().beginTransaction();
        UpdateChecker updateChecker = new UpdateChecker();
        Bundle args = new Bundle();
        args.putInt(NOTICE_TYPE_KEY, NOTICE_DIALOG);
        args.putString(APP_UPDATE_SERVER_URL, url);
        //args.putInt(SUCCESSFUL_CHECKS_REQUIRED_KEY, 5);
        updateChecker.setArguments(args);
        content.add(updateChecker, null).commit();
    }


    /**
     * Show a Notification if an update is available for download. Callable in a
     * FragmentActivity Specify the number of checks after the notification will
     * be shown.
     *
     * @param fragmentActivity Required.
     */
    public static void checkForNotification(FragmentActivity fragmentActivity, String url) {
        FragmentTransaction content = fragmentActivity.getSupportFragmentManager().beginTransaction();
        UpdateChecker updateChecker = new UpdateChecker();
        Bundle args = new Bundle();
        args.putInt(NOTICE_TYPE_KEY, NOTICE_NOTIFICATION);
        args.putString(APP_UPDATE_SERVER_URL, url);
        //args.putInt(NOTIFICATION_ICON_RES_ID_KEY, notificationIconResId);
        //args.putInt(SUCCESSFUL_CHECKS_REQUIRED_KEY, 5);
        updateChecker.setArguments(args);
        content.add(updateChecker, null).commit();
    }


    /**
     * This class is a Fragment. Check for the method you have chosen.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (FragmentActivity) activity;
        Bundle args = getArguments();
        mTypeOfNotice = args.getInt(NOTICE_TYPE_KEY);
        String url = args.getString(APP_UPDATE_SERVER_URL);
        //mSuccessfulChecksRequired = args.getInt(SUCCESSFUL_CHECKS_REQUIRED_KEY);
        //mNotificationIconResId = args.getInt(NOTIFICATION_ICON_RES_ID_KEY);
        checkForUpdates(url);
    }

    /**
     * Heart of the library. Check if an update is available for download
     * parsing the desktop Play Store page of the app
     */
    private void checkForUpdates(final String url) {
        mThread = new Thread() {
            @Override
            public void run() {
                parseJson();
                //if (isNetworkAvailable(mContext)) {

              /*  String json = sendPost(url);
                if (json != null) {
                    parseJson(json);
                    Log.e("updateMessage", "json");
                } else {
                    Log.e(TAG, "can't get app update json");
                }*/
                //}
            }

        };
        mThread.start();
    }

    //TODO: 2016/6/29 获取JSON 有问题
    /*protected String sendPost(String urlStr) {
        HttpURLConnection uRLConnection = null;
        InputStream is = null;
        BufferedReader buffer = null;
        String result = null;
        try {
            URL url = new URL(urlStr);
            uRLConnection = (HttpURLConnection) url.openConnection();
            uRLConnection.setDoInput(false);
            uRLConnection.setDoOutput(true);
            uRLConnection.setRequestMethod("GET");
            uRLConnection.setUseCaches(false);
            uRLConnection.setConnectTimeout(10 * 1000);
            uRLConnection.setReadTimeout(10 * 1000);
            uRLConnection.setInstanceFollowRedirects(false);
  *//*          uRLConnection.setRequestProperty("Connection", "Keep-Alive");
            uRLConnection.setRequestProperty("Charset", "UTF-8");
            uRLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");*//*
            uRLConnection.setRequestProperty("Access-Control-Allow-Headers", "X-Requested-With,Content-Type,Accept,X-AUTH-TOKEN,VERIFY_UUID,WECHAT-AUTH-TOKEN");
            uRLConnection.setRequestProperty("Access-Control-Allow-Origin", "*");
//            uRLConnection.setRequestProperty("Accept-Encoding", "*");
//            uRLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            uRLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            uRLConnection.connect();
            Log.e(TAG, "http post error  get1");

            is = uRLConnection.getInputStream();
            Log.e(TAG, "http post error  get2");
            String content_encode = uRLConnection.getContentEncoding();
            Log.e(TAG, "http post error  get3" + content_encode);
            if (null != content_encode && !"".equals(content_encode) && content_encode.equals("gzip")) {
                is = new GZIPInputStream(is);
            }

            buffer = new BufferedReader(new InputStreamReader(is));
            StringBuilder strBuilder = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                strBuilder.append(line);
                Log.e(TAG, "http post error  get3");
            }
            result = strBuilder.toString();
        } catch (Exception e) {
            Log.e(TAG, "http post error", e);
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (uRLConnection != null) {
                uRLConnection.disconnect();
            }
        }
        return result;
    }*/


    //    private void parseJson(String json) {
    private void parseJson() {
//        mThread.interrupt();
//        Looper.prepare();
        Call<JsonObject> call = RetrofitUtil.getRetrofit().create(IVersionUpdate.class).getUpdateJson();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                try {
                    JsonObject json = response.body();
                    Log.e("updateMessage", json.toString());
                     updateMessage = String.valueOf(json.getAsJsonPrimitive(Constants.APK_UPDATE_CONTENT));
                    Log.e("updateMessage", updateMessage);
//                String apkUrl = json.getString(Constants.APK_DOWNLOAD_URL);
                    getApkUrl  = String.valueOf(json.getAsJsonPrimitive(Constants.APK_DOWNLOAD_URL));
//                    Log.e("updateMessage", apkUrl);
                     getApkCode = Integer.parseInt(String.valueOf(json.getAsJsonPrimitive(Constants.APK_VERSION_CODE)));
//                    Log.e("updateMessage", apkCode + "");
          /*  JSONObject obj = new JSONObject(json);
            String updateMessage = obj.getString(Constants.APK_UPDATE_CONTENT);
            Log.e("updateMessage", updateMessage);
            String apkUrl = obj.getString(Constants.APK_DOWNLOAD_URL);
            Log.e("updateMessage", apkUrl);
            int apkCode = obj.getInt(Constants.APK_VERSION_CODE);
            Log.e("updateMessage", apkCode + "");*/

                    int versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;

                    if (getApkCode > versionCode) {
                        if (mTypeOfNotice == NOTICE_NOTIFICATION) {
//                            showNotification(updateMessage, apkUrl);
                        } else if (mTypeOfNotice == NOTICE_DIALOG) {
                            handler.sendEmptyMessage(0);
//                            Log.e("apkUrl",apkUrl);
                        }
                    } else {
                        //Toast.makeText(mContext, mContext.getString(R.string.app_no_new_update), Toast.LENGTH_SHORT).show();
                    }

                } catch (PackageManager.NameNotFoundException ignored) {
                }

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                showDialog(updateMessage, getApkUrl);
                    break;
            }
        }
    }

    /**
     * Show dialog
     */
    public void showDialog(String content, String apkUrl) {
        UpdateDialog d = new UpdateDialog();
        Bundle args = new Bundle();
        args.putString(Constants.APK_UPDATE_CONTENT, content);
        args.putString(Constants.APK_DOWNLOAD_URL, apkUrl);
        Log.e("getArguments1", apkUrl);
        d.setArguments(args);
       Log.e("getArgumentsValue", d.getArguments().getString(Constants.APK_DOWNLOAD_URL));
        d.show(mContext.getSupportFragmentManager(), null);
    }

    /**
     * Show Notification
     */
    public void showNotification(String content, String apkUrl) {
        Notification noti;
        Intent myIntent = new Intent(mContext, ExeDownloadService.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra(Constants.APK_DOWNLOAD_URL, apkUrl);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int smallIcon = mContext.getApplicationInfo().icon;
        noti = new NotificationCompat.Builder(mContext).setTicker(getString(R.string.newUpdateAvailable))
                .setContentTitle(getString(R.string.newUpdateAvailable)).setContentText(content).setSmallIcon(smallIcon)
                .setContentIntent(pendingIntent).build();

        noti.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }

    /**
     * Check if a network available
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                connected = ni.isConnected();
            }
        }
        return connected;
    }


}
