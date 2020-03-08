package thebigbang.com.icare.Activities.Medical.Vaccination;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thebigbang.com.icare.Activities.Diet.CreateNewDietPlanActivity;
import thebigbang.com.icare.Activities.Medical.PresentHealthInfo.HealthConditionActivity;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Diseases;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Vaccination;
import thebigbang.com.icare.Model.Diseases;
import thebigbang.com.icare.Model.Vaccination;
import thebigbang.com.icare.R;

public class VaccineDetailsActivity extends ActionBarActivity {
    private TextView tvVaccineName;
    private TextView tvTotalDose;
    private TextView tvRemainingDose;
    private TextView tvNextDate;
    private TextView tvCareCenter;
    private TextView tvCompleteDose;

    private static int vaccineId;
    private static int profileId;
    private static int totalDose;

    private ArrayList<Vaccination> vaccinationArrayList;
    private DBAdapter_Vaccination dbAdapterVaccination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_vaccine_details);

        Bundle extras=getIntent().getExtras();
        vaccineId =extras.getInt("id");
        profileId=extras.getInt("profileId");

        initialization();
        retrieveDataFromDatabase();
        eventClickListener();
    }

    private void eventClickListener() {
        tvCompleteDose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VaccineDetailsActivity.this, "You Clicked On Edit Icon", Toast.LENGTH_SHORT).show();
                Intent gotoEditCompleteDoseActivity = new Intent(VaccineDetailsActivity.this, EditCompleteDoseActivity.class);
                gotoEditCompleteDoseActivity.putExtra("id", vaccineId);
                gotoEditCompleteDoseActivity.putExtra("totalDose", totalDose);
                gotoEditCompleteDoseActivity.putExtra("profileId", profileId);
                gotoEditCompleteDoseActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoEditCompleteDoseActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gotoEditCompleteDoseActivity);
            }
        });
    }

    private void retrieveDataFromDatabase() {
        vaccinationArrayList=dbAdapterVaccination.getVaccineDetailsById(vaccineId);
        for (Vaccination vaccine:vaccinationArrayList){
            tvVaccineName.setText(vaccine.getName());
            totalDose=vaccine.getTotalDose();
            tvTotalDose.setText(String.valueOf(totalDose));
            tvNextDate.setText(vaccine.getNextDate());
            tvRemainingDose.setText(String.valueOf(vaccine.getRemainingDose()));
            tvCareCenter.setText(vaccine.getCareCenter());
            tvCompleteDose.setText(String.valueOf(vaccine.getCompleteDose()));
        }
    }

    private void initialization() {
        tvVaccineName= (TextView) findViewById(R.id.tvVaccineNameInDetails);
        tvTotalDose= (TextView) findViewById(R.id.tvTotalDoseInDetails);
        tvNextDate= (TextView) findViewById(R.id.tvNextDateInDetails);
        tvRemainingDose= (TextView) findViewById(R.id.tvRemainingDoseInDetails);
        tvCareCenter= (TextView) findViewById(R.id.tvCareCenterInDetails);
        tvCompleteDose= (TextView) findViewById(R.id.tvCompleteDoseInDetails);

        vaccinationArrayList=new ArrayList<>();
        dbAdapterVaccination=new DBAdapter_Vaccination(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vaccine_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.editDietDetails) {
            Intent gotoAddVaccineActivity = new Intent(VaccineDetailsActivity.this,AddVaccineActivity.class);
            gotoAddVaccineActivity.putExtra("id", profileId);
            gotoAddVaccineActivity.putExtra("vaccineId", vaccineId);
            gotoAddVaccineActivity.putExtra("from", "vaccineDetails");
            gotoAddVaccineActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            gotoAddVaccineActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(gotoAddVaccineActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
