package thebigbang.com.icare.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import thebigbang.com.icare.R;

public class Splash_Screen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Intent takeUserToHomeScreen=new Intent(getBaseContext(),HomeScreenActivity.class);
                    startActivity(takeUserToHomeScreen);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
