package com.bqt.fragment.demo;

import java.util.Arrays;
import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListTitleFragment extends ListFragment {

	/**结果码*/
	public static final int REQUEST_DETAIL = 0x110;
	
	private List<String> mTitles = Arrays.asList("Hello", "World", "Android");
	private int mCurrentPos;
	private ArrayAdapter<String> mAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mTitles));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mCurrentPos = position;
		Intent intent = new Intent(getActivity(), ContentActivity.class);
		intent.putExtra(ContentFragment.ARGUMENT, mTitles.get(position));
		startActivityForResult(intent, REQUEST_DETAIL);
	}

	@Override
	//获取由此Fragment启动的另一个Activity中的一个ContentFragment结束时，ContentFragment返回的数据
	//注意：这里的ContentFragment中的数据又是由另一个EvaluateDialog结束时返回的
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_DETAIL && data != null) {
			String responseEvaluate = data.getStringExtra(ContentFragment.RESPONSE);
			if (responseEvaluate == null || responseEvaluate == "") responseEvaluate = "你还没有评价";
			mTitles.set(mCurrentPos, mTitles.get(mCurrentPos) + " -- " + responseEvaluate);
			mAdapter.notifyDataSetChanged();
		} else Toast.makeText(getActivity(), "你还没有评价哦~", Toast.LENGTH_SHORT).show();
	}
}
