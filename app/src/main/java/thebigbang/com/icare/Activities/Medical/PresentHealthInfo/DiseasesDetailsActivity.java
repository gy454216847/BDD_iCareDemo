package thebigbang.com.icare.Activities.Medical.PresentHealthInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thebigbang.com.icare.Activities.Medical.PresentHealthInfo.Prescriptions.PrescriptionsActivity;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Diseases;
import thebigbang.com.icare.Model.Diseases;
import thebigbang.com.icare.R;

public class DiseasesDetailsActivity extends Activity {
    private TextView tvDiseasesName;
    private TextView tvSymptoms;
    private TextView tvMedicines;
    private TextView tvComments;
    private TextView tvEditHealthConditions;
    private TextView tvPrescriptions;
    private static int diseasesId;
    private static int profileId;

    private ArrayList<Diseases> diseasesArrayList;
    private DBAdapter_Diseases dbAdapterDiseases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_diseases_details);

        Bundle extras=getIntent().getExtras();
        diseasesId=extras.getInt("id");
        profileId=extras.getInt("profileId");

        initializations();
        retrieveDataFromDatabase();
        eventClickListener();
    }

    private void eventClickListener() {
        tvEditHealthConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View ev) {
                Intent gotoAddDis=new Intent(DiseasesDetailsActivity.this,AddDiseasesActivity.class);
                gotoAddDis.putExtra("from", "diseasesDetails");
                gotoAddDis.putExtra("diseasesId", diseasesId);
                gotoAddDis.putExtra("id", profileId);
                Toast.makeText(getApplicationContext(), "profile Id: " + profileId, Toast.LENGTH_SHORT).show();
                gotoAddDis.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                gotoAddDis.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoAddDis);
            }
        });
        tvPrescriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoPrescriptionsActivity=new Intent(DiseasesDetailsActivity.this,PrescriptionsActivity.class);
                gotoPrescriptionsActivity.putExtra("from", "diseasesDetails");
                gotoPrescriptionsActivity.putExtra("diseasesId", diseasesId);
                Toast.makeText(DiseasesDetailsActivity.this, "disease Id: "+diseasesId, Toast.LENGTH_SHORT).show();
                gotoPrescriptionsActivity.putExtra("id", profileId);
                gotoPrescriptionsActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                gotoPrescriptionsActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoPrescriptionsActivity);

            }
        });
    }

    private void retrieveDataFromDatabase() {
        diseasesArrayList=dbAdapterDiseases.getDiseasesDetailsById(diseasesId);
        for (Diseases diseases:diseasesArrayList){
            tvDiseasesName.setText(diseases.getName());
            tvSymptoms.setText(diseases.getSymptoms());
            tvMedicines.setText(diseases.getMedicines());
            tvComments.setText(diseases.getComments());
        }

    }

    private void initializations() {
        tvDiseasesName= (TextView) findViewById(R.id.tvDiseasesNameInDiseasesDetails);
        tvSymptoms= (TextView) findViewById(R.id.tvSymptomsInDetails);
        tvMedicines= (TextView) findViewById(R.id.tvMedicineInDetails);
        tvComments= (TextView) findViewById(R.id.tvCommentsInDetails);
        tvPrescriptions= (TextView) findViewById(R.id.tvPrescriptionsInDetails);
        tvEditHealthConditions= (TextView) findViewById(R.id.tvEditHealthConditionsInDetails);

        diseasesArrayList=new ArrayList<>();
        dbAdapterDiseases=new DBAdapter_Diseases(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
