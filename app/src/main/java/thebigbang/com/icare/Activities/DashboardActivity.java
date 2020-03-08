package thebigbang.com.icare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import thebigbang.com.icare.Activities.Diet.DietPlanActivity;
import thebigbang.com.icare.Activities.Medical.MedicalInfoActivity;
import thebigbang.com.icare.Activities.Profile.ProfileActivity;
import thebigbang.com.icare.R;

public class DashboardActivity extends AppCompatActivity {
    private ImageView imvPersonalInfo;
    private ImageView imvMedicalHistory;
    private ImageView imvDiet;
    private static int profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_dashboard);

        Bundle extras=getIntent().getExtras();
        profileId=extras.getInt("id");

        initialization();
        setEventClickListener();

    }

    private void setEventClickListener() {
        imvPersonalInfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        Intent gotoProfileActivity = new Intent(DashboardActivity.this, ProfileActivity.class);
                        gotoProfileActivity.putExtra("id", profileId);
                        gotoProfileActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        gotoProfileActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(gotoProfileActivity);
                        break;
                }
                return true;
            }
        });
        imvMedicalHistory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        Toast.makeText(getApplicationContext(),"You Clicked On Medical History",Toast.LENGTH_SHORT).show();
                        Intent gotoMedicalActivity = new Intent(DashboardActivity.this, MedicalInfoActivity.class);
                        gotoMedicalActivity.putExtra("id", profileId);
                        gotoMedicalActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        gotoMedicalActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(gotoMedicalActivity);
                        break;
                }
                return true;
            }
        });
        imvDiet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        Intent gotoDietPlanActivity = new Intent(DashboardActivity.this, DietPlanActivity.class);
                        gotoDietPlanActivity.putExtra("id", profileId);
                        gotoDietPlanActivity.putExtra("from", "DashboardActivity");
                        gotoDietPlanActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        gotoDietPlanActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(gotoDietPlanActivity);

                        break;
                }
                return true;
            }
        });

    }

    private void initialization() {
        imvPersonalInfo= (ImageView) findViewById(R.id.imvPersonalInfo);
        imvMedicalHistory= (ImageView) findViewById(R.id.imvMedicalHistory);
        imvDiet= (ImageView) findViewById(R.id.imvDiet);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
