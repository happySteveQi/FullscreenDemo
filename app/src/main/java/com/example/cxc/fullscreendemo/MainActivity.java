package com.example.cxc.fullscreendemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.cxc.fullscreendemo.apk.PackageUtils;
import com.example.cxc.fullscreendemo.decoration.RecyclerViewTestActivity;
import com.example.cxc.fullscreendemo.draw.DrawTestActivity;
import com.example.cxc.fullscreendemo.notification.NotificationUtils;
import com.example.cxc.fullscreendemo.service.ServiceTestActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //打开详情
        View detailBtn = findViewById(R.id.detail_btn);
        detailBtn.setOnClickListener(v -> onDetailBtnClick());

        //打开RecyclerView
        View recyclerBtn = findViewById(R.id.recycler_view_btn);
        recyclerBtn.setOnClickListener(v -> onTestRecyclerViewBtnClick());

        //Launch Notification
        View launchNotificationBtn = findViewById(R.id.launch_notification_btn);
        launchNotificationBtn.setOnClickListener(v -> onLaunchNotificationBtnClick());

        //安装Apk
        View installApkBtn = findViewById(R.id.install_apk_btn);
        installApkBtn.setOnClickListener(v -> onInstallApkBtnClick());

        //Query Intent
        View queryIntentBtn = findViewById(R.id.query_intent_btn);
        queryIntentBtn.setOnClickListener(v -> onQueryIntentBtnClick());

        //隐式Intent启动Activity
        View startActivityBtn = findViewById(R.id.start_activity_btn);
        startActivityBtn.setOnClickListener(v -> onStartActivityBtnClick());

        //start ServiceTestActivity
        View startServiceBtn = findViewById(R.id.start_service_activity_btn);
        startServiceBtn.setOnClickListener(v -> onStartServiceActivityBtnClick());
        //start ServiceTestActivity
        View startCustomDrawBtn = findViewById(R.id.start_custom_draw_activity_btn);
        startCustomDrawBtn.setOnClickListener(v -> onStartCustomDrawBtnClick());
    }

    private void onDetailBtnClick() {
        Intent intent = new Intent(this, DetailActivity.class);
        //传参
        intent.putExtra(ExtraKeyConstants.NAME, "CXC");
        intent.putExtra(ExtraKeyConstants.AGE, 22);

        /*//传参
        Bundle extras = new Bundle();
        extras.putString(ExtraKeyConstants.NAME, "CXC");
        extras.putInt(ExtraKeyConstants.AGE, 22);
        intent.putExtras(extras);*/
        startActivity(intent);
    }

    private void onTestRecyclerViewBtnClick() {
        Intent intent = new Intent(this, RecyclerViewTestActivity.class);
        startActivity(intent);
    }

    public static final int NOTIFICATION_ID = 888;

    private void onLaunchNotificationBtnClick() {
        String textTitle = "This is Title";
        String textContent = "This is Content";

        //创建Notification Channel
        String channelId = NotificationUtils.createNotificationChannel(getApplicationContext());

        //创建Notification并与Channel关联
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //Launch Notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }


    private void onInstallApkBtnClick() {
//        String localAPKPath = PackageUtils.getFilePath(getApplicationContext());
        String localAPKPath = "/data/data/com.example.cxc.fullscreendemo/cache/fill_screen_1.0.2.apk";
        Log.d(TAG, "-->onInstallApkBtnClick()--localAPKPath=" + localAPKPath);
        PackageUtils.onApkDownLoadCompleted(getApplicationContext(), localAPKPath);
        PackageUtils.onInstallApkBtnClick(this, localAPKPath);
    }

    private void onQueryIntentBtnClick() {
        Log.d(TAG, "-->onQueryIntentBtnClick()--");
        Intent testAIntent = new Intent();
        testAIntent.setAction("com.example.cxc.intentFilter.testA");
//        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(testAIntent, PackageManager.MATCH_DEFAULT_ONLY);
        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(testAIntent, PackageManager.MATCH_ALL);
        if (resolveInfoList != null) {
            for (ResolveInfo info : resolveInfoList) {
                Log.d(TAG, "-->info:" + info);
            }
        }
    }

    private void onStartActivityBtnClick() {
        Log.d(TAG, "-->onStartActivityBtnClick()--");
        Intent testAIntent = new Intent();
        testAIntent.setAction("com.example.cxc.intentFilter.testA");
        if (getPackageManager().resolveActivity(testAIntent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            startActivity(testAIntent);
        }
    }

    private void onStartServiceActivityBtnClick() {
        Intent intent = new Intent(this, ServiceTestActivity.class);
        startActivity(intent);
    }

    private void onStartCustomDrawBtnClick() {
        Intent intent = new Intent(this, DrawTestActivity.class);
        startActivity(intent);
    }
}
