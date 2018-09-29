package notes.apps.aleonqe.com.sharecontenturisample;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.List;

import static notes.apps.aleonqe.com.sharecontenturisample.Logutil.logx;

public class AppUtil {

    public static String getActivityInfoString(Activity activity) {
        ActivityManager manager = (ActivityManager) activity.getSystemService(Activity.ACTIVITY_SERVICE);
        String result = "meh...";
        List<ActivityManager.RunningTaskInfo> taskList = manager.getRunningTasks(1);
        if (taskList != null) {
            for (ActivityManager.RunningTaskInfo taskInfo : taskList) {
                result = "Task ID: " + taskInfo.id + "\n" +
                        "Top Activity: " + taskInfo.topActivity.getShortClassName() + "\n" +
                        "Base Activity: " + taskInfo.baseActivity.getShortClassName() + "\n" +
                        "Total: " + taskInfo.numActivities + "\n" +
                        "Running: " + taskInfo.numRunning;
            }
        }
        return result;
    }

    public static String getUriForString(@NonNull Intent intent) {
        String action = intent.getAction();
        if (action == null) {
            return null;
        }
        String type = intent.getType();
        switch (action) {
            case Intent.ACTION_SEND:
                if (type.equals("text/plain")) {
                    logx("text/plain");
                } else if (type.startsWith("image/")) {
                    return getSharedDataImage(intent);
                }
                break;
            case Intent.ACTION_SEND_MULTIPLE:
                if (type.startsWith("image/")) {
                }
                break;
        }
        return null;
    }

    public static String getSharedDataImage(Intent intent) {
        Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        return imageUri.toString();
    }

    public static Bitmap getBitmapFromUri(Uri uri, Activity activity) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = activity.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}
