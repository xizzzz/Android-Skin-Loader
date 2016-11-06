package cn.feng.skin.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cn.feng.skin.demo.R;
import cn.feng.skin.manager.base.BaseActivity;
import cn.feng.skin.manager.util.ResourceUtils;

public class DetailActivity extends BaseActivity{
	
	private TextView titleText;
	private TextView detailText;
	private String article;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		initData();
		initView();
	}

	private void initData() {
		article = ResourceUtils.geFileFromAssets(this, "article.txt");
	}

	private void initView() {
		titleText = (TextView) findViewById(R.id.title_text);
		detailText = (TextView) findViewById(R.id.detail_text);
		
		titleText.setText("This is an article");
		detailText.setText(article);
	}

	public void SendSMS(View v) {
		Intent i = new Intent("com.example.android.directshare.SEND_SMS");
		String phoneNumber = ((EditText)findViewById(R.id.editText)).getText().toString();
		String msg = "testing message";
		i.putExtra("PHONE_NUMBER", phoneNumber);
		i.putExtra("TEXT_MSG", msg);
		//  i.setPackage(this.getPackageName());
		Log.d("send", "send sms");
		startService(i);
	}
}
