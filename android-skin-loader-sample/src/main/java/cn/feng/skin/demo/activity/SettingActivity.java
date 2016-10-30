package cn.feng.skin.demo.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import cn.feng.skin.demo.R;
import cn.feng.skin.manager.base.BaseActivity;
import cn.feng.skin.manager.listener.ILoaderListener;
import cn.feng.skin.manager.loader.SkinManager;
import cn.feng.skin.manager.util.L;

public class SettingActivity extends BaseActivity {

	/**
	 * Put this skin file on the root of sdcard
	 * eg:
	 * /mnt/sdcard/BlackFantacy.skin
	 */
	private static final String SKIN_NAME = "BlackFantacy.skin";
	private static final String SKIN_DIR = Environment
			.getExternalStorageDirectory() + File.separator + SKIN_NAME;
	
	
	private TextView titleText;
	private Button setOfficalSkinBtn;
	private Button setNightSkinBtn;
	
	private boolean isOfficalSelected = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initView();
	}

	private void initView() {
		titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText("Skin Setting");
		setOfficalSkinBtn = (Button) findViewById(R.id.set_default_skin);
		setNightSkinBtn = (Button) findViewById(R.id.set_night_skin);
		
		
		isOfficalSelected = !SkinManager.getInstance().isExternalSkin();
		
		if(isOfficalSelected){
			setOfficalSkinBtn.setText("Default(current)");
			setNightSkinBtn.setText("BlackFantacy");
		}else{
			setNightSkinBtn.setText("BlackFantacy(current)");
			setOfficalSkinBtn.setText("Default");
		}
		
		setNightSkinBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSkinSetClick();
			}
		});
		
		setOfficalSkinBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSkinResetClick();
			}
		});
	}

	protected void onSkinResetClick() {
		if(!isOfficalSelected){
			SkinManager.getInstance().restoreDefaultTheme();
			Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
			setOfficalSkinBtn.setText("Default(current)");
			setNightSkinBtn.setText("BlackFantacy");
			isOfficalSelected = true;
		}
	}

	private void onSkinSetClick() {
		if(!isOfficalSelected) return;
		
		File skin = new File(SKIN_DIR);

		if(skin == null || !skin.exists()){
			Toast.makeText(getApplicationContext(), "Please check whether " + SKIN_DIR + " exists ", Toast.LENGTH_SHORT).show();
			return;
		}

		SkinManager.getInstance().load(skin.getAbsolutePath(),
				new ILoaderListener() {
					@Override
					public void onStart() {
						L.e("startloadSkin ");
					}

					@Override
					public void onSuccess() {
						L.e("loadSkinSuccess");
						Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
						setNightSkinBtn.setText("BlackFantacy(current)");
						setOfficalSkinBtn.setText("Default");
						isOfficalSelected = false;
					}

					@Override
					public void onFailed() {
						L.e("loadSkinFail");
						Toast.makeText(getApplicationContext(), "Failed to load skin", Toast.LENGTH_SHORT).show();
					}
				});
	}
}
