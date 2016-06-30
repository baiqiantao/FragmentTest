package com.bqt.fragment.demo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class ContentFragment extends Fragment {

	/**构造时传入的值**/
	private String mArgument;
	/**构造时传入的参数**/
	public static final String ARGUMENT = "argument";

	/**当前Fragment返回结果时的返回值对应的Tag*/
	public static final String RESPONSE = "response";

	/**为启动的DialogFragment设置一个Tag，方便以后通过findFragmentByTag(tag)找到此Fragment*/
	public static final String EVALUATE_DIALOG = "evaluate_dialog";

	/**启动另一个Fragment时的请求码*/
	public static final int REQUEST_CODE_EVALUATE = 0X110;

	/**构造时把传入的参数带进来*/
	public static ContentFragment newInstance(String argument) {
		Bundle bundle = new Bundle();
		bundle.putString(ARGUMENT, argument);
		ContentFragment contentFragment = new ContentFragment();
		contentFragment.setArguments(bundle);
		return contentFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			mArgument = bundle.getString(ARGUMENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		TextView tv = new TextView(getActivity());
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		tv.setLayoutParams(params);
		tv.setText(mArgument);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);//单位为sp
		tv.setGravity(Gravity.CENTER);
		tv.setBackgroundColor(0xccffff00);
		//两个Fragment在同一个Activity中，在一个Fragment中启动另一个Fragment
		tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EvaluateDialog dialog = new EvaluateDialog();
				//注意设置setTargetFragment，参数表示【此Fragment返回时的目标Fragment】【启动此Fragment时的请求码】
				dialog.setTargetFragment(ContentFragment.this, REQUEST_CODE_EVALUATE);
				dialog.show(getFragmentManager(), EVALUATE_DIALOG);
			}
		});
		return tv;
	}

	@Override
	//当另一个Fragment结束时，接收其返回回来的数据
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_EVALUATE) {
			//根据返回数据的Fragment返回的数据中的Tag，获取返回数据中我们需要的值
			String evaluate = data.getStringExtra(EvaluateDialog.RESPONSE_EVALUATE);
			Toast.makeText(getActivity(), "你的评价为：" + evaluate, Toast.LENGTH_SHORT).show();

			//将获取到的结果返回
			Intent intent = new Intent();
			intent.putExtra(RESPONSE, evaluate);
			getActivity().setResult(ListTitleFragment.REQUEST_DETAIL, intent);//第一个参数代表结果码，在启动者中定义
		}

	}
}
