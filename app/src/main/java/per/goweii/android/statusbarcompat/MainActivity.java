package per.goweii.android.statusbarcompat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import per.goweii.statusbarcompat.StatusBarCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatusBarCompat.transparent(MainActivity.this);
        StatusBarCompat.registerAutoIconMode(this);

        StatusBarCompat.registerAutoIconMode(getApplication());
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AutoActivity.class));
            }
        });
    }
}
