package com.reactnativesimpleauthexample;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.cloudrail.si.interfaces.Profile;
import com.cloudrail.si.services.Facebook;
import com.cloudrail.si.services.GooglePlus;
import com.cloudrail.si.services.Instagram;
import com.cloudrail.si.services.LinkedIn;
import com.cloudrail.si.services.Twitter;
import com.cloudrail.si.types.DateOfBirth;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

public class SimpleAuthService extends IntentService {

    public SimpleAuthService() {
        super("SimpleAuthService");
    }

    public Profile init(String provider, Intent intent) {
        Profile profile;

        switch (provider) {
            case "facebook": {
                profile = new Facebook(this, intent.getStringExtra("app_id"), intent.getStringExtra("app_secret"));
                break;
            }
            case "twitter": {
                profile = new Twitter(this, intent.getStringExtra("consumer_key"), intent.getStringExtra("consumer_secret"));
                break;
            }
            case "google-web": {
                profile = new GooglePlus(this, intent.getStringExtra("client_id"), intent.getStringExtra("client_secret"));
                break;
            }
            case "linkedin-web": {
                profile = new LinkedIn(this, intent.getStringExtra("client_id"), intent.getStringExtra("client_secret"));
                break;
            }
            case "instagram": {
                profile = new Instagram(this, intent.getStringExtra("client_id"), intent.getStringExtra("client_secret"));
                break;
            }
            default:
                throw new RuntimeException("Unknown Provider");
        }

        return profile;
    }

    private UserAccount init(Profile profile) {
        UserAccount account = new UserAccount();
        account.setIdentifier(profile.getIdentifier());
        account.setFullName(profile.getFullName());
        account.setEmail(profile.getEmail());
        account.setDescription(profile.getDescription());
        DateOfBirth dob = profile.getDateOfBirth();
        if (dob != null) {
            Long day = dob.getDay();
            Long month = dob.getMonth();
            Long year = dob.getYear();
            if (day != null) {
                account.setDay(day);
            }
            if (month != null) {
                account.setMonth(month);
            }
            if (year != null) {
                account.setYear(year);
            }
        }
        account.setGender(profile.getGender());
        account.setPictureURL(profile.getPictureURL());
        account.setLocale(profile.getLocale());
        return account;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String provider = intent.getStringExtra("provider");

        Log.w("myApp", "service:onHandleIntent");

        Intent broadcastIntent = new Intent(provider);
        try {
            Profile profile = init(provider, intent);
            UserAccount account = init(profile);
            Log.w("myApp", profile.getFullName());
            broadcastIntent.putExtra("account", account);
        } catch (com.cloudrail.si.exceptions.AuthenticationException e) {
            broadcastIntent.putExtra("error", e.getMessage());
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }
}
