package thebigbang.com.icare.Activities.Medical.DoctorsProfile;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thebigbang.com.icare.Activities.Medical.Vaccination.VaccinationActivity;
import thebigbang.com.icare.Activities.Medical.Vaccination.VaccineDetailsActivity;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Doctors_Profile;
import thebigbang.com.icare.Model.Doctors;
import thebigbang.com.icare.Model.Vaccination;
import thebigbang.com.icare.R;

public class CreateDoctorsProfile extends ActionBarActivity {
    private EditText etName;
    private EditText etSpecialities;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etHospital;
    private EditText etChamberAddress;
    private EditText etFee;

    private TextView btnSave;

    private static int profileId;
    private static int doctorsID;
    private String fromWhere;

    private ArrayList<Doctors> doctorsArrayList;
    private DBAdapter_Doctors_Profile dbAdapterDoctorsProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_create_doctors_profile);
        Bundle extras=getIntent().getExtras();
        fromWhere=extras.getString("from");
        initialization();
        retrieveDataFromDatabase();
        eventClickListener();
    }

    private void retrieveDataFromDatabase() {
        if(fromWhere.contains("DoctorsDetailsActivity")){
            Bundle extras=getIntent().getExtras();
            profileId=extras.getInt("id");
            doctorsID=extras.getInt("doctorsId");
            doctorsArrayList=dbAdapterDoctorsProfile.getDoctorsDetailsById(doctorsID);
            setEditTextValueFromDatabase();
            btnSave.setText("Update");
        }
        else if(fromWhere.contains("MyDoctorsActivity")){
            Bundle extras=getIntent().getExtras();
            profileId=extras.getInt("id");
        }
    }

    private void setEditTextValueFromDatabase() {
        for(Doctors doctors:doctorsArrayList){
            etName.setText(doctors.getName());
            etSpecialities.setText(doctors.getSpecialities());
            etHospital.setText(doctors.getHospital());
            etEmail.setText(doctors.getEmail());
            etPhone.setText(doctors.getPhone());
            etFee.setText(doctors.getFee());
            etChamberAddress.setText(doctors.getChamberAddress());

        }
    }

    private void eventClickListener() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=etName.getText().toString().trim();
                String specialities=etSpecialities.getText().toString().trim();
                String hospital=etHospital.getText().toString().trim();
                String email=etEmail.getText().toString().trim();
                String phone=etPhone.getText().toString().trim();
                String chamber=etChamberAddress.getText().toString().trim();
                String fee=etFee.getText().toString().trim();

                if (name.isEmpty()|| specialities.isEmpty()|| hospital.isEmpty()|| email.isEmpty()||
                        phone.isEmpty()|| chamber.isEmpty()|| fee.isEmpty()){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateDoctorsProfile.this);
                    alertDialog.setTitle("OOps");
                    alertDialog.setMessage("You have to fill all filed carefully!");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);
                    alertDialog.show();
                    if (name.isEmpty()) {
                        etName.setHintTextColor(Color.parseColor("red"));
                    }
                    if (specialities.isEmpty()) {
                        etSpecialities.setHintTextColor(Color.parseColor("red"));
                    }
                    if (hospital.isEmpty()) {
                        etHospital.setHintTextColor(Color.parseColor("red"));
                    }
                    if (email.isEmpty()) {
                        etEmail.setHintTextColor(Color.parseColor("red"));
                    }
                    if (phone.isEmpty()) {
                        etPhone.setHintTextColor(Color.parseColor("red"));
                    }
                    if (chamber.isEmpty()) {
                        etChamberAddress.setHintTextColor(Color.parseColor("red"));
                    }
                    if (fee.isEmpty()) {
                        etFee.setHintTextColor(Color.parseColor("red"));
                    }
                }
                else{
                    dbAdapterDoctorsProfile.openDB();
                    Doctors doctors=new Doctors(profileId,name,specialities,phone,email,hospital,chamber,fee);
                    if(fromWhere.contains("MyDoctorsActivity")){
                        long insert=dbAdapterDoctorsProfile.addNewDoctorsProfile(doctors);
                        if(insert>=0){
                            Toast.makeText(CreateDoctorsProfile.this, "Data insert Successfully!", Toast.LENGTH_SHORT).show();
                            Intent gotoMyDoctorsActivity=new Intent(CreateDoctorsProfile.this,MyDoctorsActivity.class);
                            gotoMyDoctorsActivity.putExtra("from", "CreateDoctorsProfile");
                            gotoMyDoctorsActivity.putExtra("id", profileId);
                            gotoMyDoctorsActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            gotoMyDoctorsActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotoMyDoctorsActivity);
                            dbAdapterDoctorsProfile.closeDB();
                            finish();
                        }
                    }
                    else if (fromWhere.contains("DoctorsDetailsActivity")){

                        long update=dbAdapterDoctorsProfile.updateDoctorsInfo(doctors,doctorsID);
                        if(update>=0){
                            Toast.makeText(CreateDoctorsProfile.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                            Intent gotoDoctorsDetails=new Intent(CreateDoctorsProfile.this,DoctorsDetailsActivity.class);
                            gotoDoctorsDetails.putExtra("from", "Update");
                            gotoDoctorsDetails.putExtra("id", doctorsID);
                            gotoDoctorsDetails.putExtra("profileId", profileId);
                            gotoDoctorsDetails.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            gotoDoctorsDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity( gotoDoctorsDetails);
                            dbAdapterDoctorsProfile.closeDB();
                            finish();
                        }
                    }
                }

            }
        });
    }

    private void initialization() {

        etName= (EditText) findViewById(R.id.etDoctorsName);
        etSpecialities= (EditText) findViewById(R.id.etDoctorsSpecialities);
        etPhone= (EditText) findViewById(R.id.etDoctorsPhone);
        etEmail= (EditText) findViewById(R.id.etDoctorsEmail);
        etHospital= (EditText) findViewById(R.id.etClinicOrHospital);
        etChamberAddress= (EditText) findViewById(R.id.etChamberAddress);
        etFee= (EditText) findViewById(R.id.etFeeInAddDoctors);

        btnSave= (TextView) findViewById(R.id.btnSaveInAddDoctors);

        doctorsArrayList=new ArrayList<>();
        dbAdapterDoctorsProfile=new DBAdapter_Doctors_Profile(this);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
