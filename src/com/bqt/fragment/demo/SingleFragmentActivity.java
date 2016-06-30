package com.bqt.fragment.demo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.bqt.fragment.R;

/**
 * 抽象一个Activity托管我们的Single Fragment
 * @author 白乾涛
 */
public abstract class SingleFragmentActivity extends Activity {
	protected abstract Fragment createFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_fragment);

		FragmentManager fm = getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.id_fragment_container);

		if (savedInstanceState==nul) {//核心代码
			//为什么需要判断是否为null？
			//当【Activity】因为配置发生改变（如屏幕旋转）或者因内存不足被系统杀死，造成被重新创建时，我们的【fragment】会被保存下来
			//虽然会创建新的【fm】，但是新的fm会首先去获取保存下来的fragment队列，重建fragment队列，从而可以恢复之前的状态。
			fragment = createFragment();
			//add方法中id的作用：1、告知fm此fragment的位置；2、设置后我们可以通过findFragmentById找到它
			fm.beginTransaction().add(R.id.id_fragment_container, fragment).commit();
		}
	}
}
