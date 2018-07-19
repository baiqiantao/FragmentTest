package com.bqt.test;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends ListActivity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle metaData = getMetaData(this);
		String[] array = {
				"applicationId：" + BuildConfig.APPLICATION_ID + "\nApp的包名：" + getPackageName()
						+ "\n类的限定类名：" + getLocalClassName() + "  \n类所在的包名：" + getClass().getPackage().getName(),
				"Flavor：" + BuildConfig.FLAVOR,
				"versionName：" + BuildConfig.VERSION_NAME,
				"versionCode：" + BuildConfig.VERSION_CODE,
				"buildType：" + BuildConfig.BUILD_TYPE,
				"debuggable：" + BuildConfig.DEBUG,
				"",
				"buildConfigField，是否是香港用户：" + BuildConfig.isHongkongUser,
				"buildConfigField，国家代码：" + BuildConfig.countryCode,
				"buildConfigField，域名：" + BuildConfig.BASE_URL,
				"",
				"meta-data，打包时间：" + metaData.getString("releaseTime"),
				"meta-data，渠道名称：" + metaData.getString("chanel"),
				"meta-data，百度地图密钥：" + metaData.getInt("baiduMapKey"),//注意这里是 int 类型
				"",
				"resValue，应用名称：" + getResources().getString(R.string.app_icon_name),
				"resValue，设置的颜色：" + Integer.toHexString(getResources().getColor(R.color.color_footer)),
				"",
				"应用SHA1签名：" + getAppSignatureSHA1(this),
		};
		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(Arrays.asList(array))));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		//注意：这个 SecondActivity 不是在 main 目录里面定义的，也是各个产品目录(如productA)里面的 java/完整包名/... 中定义的
		startActivity(new Intent(this, SecondActivity.class));
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