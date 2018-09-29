package notes.apps.aleonqe.com.sharecontenturisample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import static notes.apps.aleonqe.com.sharecontenturisample.AppUtil.getActivityInfoString;
import static notes.apps.aleonqe.com.sharecontenturisample.AppUtil.getBitmapFromUri;
import static notes.apps.aleonqe.com.sharecontenturisample.AppUtil.getSharedDataImage;
import static notes.apps.aleonqe.com.sharecontenturisample.AppUtil.getUriForString;
import static notes.apps.aleonqe.com.sharecontenturisample.Logutil.logx;
import static notes.apps.aleonqe.com.sharecontenturisample.Logutil.toax;
import static notes.apps.aleonqe.com.sharecontenturisample.MainActivity.IntentKeys.KEY_DELAY;
import static notes.apps.aleonqe.com.sharecontenturisample.MainActivity.IntentKeys.KEY_URI;

public class MainActivity extends AppCompatActivity {

    private TextView tv_activityInfo;
    private TextView tv_guideMessage;
    private LinearLayout ll_configuration;
    private TextInputEditText et_delay;
    private RadioGroup rg_finishBefore;
    private CheckBox cb_loadImage;
    private ImageView iv_preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_image_preview_container);

        init();
        updateUi();
    }

    private void updateUi() {
        String activityInfoString = getActivityInfoString(this);
        logx("1234321", activityInfoString);
        tv_activityInfo.setText(getString(R.string.activity_information, activityInfoString));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getUriForString(getIntent()) != null) {
            ll_configuration.setVisibility(View.VISIBLE);
            tv_guideMessage.setVisibility(View.GONE);
        } else {
            ll_configuration.setVisibility(View.GONE);
            tv_guideMessage.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        setTitle(getClass().getSimpleName());
        tv_activityInfo = findViewById(R.id.tv_activity_info);
        tv_guideMessage = findViewById(R.id.tv_guide_message);
        ll_configuration = findViewById(R.id.ll_configuration);
        et_delay = findViewById(R.id.tiet_time);
        rg_finishBefore = findViewById(R.id.rg_finish_before);
        cb_loadImage = findViewById(R.id.cb_load_image);
        cb_loadImage.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    displayImage(getIntent(), iv_preview, MainActivity.this);
                } else {
                    iv_preview.setImageBitmap(null);
                }
            }
        });
        iv_preview = findViewById(R.id.iv_preview);
    }

    public void launchActivity(View view) {
        if (rg_finishBefore.getCheckedRadioButtonId() == R.id.rb_finish_before) {
            finish();
        }
        Intent intent = new Intent(this, NotMainActivity.class);
        if (TextUtils.isEmpty(et_delay.getText())) {
            intent.putExtra(KEY_DELAY, 0);
        } else {
            intent.putExtra(KEY_DELAY, Long.parseLong(et_delay.getText() + ""));

        }
        intent.putExtra(KEY_URI, getSharedDataImage(getIntent()));
        startActivity(intent);
        if (rg_finishBefore.getCheckedRadioButtonId() == R.id.rb_finish_after) {
            finish();
        }
    }

    private void displayImage(Intent intent, ImageView imageView, Activity activity) {
        String uriString = getUriForString(intent);
        if (uriString != null) {

            Uri uri = Uri.parse(uriString);
            Bitmap bitmap;
            try {
                bitmap = getBitmapFromUri(uri, activity);
                imageView.setImageBitmap(bitmap);
            } catch (Throwable e) {
                // deliberately catching this
                e.printStackTrace();
                toax(activity, e.getMessage());
            }
        } else {
            toax(activity, "Invalid Uri");
        }
    }

    public interface IntentKeys {
        String KEY_DELAY = "delay";
        String KEY_URI = "uri";
    }
}
