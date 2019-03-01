package per.goweii.android.statusbarcompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import per.goweii.statusbarcompat.StatusBarCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwitchCompat sc_status_bar_dark_icon = findViewById(R.id.sc_status_bar_dark_icon);
        sc_status_bar_dark_icon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StatusBarCompat.setIconMode(MainActivity.this, isChecked);
            }
        });
    }
}
