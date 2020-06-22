package my.Shiksha.sdk.initsdk;

import android.content.Context;
import android.util.Log;

import my.Shiksha.sdk.ShikshaSDK;
import my.Shiksha.sdk.ShikshaSDKInitParams;
import my.Shiksha.sdk.ShikshaSDKInitializeListener;
import my.Shiksha.sdk.ShikshaSDKRawDataMemoryMode;

/**
 * Init and auth Shiksha sdk first before using SDK interfaces
 */
public class InitAuthSDKHelper implements AuthConstants, ShikshaSDKInitializeListener {

    private final static String TAG = "InitAuthSDKHelper";

    private static InitAuthSDKHelper mInitAuthSDKHelper;

    private ShikshsSDK mShikshaSDK;

    private InitAuthSDKCallback mInitAuthSDKCallback;

    private InitAuthSDKHelper() {
        mShikshaSDK = ShikshaSDK.getInstance();
    }

    public synchronized static InitAuthSDKHelper getInstance() {
        mInitAuthSDKHelper = new InitAuthSDKHelper();
        return mInitAuthSDKHelper;
    }

    /**
     * init sdk method
     */
    public void initSDK(Context context, InitAuthSDKCallback callback) {
        if (!mShikshaSDK.isInitialized()) {
            mInitAuthSDKCallback = callback;

            ShikshaSDKInitParams initParams = new ShikshaSDKInitParams();
            initParams.jwtToken = SDK_JWTTOKEN;
            initParams.enableLog = true;
            initParams.logSize = 50;
            initParams.domain=AuthConstants.WEB_DOMAIN;
            initParams.videoRawDataMemoryMode = ShikshaSDKRawDataMemoryMode.ShikshaSDKRawDataMemoryModeStack;
            mShikshaSDK.initialize(context, this, initParams);
        }
    }

    /**
     * init sdk callback
     *
     * @param errorCode         defined in {@link my.Shiksha.sdk.ShikshaError}
     * @param internalErrorCode Shiksha internal error code
     */
    @Override
    public void onShikshaSDKInitializeResult(int errorCode, int internalErrorCode) {
        Log.i(TAG, "onShikshaSDKInitializeResult, errorCode=" + errorCode + ", internalErrorCode=" + internalErrorCode);

        if (mInitAuthSDKCallback != null) {
            mInitAuthSDKCallback.onShikshaSDKInitializeResult(errorCode, internalErrorCode);
        }
    }

    @Override
    public void onShikshaAuthIdentityExpired() {
        Log.e(TAG,"onShikshaAuthIdentityExpired in init");
    }

    public void reset(){
        mInitAuthSDKCallback = null;
    }
}
