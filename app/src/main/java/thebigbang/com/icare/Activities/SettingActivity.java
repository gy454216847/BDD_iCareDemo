package thebigbang.com.icare.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.zip.CheckedOutputStream;

import thebigbang.com.icare.Activities.AroundMe.AroundMeActivity;
import thebigbang.com.icare.Activities.Medical.Vaccination.AlarmService;
import thebigbang.com.icare.R;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private CheckBox chbSetVaccineAlarm;
    private SharedPreferences pref;

;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initializations();
    }

    private void initializations() {
        chbSetVaccineAlarm= (CheckBox) findViewById(R.id.chbVaccineAlarm);
        pref = getSharedPreferences("Setting", MODE_PRIVATE);
        chbSetVaccineAlarm.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.aroundMeInSettings:
                Intent gotoAroundMeActivity = new Intent(SettingActivity.this, AroundMeActivity.class);
                gotoAroundMeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoAroundMeActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gotoAroundMeActivity);
                break;
          
            case R.id.aboutIcareInSettings:
                Intent gotoAboutIcareActivity = new Intent(SettingActivity.this, AboutiCAreActivity.class);
                gotoAboutIcareActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoAboutIcareActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gotoAboutIcareActivity);
                break;
            case R.id.userProfileInSettings:
                Intent gotoHomeScreenActivity = new Intent(SettingActivity.this, HomeScreenActivity.class);
                gotoHomeScreenActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoHomeScreenActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gotoHomeScreenActivity);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == chbSetVaccineAlarm) {
            if (isChecked) {

                Intent startAlarmService=new Intent(SettingActivity.this, AlarmService.class);
                startService(startAlarmService);
            }
            else if (!isChecked){
                stopService(new Intent(this, AlarmService.class));
            }
        }

    }
}
