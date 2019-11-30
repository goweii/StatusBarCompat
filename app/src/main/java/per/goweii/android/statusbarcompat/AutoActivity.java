package per.goweii.android.statusbarcompat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

import per.goweii.statusbarcompat.StatusBarCompat;

public class AutoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        StatusBarCompat.transparent(AutoActivity.this);

        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        findViewById(R.id.v_bar).setBackgroundColor(Color.rgb(r, g, b));
    }
}
