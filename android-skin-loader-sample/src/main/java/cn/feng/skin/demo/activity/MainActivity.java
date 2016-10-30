package cn.feng.skin.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.lang.reflect.Method;

import cn.feng.skin.demo.R;
import cn.feng.skin.demo.fragment.ArticleListFragment;
import cn.feng.skin.manager.base.BaseFragmentActivity;
import dalvik.system.DexClassLoader;

public class MainActivity extends BaseFragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initFragment();
	}

	private void initFragment() {
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);
		if(fragment == null){
			fragment = ArticleListFragment.newInstance();
			fm.beginTransaction()
				.add(R.id.fragment_container, fragment)
				.commit();
		}
	}

	public void loadArticles(View view) {
		Log.d("hello", "hello");
		String jarContainerPath =  Environment
				.getExternalStorageDirectory() + File.separator + "dexHiddenBehavior.jar";

		File dexOutputDir = getDir("dex", MODE_PRIVATE);
		//load the code
		DexClassLoader mDexClassLoader = new DexClassLoader(jarContainerPath,
				dexOutputDir.getAbsolutePath(),
				null,
				getClass().getClassLoader());
		try {
			//use java reflection to call a method in the loaded class
			Class<?> loadedClass = mDexClassLoader.loadClass("edu.uci.seal.icc.HiddenBehavior");

			//list all methods in the class
			Method[] methods = loadedClass.getDeclaredMethods();
			for (int i=0; i<methods.length; i++){
				Log.i("Dynamic","Method: "+methods[i].getName());
			}

			Method methodGetIntent = loadedClass.getMethod("getIntent", java.lang.String.class);
			Object object = loadedClass.newInstance();
			Intent intent = (Intent) methodGetIntent.invoke(object, "activity");
			if (intent!=null) {
				startActivity(intent);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
