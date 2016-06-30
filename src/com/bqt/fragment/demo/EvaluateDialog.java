package com.bqt.fragment.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

/**这是在同一个Activity中，由另一个Fragment启动的一个DialogFragment*/
public class EvaluateDialog extends DialogFragment {
	private String[] mEvaluteVals = new String[] { "优", "差", "良" };

	/**返回的数据的Key或Tag或Name*/
	public static final String RESPONSE_EVALUATE = "response_evaluate";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("请选择评价 :").setItems(mEvaluteVals, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setResult(which);
			}
		});
		return builder.create();
	}

	// 设置返回数据
	protected void setResult(int which) {
		// 判断是否设置了targetFragment
		if (getTargetFragment() == null) return;
		Intent intent = new Intent();
		intent.putExtra(RESPONSE_EVALUATE, mEvaluteVals[which]);
		//手动调用onActivityResultt进行返回数据，【请求码：由目标Fragment决定】【响应码：由自身决定，要求不严格的话也可随便设置一个】
		getTargetFragment().onActivityResult(ContentFragment.REQUEST_CODE_EVALUATE, Activity.RESULT_OK, intent);
	}
}