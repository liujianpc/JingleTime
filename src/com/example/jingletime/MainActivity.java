package com.example.jingletime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
	TextView time_gone, time_last;
	Date begin;
	Date end;
	int width;
	int height;
	int nowYear, nowMonth, nowDate, hour, minute, second;
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			Calendar calendar = Calendar.getInstance();
			int[] time = { calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),
					calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE),
					calendar.get(Calendar.SECOND) };

			message.what = 0;
			message.obj = time;
			mHandler.sendMessage(message);
		}

	};
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {

		@SuppressLint("SimpleDateFormat")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				nowYear = ((int[]) (msg.obj))[0];
				nowMonth = ((int[]) (msg.obj))[1];
				nowDate = ((int[]) (msg.obj))[2];
				hour = ((int[]) (msg.obj))[3];
				minute = ((int[]) (msg.obj))[4];
				second = ((int[]) (msg.obj))[5];

				long endMillis = end.getTime();
				long beginMillis = begin.getTime();
				long nowMillis = System.currentTimeMillis();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				float gonePercent = 100 *((float) (nowMillis - beginMillis))
						/ (endMillis - beginMillis);
				float lastPercent = (float) (100.0 - gonePercent);
				time_gone.setHeight(((int) (gonePercent * height / 100)));
				time_last.setHeight(height
						- ((int) (gonePercent * height / 100)));
				time_gone.setText("今年" + dateFormat.format(new Date()) + "\n"
						+ String.valueOf(nowYear) + "年已经消耗了"
						+ String.valueOf(gonePercent) + "%");
				time_last.setText(String.valueOf(nowYear) + "年只剩"
						+ String.valueOf(lastPercent) + "%\n抓紧时间浪！");
				break;

			default:
				break;
			}
		}

	};

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:SS");
			Calendar calendar = Calendar.getInstance();
			int now_year = calendar.get(Calendar.YEAR);
		try {
			end = simpleDateFormat.parse(String.valueOf(now_year+ 1) + "-01"
					+ "-01" + " " + "00:" + "00:" + "00");
			begin = simpleDateFormat.parse(String.valueOf(now_year) + "-01"
					+ "-01" + " " + "00:" + "00:" + "00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		time_gone = (TextView) findViewById(R.id.time_gone);
		time_last = (TextView) findViewById(R.id.time_last);

		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		width = displayMetrics.widthPixels;
		height = displayMetrics.heightPixels;
		timer.schedule(task, 1000, 1000);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
