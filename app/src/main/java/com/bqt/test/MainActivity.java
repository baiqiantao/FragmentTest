package com.bqt.test;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends ListActivity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle metaData = getMetaData(this);
		Object baiduMapKey = metaData.get("baiduMapKey");
		String[] array = {
				"Flavor：" + BuildConfig.FLAVOR,
				"applicationId：" + BuildConfig.APPLICATION_ID,
				"versionName：" + BuildConfig.VERSION_NAME,
				"versionCode：" + BuildConfig.VERSION_CODE,
				"buildType：" + BuildConfig.BUILD_TYPE,
				"debuggable：" + BuildConfig.DEBUG,
				"",
				"Fields from product flavor.\n是否是香港用户：" + BuildConfig.isHongkongUser,
				"Fields from product flavor.\n国家代码：" + BuildConfig.countryCode,
				"Fields from build type.\n域名：" + BuildConfig.BASE_URL,
				"",
				"Fields from metaData.\n打包时间：" + metaData.getString("releaseTime"),
				"Fields from metaData.\n渠道名称：" + metaData.getString("chanel"),
				"Fields from metaData.\n百度地图密钥：" + baiduMapKey + "  类型：" + baiduMapKey.getClass().getSimpleName(),
				"Fields from metaData.\n应用SHA1签名：" + getAppSignatureSHA1(this),
		};
		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(Arrays.asList(array))));
	}
	
	public static Bundle getMetaData(Context mContext) {
		try {
			ApplicationInfo applicationInfo = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
			return applicationInfo.metaData;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return new Bundle();
		}
	}
	
	public static String getAppSignatureSHA1(Context mContext) {
		Signature[] signature = getAppSignature(mContext);
		if (signature == null || signature.length <= 0) return "";
		String encryptSHA1 = encryptSHA1ToString(signature[0].toByteArray());
		return encryptSHA1.replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
	}
	
	public static Signature[] getAppSignature(Context mContext) {
		try {
			@SuppressLint("PackageManagerGetSignatures")
			PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_SIGNATURES);
			return packageInfo == null ? null : packageInfo.signatures;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String encryptSHA1ToString(final byte[] data) {
		return bytes2HexString(encryptSHA1(data));
	}
	
	private static String bytes2HexString(final byte[] bytes) {
		if (bytes == null) return "";
		int len = bytes.length;
		if (len <= 0) return "";
		char[] ret = new char[len << 1];
		for (int i = 0, j = 0; i < len; i++) {
			ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
			ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
		}
		return new String(ret);
	}
	
	private static byte[] encryptSHA1(final byte[] data) {
		if (data == null || data.length <= 0) return null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(data);
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
}