package com.sxau.agriculture.update;

public class Constants {

	protected static final String APP_UPDATE_SERVER_URL = "http://sxnk110.workerhub.cn:9000/api/v1/app/checkUpdate";
// json {"url":"","versionCode":2,"updateMessage":"版本更新信息"}
	//我这里服务器返回的json数据是这样的，可以根据实际情况修改下面参数的名称
	public static final String APK_DOWNLOAD_URL = "url";
	public static final String APK_UPDATE_CONTENT = "updateMessage";
	public static final String APK_VERSION_CODE = "versionCode";

}
