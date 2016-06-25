package com.sxau.agriculture.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sxau.agriculture.view.activity.DetailQuestionActivity;
import com.sxau.agriculture.view.activity.TradeContentActivity;
import com.sxau.agriculture.view.activity.WebViewActivity;
import com.sxau.agriculture.view.activity.WebViewTwoActivity;
import com.sxau.agriculture.view.fragment.MessageFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 *
 * @author 高海龙
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//        	processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String id = null;
            String type = null;
            try {
                //TODO fragment是否能跳转
                JSONObject extrasJson = new JSONObject(extras);
                type = extrasJson.getString("type");
                id = extrasJson.getString("id");
                Intent intentStart = new Intent();
                switch (type) {
                    case ConstantUtil.QUESTION://问答
                        intentStart.setClass(context, DetailQuestionActivity.class);
                        intentStart.putExtra("indexPosition", id);
                        break;
                    case ConstantUtil.TRADE://交易
                        intentStart.setClass(context, TradeContentActivity.class);
                        intentStart.putExtra("TradeId", id);
                        break;
                    case ConstantUtil.ARTICLE://文章
                        intentStart.setClass(context, WebViewTwoActivity.class);
                        intentStart.putExtra("article", id);
                        break;
                    case ConstantUtil.RELATION://关系
                        intentStart.setClass(context, MessageFragment.class);
                        break;
                    case ConstantUtil.SYSTEM://系统
                        intentStart.setClass(context, MessageFragment.class);
                        break;
                    case ConstantUtil.WECHAT://微信
                        intentStart.setClass(context, MessageFragment.class);
                        break;
                    case ConstantUtil.NOTICE://公告
                        intentStart.setClass(context, MessageFragment.class);
                        break;
                    default:
                        break;
                }
                context.startActivity(intentStart);
            } catch (JSONException e) {
                e.printStackTrace();
            }
          /*  String type = intent.getStringExtra("type");
            String id = intent.getStringExtra("id");
            Log.e("getType",type);
            Log.e("getId",type);
//            bundle.getString(JPushInterface.EXTRA_EXTRA);
            //TODO 将要选择跳转的activity写到这*/
       /* 	//打开自定义的Activity 应该是打开内容的activity
            Intent i = new Intent(context, TestActivity.class);
        	i.putExtras(bundle);
        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        	context.startActivity(i);*/

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } /*else if (JPushInterface.EXTRA_EXTRA.equals(intent.getAction())) {
            String type = intent.getStringExtra("type");
            String id = intent.getStringExtra("id");
            if (type.equals("QUESTION")) {

            } else if (type.equals("RELATION")) {

            } else if (type.equals("SYSTEM")) {

            } else if (type.equals("WECHAT")) {

            } else if (type.equals("NOTICE")) {

            } else if (type.equals("TRADE")) {

            } else {

            }
        } */ else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    /*private void processCustomMessage(Context context, Bundle bundle) {
        if (MainActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
			if (!JPushUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (null != extraJson && extraJson.length() > 0) {
						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			context.sendBroadcast(msgIntent);
		}
	}*/
}
