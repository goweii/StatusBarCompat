package per.goweii.android.statusbarcompat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import per.goweii.statusbarcompat.StatusBarCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatusBarCompat.transparent(MainActivity.this);
        StatusBarCompat.registerToAutoChangeIconMode(this);
    }
}
