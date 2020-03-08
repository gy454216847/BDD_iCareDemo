package thebigbang.com.icare.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import thebigbang.com.icare.Activities.AroundMe.AroundMeActivity;
import thebigbang.com.icare.R;

public class AboutiCAreActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_about_icare);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_abouti_care, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.aroundMeInAboutIcare:
                Intent gotoAroundMeActivity = new Intent(AboutiCAreActivity.this, AroundMeActivity.class);
                gotoAroundMeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoAroundMeActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gotoAroundMeActivity);
                break;

            case R.id.settinsInAboutIcare:
                Intent gotoSettingActivity = new Intent(AboutiCAreActivity.this, SettingActivity.class);
                gotoSettingActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoSettingActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gotoSettingActivity);
                break;
            case R.id.userProfileInAboutIcare:
                Intent gotoHomeScreenActivity = new Intent(AboutiCAreActivity.this, HomeScreenActivity.class);
                gotoHomeScreenActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoHomeScreenActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gotoHomeScreenActivity);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
