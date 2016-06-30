package com.bqt.fragment.demo;

import android.app.Fragment;

public class ListTitleActivity extends SingleFragmentActivity {
	private ListTitleFragment mListFragment;

	@Override
	protected Fragment createFragment() {
		mListFragment = new ListTitleFragment();
		return mListFragment;
	}
}