package thebigbang.com.icare.Activities.Medical.Vaccination;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import thebigbang.com.icare.Adapters.VaccineAdapter;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Vaccination;
import thebigbang.com.icare.Model.Vaccination;
import thebigbang.com.icare.R;

public class VaccinationActivity extends ActionBarActivity {
    private ListView lvVaccinations;
    private EditText etSearch;
    private TextView tvAddNewVaccine;

    private static int profileId;
    private static String fromWhere;

    private ArrayList<Vaccination> vaccinationArrayList;
    private VaccineAdapter adapter;
    private DBAdapter_Vaccination dbAdapterVaccination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_vaccination);

        initialization();
        retrieveDataFromDatabase();
        eventClickListener();
    }

    private void retrieveDataFromDatabase() {
        Bundle extras=getIntent().getExtras();
        fromWhere=extras.getString("from");
        profileId=extras.getInt("id");
        vaccinationArrayList=dbAdapterVaccination.getVaccineNameAndId(profileId);
        setValueInTextField();
    }

    private void setValueInTextField() {
        adapter=new VaccineAdapter(this,vaccinationArrayList,profileId);
        lvVaccinations.setAdapter(adapter);
    }

    private void eventClickListener() {
        tvAddNewVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAddVaccineActivity=new Intent(VaccinationActivity.this,AddVaccineActivity.class);
                gotoAddVaccineActivity.putExtra("from","vaccinationActivity");
                gotoAddVaccineActivity.putExtra("id",profileId);
                gotoAddVaccineActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                gotoAddVaccineActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoAddVaccineActivity);

            }
        });
    }

    private void initialization() {
        etSearch= (EditText) findViewById(R.id.etSearchInVaccination);
        lvVaccinations= (ListView) findViewById(R.id.lvVaccinations);
        tvAddNewVaccine= (TextView) findViewById(R.id.tvAddNewVaccine);
        vaccinationArrayList=new ArrayList<>();
        dbAdapterVaccination=new DBAdapter_Vaccination(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        retrieveDataFromDatabase();
    }

}
