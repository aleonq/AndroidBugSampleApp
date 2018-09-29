package notes.apps.aleonqe.com.sharecontenturisample;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import static notes.apps.aleonqe.com.sharecontenturisample.AppUtil.getActivityInfoString;
import static notes.apps.aleonqe.com.sharecontenturisample.Logutil.logx;

public class NotMainActivity extends AppCompatActivity {

    private String uriString;
    private long delay;
    private ImageView iv_preview;
    private TextView tv_activityInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        updateUi();
    }

    private void init() {
        setTitle(getClass().getSimpleName());
        tv_activityInfo = findViewById(R.id.tv_activity_info);
        iv_preview = findViewById(R.id.iv_preview);
    }

    private void updateUi() {
        String activityInfoString = getActivityInfoString(this) + "\n" +
                "Shared Uri: " + uriString + "\n" +
                "Delay: " + delay;
        logx("1234321", activityInfoString);
        tv_activityInfo.setText(getString(R.string.activity_information, activityInfoString));
        uriString = getIntent().getStringExtra(MainActivity.IntentKeys.KEY_URI);
        delay = getIntent().getLongExtra(MainActivity.IntentKeys.KEY_DELAY, 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_preview.setImageURI(Uri.parse(uriString));
            }
        }, delay);
    }
}
