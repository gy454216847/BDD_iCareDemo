package thebigbang.com.icare.Activities.Medical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import thebigbang.com.icare.Activities.Medical.DoctorsProfile.MyDoctorsActivity;
import thebigbang.com.icare.Activities.Medical.PresentHealthInfo.HealthConditionActivity;
import thebigbang.com.icare.Activities.Medical.Vaccination.VaccinationActivity;
import thebigbang.com.icare.R;

public class MedicalInfoActivity extends AppCompatActivity {

    private LinearLayout llHealthConditions;
    private LinearLayout llVaccination;
    private LinearLayout llMyDoctors;
    private LinearLayout llNotes;

    private static int profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_medical_info);

        initializations();
        retrieveDataFromDatabase();
        eventClickListener();
    }

    private void eventClickListener() {
        llHealthConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoHealthConditionsActivity = new Intent(MedicalInfoActivity.this, HealthConditionActivity.class);
                gotoHealthConditionsActivity.putExtra("id", profileId);
                gotoHealthConditionsActivity.putExtra("from", "medicalInfo");
                gotoHealthConditionsActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoHealthConditionsActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gotoHealthConditionsActivity);
            }
        });
        llVaccination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoVaccinationActivity=new Intent(MedicalInfoActivity.this,VaccinationActivity.class);
                gotoVaccinationActivity.putExtra("id", profileId);
                gotoVaccinationActivity.putExtra("from", "medicalInfo");
                gotoVaccinationActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                gotoVaccinationActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoVaccinationActivity);
            }
        });
        llMyDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMyDoctorsActivity=new Intent(MedicalInfoActivity.this,MyDoctorsActivity.class);
                gotoMyDoctorsActivity.putExtra("id", profileId);
                gotoMyDoctorsActivity.putExtra("from", "medicalInfo");
                gotoMyDoctorsActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                gotoMyDoctorsActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoMyDoctorsActivity);

            }
        });
        llNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void retrieveDataFromDatabase() {
        Bundle extras=getIntent().getExtras();
        profileId=extras.getInt("id");

    }

    private void initializations() {
        llHealthConditions= (LinearLayout) findViewById(R.id.llHealthConditions);
        llVaccination= (LinearLayout) findViewById(R.id.llVaccination);
        llMyDoctors= (LinearLayout) findViewById(R.id.llMyDoctors);
        llNotes= (LinearLayout) findViewById(R.id.llNotes);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
