package thebigbang.com.icare.Activities.Medical.Vaccination;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Vaccination;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Vaccine_Dose;
import thebigbang.com.icare.Model.Vaccination;
import thebigbang.com.icare.Model.Vaccine_Dose;
import thebigbang.com.icare.R;

public class EditCompleteDoseActivity extends AppCompatActivity {
    private EditText etCompleteDose;
    private EditText etCareCenter;
    private EditText etDate;
    private EditText etNextDate;
    private TextView btnUpdate;

    private int vaccineId;
    private int totalDose;
    private int profileId;

    private ArrayList<Vaccination> vaccineArrayList;
    private DBAdapter_Vaccination dbAdapterVaccination;
    private DBAdapter_Vaccine_Dose dbAdapterVaccineDose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_edit_complete_dose);

        Bundle extras=getIntent().getExtras();
        vaccineId =extras.getInt("id");
        totalDose=extras.getInt("totalDose");
        profileId=extras.getInt("profileId");

        initializations();
        retrieveDataFromDatabase();
        eventClickListener();
    }

    private void retrieveDataFromDatabase() {
        vaccineArrayList=dbAdapterVaccination.getVaccineDetailsById(vaccineId);
        setTextFieldValue();
    }

    private void setTextFieldValue() {
        for (Vaccination vaccine:vaccineArrayList){
            etCompleteDose.setText(String.valueOf(vaccine.getCompleteDose()));
            etCareCenter.setText(vaccine.getCareCenter());
            etNextDate.setText(vaccine.getNextDate());
        }
    }

    private void eventClickListener() {
        etNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(EditCompleteDoseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                etNextDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }

                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(EditCompleteDoseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                etDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }

                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String completeDoseStr=etCompleteDose.getText().toString();
                String careCenterStr=etCareCenter.getText().toString();
                String dateStr=etDate.getText().toString();
                String nextDateStr=etNextDate.getText().toString();

                if(completeDoseStr.isEmpty() || careCenterStr.isEmpty() || dateStr.isEmpty() || nextDateStr.isEmpty()){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditCompleteDoseActivity.this);
                    alertDialog.setTitle("OOps");
                    alertDialog.setMessage("You have to fill all filed carefully!");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);
                    alertDialog.show();

                    if (completeDoseStr.isEmpty()) {
                        etCompleteDose.setHintTextColor(Color.parseColor("red"));
                    }
                    if (careCenterStr.isEmpty()) {
                        etCareCenter.setHintTextColor(Color.parseColor("red"));
                    }
                    if (dateStr.isEmpty()) {
                        etDate.setHintTextColor(Color.parseColor("red"));
                    }
                    if (nextDateStr.isEmpty()) {
                        etNextDate.setHintTextColor(Color.parseColor("red"));
                    }

                }
                else{
                    dbAdapterVaccineDose.openDB();
                    dbAdapterVaccination.openDB();
                    int completeDose=Integer.parseInt(completeDoseStr);
                    int remainingDose=totalDose-completeDose;
                    if(remainingDose<0){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditCompleteDoseActivity.this);
                        alertDialog.setTitle("OOps");
                        alertDialog.setMessage("Total Dose = "+totalDose);
                        alertDialog.setIcon(android.R.drawable.ic_dialog_info);
                        alertDialog.show();
                    }else{
                        if (remainingDose==0){
                            //We have to put logic here
                        }
                        Vaccine_Dose dose=new Vaccine_Dose(vaccineId,dateStr,careCenterStr);
                        Vaccination vaccination=new Vaccination(completeDose,remainingDose,nextDateStr,careCenterStr);
                        Toast.makeText(EditCompleteDoseActivity.this, "Vaccine Id: "+vaccineId+" Date: "+dateStr
                                +" Next Date: "+nextDateStr, Toast.LENGTH_SHORT).show();
                        long insertCompleteDoseInfo=dbAdapterVaccineDose.addDoseDate(dose);
                        long updateVaccinationInfo=dbAdapterVaccination.updateVaccineDoseInfo(vaccination,vaccineId);
                        if(insertCompleteDoseInfo>=0){
                            Toast.makeText(EditCompleteDoseActivity.this, "Data insert in Dose Table Success!", Toast.LENGTH_SHORT).show();
                            if(updateVaccinationInfo>=0){
                                Toast.makeText(EditCompleteDoseActivity.this, "Data Updated in Vaccine Table success!", Toast.LENGTH_SHORT).show();


                                Intent gotoVaccineDetails = new Intent(EditCompleteDoseActivity.this, VaccineDetailsActivity.class);
                                gotoVaccineDetails.putExtra("id", vaccineId);
                                gotoVaccineDetails.putExtra("profileId", profileId);
                                gotoVaccineDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                gotoVaccineDetails.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(gotoVaccineDetails);
                                dbAdapterVaccination.closeDB();
                                dbAdapterVaccineDose.closeDB();
                                finish();
                            }
                        }
                    }

                }
            }
        });
    }

    private void initializations() {
        etCompleteDose= (EditText) findViewById(R.id.etCompleteDoseInEdit);
        etCareCenter= (EditText) findViewById(R.id.etCareCenterInEditComplete);
        etDate= (EditText) findViewById(R.id.etDateInEditComplete);
        etNextDate= (EditText) findViewById(R.id.etNextDateInEditComplete);
        btnUpdate= (TextView) findViewById(R.id.btnUpdateInEditCompleteDose);

        vaccineArrayList=new ArrayList<>();
        dbAdapterVaccination=new DBAdapter_Vaccination(this);
        dbAdapterVaccineDose=new DBAdapter_Vaccine_Dose(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
