package com.retroportalstudio.www.background_service;

import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import android.os.Bundle;

public class MainActivity extends FlutterActivity {

  private Intent forService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);

    forService = new Intent(MainActivity.this,MyService.class);

    // new MethodChannel(getFlutterView(),"com.retroportalstudio.messages")
    //         .setMethodCallHandler(new MethodChannel.MethodCallHandler() {
    //   @Override
    //   public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
    //     if(methodCall.method.equals("startService")){
    //       startService();
    //       result.success("Service Started");
    //     }
    //   }
    // });


  }
    private static final String CHANNEL = "com.retroportalstudio.messages";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler(
                    (call, result) -> {
                        if(call.method.equals("startService")){
                          startService();
                          result.success("Service Started");
                        }
                }
        );
    }



  @Override
  protected void onDestroy() {
    super.onDestroy();
    stopService(forService);
  }

  private void startService(){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
      startForegroundService(forService);
    } else {
      startService(forService);
    }
  }



}
