package com.reactnativesimpleauthexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;

import java.util.HashMap;
import java.util.Map;


public class SimpleAuthWrapper extends ReactContextBaseJavaModule {

    private final Map<String, ReadableMap> providerMap = new HashMap<>();

    public SimpleAuthWrapper(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "SimpleAuthWrapper";
    }

    @ReactMethod
    public void configure(String provider, ReadableMap config, Callback callback) {
        providerMap.put(provider, config);
        callback.invoke();
    }

    @ReactMethod
    public void authorize(String provider, final Callback callback) {
        Log.w("myApp", "Authorize");

        ReadableMap config = providerMap.get(provider);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.hasExtra("account")) {
                    UserAccount account = intent.getParcelableExtra("account");
                    callback.invoke(null, Arguments.createMap(), account.toMap());
                } else {
                    callback.invoke(intent.getStringExtra("error"));
                }
            }
        };
        LocalBroadcastManager.getInstance(getReactApplicationContext())
                .registerReceiver(receiver, new IntentFilter(provider));

        Intent intent = new Intent(getReactApplicationContext(), SimpleAuthService.class);

        ReadableMapKeySetIterator iterator = config.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            intent.putExtra(key, config.getString(key));
        }

        intent.putExtra("provider", provider);
        getReactApplicationContext().startService(intent);
    }
}
