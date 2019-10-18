package com.video.upload.app.util;

import android.content.Context;
import android.content.res.Configuration;


public class MiscUtils {
    public static boolean isOrientationLandscape(Context context) {
        boolean isOrientationLandscape;
        int orientation = context.getResources().getConfiguration().orientation;
        switch (orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                isOrientationLandscape = true;
                break;
            case Configuration.ORIENTATION_PORTRAIT:
            default:
                isOrientationLandscape = false;
        }
        return isOrientationLandscape;
    }
}
