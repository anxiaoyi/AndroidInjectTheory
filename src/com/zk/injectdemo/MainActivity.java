package com.zk.injectdemo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView mTextView;

	@ViewInject(id = R.id.button1)
	private Button mDeclaredFieldsButton;
	@ViewInject(id = R.id.button2)
	public Button mPublicFieldsButton;
	
	@ViewInject(id = R.id.button3, onClick = "declaredMethodsButtonClick")
	private Button mDeclaredMethodsButton;
	@ViewInject(id = R.id.button4, onClick = "publicMethodsButtonClick")
	private Button mPublicMethodsButton;
	
	@ViewInject(id = R.id.button5, onClick = "declaredParamMethodsButtonClick")
	private Button mDeclaredParamMethodsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextView = (TextView) findViewById(R.id.textView1);

		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				resetValue();
				String injectInfo = InjectHelper.initViewIdInject(
						MainActivity.this, true);
				mTextView.setText(Html.fromHtml(injectInfo));
				mTextView.append(Html.fromHtml(getInjectResult()));
			}
		});
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				resetValue();
				String injectInfo = InjectHelper.initViewIdInject(
						MainActivity.this, false);
				mTextView.setText(Html.fromHtml(injectInfo));
				mTextView.append(Html.fromHtml(getInjectResult()));
			}
		});
		setClickEvent();
		findViewById(R.id.button6).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				resetValue();
				findViewById(R.id.button3).setOnClickListener(null);
				findViewById(R.id.button4).setOnClickListener(null);
				findViewById(R.id.button5).setOnClickListener(null);
				setClickEvent();
				
				Toast.makeText(MainActivity.this, "remove all event", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void resetValue(){
		mDeclaredFieldsButton = null;
		mPublicFieldsButton = null;
		
		mDeclaredMethodsButton = null;
		mPublicMethodsButton = null;
		
		mDeclaredParamMethodsButton = null;
	}
	
	private void setClickEvent(){
		findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				resetValue();
				String injectInfo = InjectHelper.initViewMethodInject(MainActivity.this, true, false);
				mTextView.setText(Html.fromHtml(injectInfo));
				mTextView.append(Html.fromHtml(getInjectResult()));
			}
		});	
		findViewById(R.id.button4).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				resetValue();
				String injectInfo = InjectHelper.initViewMethodInject(
						MainActivity.this, false, false);
				mTextView.setText(Html.fromHtml(injectInfo));
				mTextView.append(Html.fromHtml(getInjectResult()));
			}
		});
		findViewById(R.id.button5).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				resetValue();
				String injectInfo = InjectHelper.initViewMethodInject(
						MainActivity.this, true, true);
				mTextView.setText(Html.fromHtml(injectInfo));
				mTextView.append(Html.fromHtml(getInjectResult()));
			}
		});
	}
	
	private void declaredMethodsButtonClick(){
		Toast.makeText(this, "declaredMethodsButtonClick", Toast.LENGTH_SHORT).show();
	}
	
	public void publicMethodsButtonClick(){
		Toast.makeText(this, "publicMethodsButtonClick", Toast.LENGTH_SHORT).show();
	}
	
	private void declaredParamMethodsButtonClick(View view){
		Toast.makeText(this, "declaredParamMethodsButtonClick", Toast.LENGTH_SHORT).show();
	}

	private String getInjectResult() {
		StringBuilder sb = new StringBuilder("<br><br>");
		sb.append(InjectHelper.getColoredText("mDeclaredFieldsButtonA:"
				+ (mDeclaredFieldsButton == null ? " Null" : " Not Null")));
		sb.append("<br>");
		sb.append(InjectHelper.getColoredText("mPublicFieldsButton:"
				+ (mPublicFieldsButton == null ? " Null" : " Not Null")));
		sb.append("<br>");
		sb.append(InjectHelper.getColoredText("mPublicFieldsButton:"
				+ (mDeclaredMethodsButton == null ? " Null" : " Not Null")));
		sb.append("<br>");
		sb.append(InjectHelper.getColoredText("mPublicFieldsButton:"
				+ (mPublicMethodsButton == null ? " Null" : " Not Null")));

		return sb.toString();
	}
}
