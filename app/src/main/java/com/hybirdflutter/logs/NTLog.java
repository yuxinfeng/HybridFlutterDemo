package com.hybirdflutter.logs;

import android.app.Application;


import java.io.File;

import netease.wm.log.WMLog;

public final class NTLog {
	public static void init(Application context) {
		WMLog.init(context);
	}

	public static void trackEvent(@WMLog.Event int event, String... msg) {
		WMLog.trackEvent(event, msg);
	}

	public static File compressLogFiles() {
		return WMLog.compressLogFiles();
	}


	public static void v(String tag, String msg) {
		WMLog.v(tag, msg);
	}

	public static void d(String tag, String msg) {
		WMLog.d(tag, msg);
	}

	public static void i(String tag, String msg) {
		WMLog.i(tag, msg);
	}

	public static void w(String tag, String msg) {
		WMLog.w(tag, msg);
	}

	public static void e(String tag, String msg) {
		WMLog.e(tag, msg);
	}

	public static class UploadResult {
		String url = "";

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}
}

