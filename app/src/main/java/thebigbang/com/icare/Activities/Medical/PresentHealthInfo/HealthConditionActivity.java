package thebigbang.com.icare.Activities.Medical.PresentHealthInfo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thebigbang.com.icare.Adapters.DiseasesAdapter;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Diseases;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_User_Profile;
import thebigbang.com.icare.Model.Diseases;
import thebigbang.com.icare.Model.User_Profile;
import thebigbang.com.icare.R;

public class HealthConditionActivity extends ActionBarActivity {
    private TextView tvBloodGroup;
    private TextView tvBloodPressure;
    private TextView tvBMI;
    private TextView tvAddHealthConditions;
    private ListView lvPresentDiseases;

    private static int profileId;
    private ArrayList<User_Profile> profileArrayList;
    private DBAdapter_User_Profile dbAdapterUserProfile;

    private ArrayList<Diseases> diseasesArrayList;
    private DBAdapter_Diseases dbAdapterDiseases;

    private DiseasesAdapter adapter;
    private String fromWhere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_health_condition);

        initialization();
        retrieveDataFromDatabase();
        eventClickListener();
    }

    private void eventClickListener() {
        tvAddHealthConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoAddDiseasesActivity = new Intent(HealthConditionActivity.this, AddDiseasesActivity.class);
                gotoAddDiseasesActivity.putExtra("id", profileId);
                Toast.makeText(getApplicationContext(),"profile Id: HealthConditions add"+profileId,Toast.LENGTH_SHORT).show();
                gotoAddDiseasesActivity.putExtra("from", "addDiseases");
                gotoAddDiseasesActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoAddDiseasesActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gotoAddDiseasesActivity);
            }
        });
    }

    private void retrieveDataFromDatabase() {
        Bundle extras=getIntent().getExtras();
        profileId=extras.getInt("id");
        profileArrayList= dbAdapterUserProfile.getProfileDetailsById(profileId);
        diseasesArrayList=dbAdapterDiseases.getDiseasesNameAndId(profileId);
        Toast.makeText(getApplicationContext(),"profile Id: healthConditions retrieve"+profileId,Toast.LENGTH_SHORT).show();
        setValuesInTextFields();

    }

    private void setValuesInTextFields() {
        adapter=new DiseasesAdapter(this,diseasesArrayList,profileId);
        lvPresentDiseases.setAdapter(adapter);
        for (User_Profile basicMedicalInfo:profileArrayList){
            tvBloodGroup.setText(basicMedicalInfo.getBloodGroup());
            tvBloodPressure.setText(basicMedicalInfo.getBloodPressure());
            String bp=basicMedicalInfo.getBloodPressure();
            if (!bp.contains("Normal")){
                tvBloodPressure.setTextColor(Color.parseColor("#FF0000"));
            }
            String bmi=basicMedicalInfo.getBMI();
            tvBMI.setText(bmi);
            if(!bmi.contains("IDEAL")){
                tvBMI.setTextColor(Color.parseColor("#FF0000"));
            }
        }

    }

    private void initialization() {

        tvBloodGroup= (TextView) findViewById(R.id.tvBloodGroupInHealthCondition);
        tvBloodPressure= (TextView) findViewById(R.id.tvBloodPressureInHealthCondition);
        tvBMI= (TextView) findViewById(R.id.tvBMIInHealthCondition);
        tvAddHealthConditions= (TextView) findViewById(R.id.tvAddIconInHealthConditions);

        lvPresentDiseases= (ListView) findViewById(R.id.lvDiseasesInHealthConditions);

        dbAdapterUserProfile =new DBAdapter_User_Profile(this);
        profileArrayList=new ArrayList<User_Profile>();

        diseasesArrayList=new ArrayList<>();
        dbAdapterDiseases=new DBAdapter_Diseases(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        retrieveDataFromDatabase();
    }
}
