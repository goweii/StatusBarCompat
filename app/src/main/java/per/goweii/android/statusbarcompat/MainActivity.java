package per.goweii.android.statusbarcompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.TextView;

import per.goweii.statusbarcompat.StatusBarCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwitchCompat sc_status_bar_translation = findViewById(R.id.sc_status_bar_translation);
        sc_status_bar_translation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    StatusBarCompat.transparent(MainActivity.this);
                } else {
                    StatusBarCompat.unTransparent(MainActivity.this);
                }
                setIsTransparent();
            }
        });

        SwitchCompat sc_status_bar_dark_icon = findViewById(R.id.sc_status_bar_dark_icon);
        sc_status_bar_dark_icon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StatusBarCompat.setIconMode(MainActivity.this, isChecked);
                setIsDark();
            }
        });
    }

    private void setIsTransparent(){
        TextView textView = findViewById(R.id.tv_status_bar_is_translation);
        if (StatusBarCompat.isTransparent(this)) {
            textView.setText("沉浸");
        } else {
            textView.setText("非沉浸");
        }
    }

    private void setIsDark(){
        TextView textView = findViewById(R.id.tv_status_bar_is_dark);
        if (StatusBarCompat.isIconDark(this)) {
            textView.setText("暗色");
        } else {
            textView.setText("亮色");
        }
    }
}
