package thebigbang.com.icare.Activities.Medical.DoctorsProfile;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import thebigbang.com.icare.Activities.Medical.Vaccination.AddVaccineActivity;
import thebigbang.com.icare.Adapters.DoctorProfileListAdapter;
import thebigbang.com.icare.Adapters.VaccineAdapter;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Doctors_Profile;
import thebigbang.com.icare.Model.Doctors;
import thebigbang.com.icare.R;

public class MyDoctorsActivity extends ActionBarActivity {
    private EditText etSearch;
    private ListView lvDoctors;
    private TextView tvAddNewDoctors;

    private static int profileId;
    private static String fromWhere;

    private ArrayList<Doctors> doctorsArrayList;
    private DoctorProfileListAdapter adapter;
    private DBAdapter_Doctors_Profile dbAdapterDoctorsProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_my_doctors);

        initializations();
        retrieveDataFromDatabase();
        eventClickListener();
    }
    private void retrieveDataFromDatabase() {
        Bundle extras=getIntent().getExtras();
        fromWhere=extras.getString("from");

        profileId=extras.getInt("id");

        doctorsArrayList=dbAdapterDoctorsProfile.getDoctorsNameSpecialityFee(profileId);
        setValueInTextField();
    }

    private void setValueInTextField() {
        adapter=new DoctorProfileListAdapter(this,doctorsArrayList,profileId);
        lvDoctors.setAdapter(adapter);
    }


    private void eventClickListener() {
        tvAddNewDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoCreateDoctorsProfile=new Intent(MyDoctorsActivity.this,CreateDoctorsProfile.class);
                gotoCreateDoctorsProfile.putExtra("from", "MyDoctorsActivity");
                gotoCreateDoctorsProfile.putExtra("id", profileId);
                gotoCreateDoctorsProfile.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                gotoCreateDoctorsProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoCreateDoctorsProfile);

            }
        });
    }

    private void initializations() {
        etSearch= (EditText) findViewById(R.id.etSearchDoctors);
        lvDoctors= (ListView) findViewById(R.id.lvDoctors);
        tvAddNewDoctors= (TextView) findViewById(R.id.tvAddNewDoctorProfile);
        doctorsArrayList=new ArrayList<>();
        dbAdapterDoctorsProfile=new DBAdapter_Doctors_Profile(this);

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        retrieveDataFromDatabase();
    }

}
