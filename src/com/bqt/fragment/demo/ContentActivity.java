package com.bqt.fragment.demo;

import android.app.Fragment;

public class ContentActivity extends SingleFragmentActivity {
	private ContentFragment mContentFragment;

	@Override
	protected Fragment createFragment() {
		String title = getIntent().getStringExtra(ContentFragment.ARGUMENT);

		mContentFragment = ContentFragment.newInstance(title);
		return mContentFragment;
	}
}
