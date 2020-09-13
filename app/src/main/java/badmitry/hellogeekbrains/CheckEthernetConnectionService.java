package badmitry.hellogeekbrains;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.app.NotificationCompat;

public class CheckEthernetConnectionService extends BroadcastReceiver {
    private int messageId = 0;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null && networkInfo.isConnected()) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                    .setSmallIcon(R.mipmap.weather)
                    .setContentTitle("Broadcast Receiver")
                    .setContentText("Network is available");
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(messageId++, builder.build());
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                    .setSmallIcon(R.mipmap.weather)
                    .setContentTitle("Broadcast Receiver")
                    .setContentText("Network isn`t available");
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(messageId++, builder.build());
        }
    }
}
