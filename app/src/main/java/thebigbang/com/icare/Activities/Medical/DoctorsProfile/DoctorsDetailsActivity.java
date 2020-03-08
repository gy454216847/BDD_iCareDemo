package thebigbang.com.icare.Activities.Medical.DoctorsProfile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thebigbang.com.icare.Activities.Medical.Vaccination.AddVaccineActivity;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Doctors_Profile;
import thebigbang.com.icare.Model.Doctors;
import thebigbang.com.icare.R;

public class DoctorsDetailsActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvSpecialities;
    private TextView tvDegrees;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvChamberAddress;
    private TextView tvDoctorFee;


    private static int doctorsId;
    private static int profileId;
    private static String phone;

    private ArrayList<Doctors> doctorsArrayList;
    private DBAdapter_Doctors_Profile dbAdapterDoctorsProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_doctors_details);

        Bundle extras=getIntent().getExtras();
        doctorsId =extras.getInt("id");
        profileId=extras.getInt("profileId");
        Toast.makeText(DoctorsDetailsActivity.this, "doctorId "+doctorsId+" ProfileId: "+profileId, Toast.LENGTH_SHORT).show();
        initializations();
        retrieveDataFromDatabase();
        eventClickListener();
    }

    private void eventClickListener() {
        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                String p="tel:" +phone;
                callIntent.setData(Uri.parse(p));
                startActivity(callIntent);

            }
        });
    }
    private void retrieveDataFromDatabase() {
        doctorsArrayList=dbAdapterDoctorsProfile.getDoctorsDetailsById(doctorsId);
        for(Doctors doctors:doctorsArrayList){
            tvName.setText(doctors.getName());
            tvSpecialities.setText(doctors.getSpecialities());
            tvDegrees.setText(doctors.getHospital());
            tvEmail.setText(doctors.getEmail());
            tvPhone.setText(doctors.getPhone());
            phone=doctors.getPhone();
            tvChamberAddress.setText("Address: "+doctors.getChamberAddress());
            tvDoctorFee.setText("Fee: "+ doctors.getFee()+" BDT");
        }
    }

    private void initializations() {
        tvName= (TextView) findViewById(R.id.tvNameInDoctorsDetails);
        tvSpecialities= (TextView) findViewById(R.id.tvSpecialitiesInDoctorsDetails);
        tvDegrees= (TextView) findViewById(R.id.tvDegreesInDoctorsDetails);
        tvEmail= (TextView) findViewById(R.id.tvEmailInDoctorsDetails);
        tvPhone= (TextView) findViewById(R.id.tvPhoneInDoctorsDetails);
        tvChamberAddress= (TextView) findViewById(R.id.tvChamberAddress);
        tvDoctorFee= (TextView) findViewById(R.id.tvDoctorFee);

        doctorsArrayList=new ArrayList<>();
        dbAdapterDoctorsProfile=new DBAdapter_Doctors_Profile(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doctors_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.editDoctorsInfo) {
            Intent gotoCreateDoctorsProfile = new Intent(DoctorsDetailsActivity.this,CreateDoctorsProfile.class);
            gotoCreateDoctorsProfile.putExtra("id", profileId);
            gotoCreateDoctorsProfile.putExtra("doctorsId", doctorsId);
            gotoCreateDoctorsProfile.putExtra("from", "DoctorsDetailsActivity");
            gotoCreateDoctorsProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            gotoCreateDoctorsProfile.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(gotoCreateDoctorsProfile);
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
