package cosine.boat.logcat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UsbBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {

            case Intent.ACTION_MEDIA_MOUNTED:
            case Intent.ACTION_MEDIA_UNMOUNTED: {

                break;
            }
            default:
                break;
        }

    }

}
