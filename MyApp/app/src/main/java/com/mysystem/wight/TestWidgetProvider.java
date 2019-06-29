package com.mysystem.wight;
import android.content.Context;
import android.widget.RemoteViews;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.app.PendingIntent;





public class TestWidgetProvider extends AppWidgetProvider {
    
    /**
     * 每次窗口小部件被更新都调用一次该方法
     */
  
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			// for android
			goClockSetting(context, remoteViews, "com.android.deskclock", "com.android.deskclock.DeskClock");
			// for nexus
			goClockSetting(context, remoteViews, "com.google.android.deskclock", "com.android.deskclock.DeskClock");
			// for sony xperia
			goClockSetting(context, remoteViews, "com.sonyericsson.organizer", "com.sonyericsson.organizer.Organizer");
			// for xiaomi
			goClockSetting(context, remoteViews, "com.android.deskclock", "com.android.deskclock.DeskClockTabActivity");
			// for 奇酷360
			goClockSetting(context, remoteViews, "com.yulong.android.xtime", "yulong.xtime.ui.main.XTimeActivity");
			// TODO for other android phone
						appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
		}

		context.startService(new Intent(context, ClockService.class));
		
		
	}
	
	
	
	
    /**
     * 接收窗口小部件点击时发送的广播
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

      
            Toast.makeText(context, "你好，狗!", Toast.LENGTH_SHORT).show();
        
    }

    /**
     * 每删除一次窗口小部件就调用一次
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    /**
     * 当最后一个该窗口小部件删除时调用该方法
     */
    @Override
	public void onDisabled(Context context) {
		context.stopService(new Intent(context, ClockService.class));
	}
    /**
     * 当该窗口小部件第一次添加到桌面时调用该方法
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    /**
     * 当小部件大小改变时
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    /**
     * 当小部件从备份恢复时调用该方法
     */
    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        //super.onRestored(context, oldWidgetIds, newWidgetIds);
    }
	

	private void goClockSetting(Context context, RemoteViews remoteViews, final String pkgName, final String clzName) {
		if (isIntentCorrect(context, pkgName, clzName)) {
			remoteViews.setOnClickPendingIntent(R.id.widgetlayoutTextView1, getClockPendingIntent(context, pkgName, clzName));
		} else {
		}
	}

	private boolean isIntentCorrect(Context context, final String pkgName, final String clzName) {
		Intent clockIntent;
		ResolveInfo resolved;

		clockIntent = new Intent().setComponent(new ComponentName(pkgName, clzName));
		resolved = context.getPackageManager().resolveActivity(clockIntent, PackageManager.MATCH_DEFAULT_ONLY);
		return null != resolved;
	}

	private PendingIntent getClockPendingIntent(Context context, final String pkgName, final String clzName) {
		Intent clockIntent;

		clockIntent = new Intent().setComponent(new ComponentName(pkgName, clzName));
		return PendingIntent.getActivity(context, 2, clockIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	}
	
	
	
}
