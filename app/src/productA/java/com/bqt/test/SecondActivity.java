package com.bqt.test;

import android.app.Activity;
import android.os.Bundle;

//注意：这个 SecondActivity 不是在 main 目录里面定义的，也是各个产品目录(如productA)里面的 java/完整包名/... 中定义的
public class SecondActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_a);
	}
}