package com.mysystem.wight;
import java.util.Timer;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.util.TimerTask;
import java.util.Date;
import android.icu.text.SimpleDateFormat;
import java.util.Locale;
import android.appwidget.AppWidgetManager;
import android.widget.RemoteViews;
import android.content.ComponentName;





public class ClockService extends Service {

	private Timer mTimer;

	

	
	@Override public void onCreate() {
		super.onCreate();
	}

	@Override public int onStartCommand(Intent intent, int flags, int startId) {
		if (null == mTimer) {
			mTimer = new Timer();
		}
		mTimer.schedule(new MyTimerTask(), 0, 500);
		return START_STICKY;
	}

	@Override public void onDestroy() {
	
		mTimer.cancel();
		mTimer = null;
		super.onDestroy();
	}

	@Override public IBinder onBind(Intent intent) {
		return null;
	}

	private final class MyTimerTask extends TimerTask {
		
		@Override public void run() {

		
			Date date = new Date();
			//yyyy年MM月dd日 HH:mm:ss
			String h = new SimpleDateFormat("hh", Locale.CHINA).format(date);
			 String m = new SimpleDateFormat("mm", Locale.CHINA).format(date);
		String s = new SimpleDateFormat("ss", Locale.CHINA).format(date);
			String y = new SimpleDateFormat("yyyy", Locale.CHINA).format(date);
			String M = new SimpleDateFormat("MM", Locale.CHINA).format(date);
			String d = new SimpleDateFormat("dd", Locale.CHINA).format(date);
			
			
			// 获取Widgets管理器
			AppWidgetManager widgetManager = AppWidgetManager.getInstance(getApplicationContext());
			// widgetManager所操作的Widget对应的远程视图即当前Widget的layout文件
			final RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.widget_layout);
			 remoteView.setTextViewText(R.id.widgetlayoutTextView1,""+h+"点"+m+"分");
			remoteView.setTextViewText(R.id.widgetlayoutTextView2,""+y+"年"+M+"月"+d+"日"+"\n"+s);
			
			//String t="https://api.ixiaowai.cn/gqapi/gqapi.php";
		//	Bitmap resoure=uribitmap(t);
				
			
		//	remoteView.setImageViewBitmap(R.id.doge_imageView,resoure);
			
					ComponentName componentName =
				new ComponentName(getApplicationContext(), TestWidgetProvider.class);
				widgetManager.updateAppWidget(componentName, remoteView);
		}
	}
	
	

		
		

	
		
		
}
