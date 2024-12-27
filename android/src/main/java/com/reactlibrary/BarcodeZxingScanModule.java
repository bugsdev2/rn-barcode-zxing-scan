package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class BarcodeZxingScanModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private final ReactApplicationContext reactContext;
    private Callback mCallback;

    public BarcodeZxingScanModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.reactContext.addActivityEventListener(this); // Register the activity event listener
    }

    @Override
    public String getName() {
        return "BarcodeZxingScan";
    }

    @ReactMethod
    public void sampleMethod(String stringArgument, Promise promise) {
        try {
            promise.resolve(stringArgument);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void showQrReader(Callback callback) {
        this.mCallback = callback; // Assign the callback
        Activity currentActivity = getCurrentActivity();

        if (currentActivity != null) {
            IntentIntegrator integrator = new IntentIntegrator(currentActivity);
            integrator.setOrientationLocked(true);
            integrator.setBeepEnabled(false);      // Disable beep sound
            integrator.setCaptureActivity(ContinuousCaptureActivity.class);
            integrator.initiateScan();
            reactContext.addActivityEventListener(this); // Ensure we handle the result
        } else {
            callback.invoke("Error: Activity is null");
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (mCallback != null) {
            // Use IntentIntegrator to parse the activity result
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() != null) {
                    // Pass the scanned data back to the callback
                    mCallback.invoke(null, result.getContents());
                } else {
                    // No result; notify the callback
                    mCallback.invoke("Scan canceled", null);
                }
            } else {
                // Parsing failed or unrelated result
                mCallback.invoke("Error: Failed to parse scan result", null);
            }
        }
    }


    @Override
    public void onNewIntent(Intent intent) {
        // No specific behavior required for this module
    }
}