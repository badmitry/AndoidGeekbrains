package badmitry.hellogeekbrains;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PushMessage extends FirebaseMessagingService {
    private int messageId = 0;

    public PushMessage() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("!!!!", "onMessageReceived: ");
        String title = Objects.requireNonNull(remoteMessage.getNotification()).getTitle();
        if (title == null){
            title = "Warning";
        }
        String text = remoteMessage.getNotification().getBody();
        assert text != null;
        Log.d("!!!!", text);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2")
                .setSmallIcon(R.mipmap.weather)
                .setContentTitle(title)
                .setContentText(text);
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++, builder.build());
    }

    @Override
    public void onNewToken(@NotNull String s) {
        Log.d("token", "onNewToken: " + s);
    }
}
