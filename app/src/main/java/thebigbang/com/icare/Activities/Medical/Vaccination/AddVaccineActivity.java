package thebigbang.com.icare.Activities.Medical.Vaccination;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Vaccination;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Vaccine_Dose;
import thebigbang.com.icare.Model.Vaccination;
import thebigbang.com.icare.R;

public class AddVaccineActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    private EditText etTotalDose;
    private EditText etNextDate;
    private EditText etCareCenter;
    private TextView btnSave;
    private Spinner spVaccineName;
    ArrayAdapter<String> vaccineAdapter;

    private ArrayList<Vaccination> vaccinationArrayList;
    private DBAdapter_Vaccination dbAdapterVaccination;
    private DBAdapter_Vaccine_Dose dbAdapterVaccineDoseDate;

    private String fromWhere;
    private static String vaccineNameStr;
    private static String date;
    private static String nextDate;
    private static int profileId;
    private static int vaccineId;
    private int completeDose=0;
    private static long reminderTime;
    private static long eventTime;

    private PendingIntent pendingIntent;

    private String[] vaccinesName= {"DTP","HEPATITIS A","HEPATITIS B","HiB","INFLUENZA","MMR","PNEUMOCOCCAL","POLIOMYELITIS","ROTAVIRUS","VARICELLA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_add_vaccine);

        Bundle extras=getIntent().getExtras();
        fromWhere=extras.getString("from");
        initialization();
        retrieveDataFromDatabase();
        eventClickListener();

    }

    private void retrieveDataFromDatabase() {

        if(fromWhere.contains("vaccineDetails")){
            Bundle extras=getIntent().getExtras();
            profileId=extras.getInt("id");
            vaccineId=extras.getInt("vaccineId");
            vaccinationArrayList=dbAdapterVaccination.getVaccineDetailsById(vaccineId);
            setEditTextValueFromDatabase();
            btnSave.setText("Update");
        }
        else if(fromWhere.contains("vaccinationActivity")){
            Bundle extras=getIntent().getExtras();
            profileId=extras.getInt("id");
        }
    }

    private void setEditTextValueFromDatabase() {
        for(Vaccination vaccination:vaccinationArrayList){
            vaccineNameStr=vaccination.getName();
            etTotalDose.setText(String.valueOf(vaccination.getTotalDose()));
            etNextDate.setText(vaccination.getNextDate());
            etCareCenter.setText(vaccination.getCareCenter());
            completeDose=vaccination.getCompleteDose();
            spVaccineName.setSelection(vaccineAdapter.getPosition(vaccineNameStr));
        }

    }

    private void eventClickListener() {
        spVaccineName.setOnItemSelectedListener(AddVaccineActivity.this);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String totalDoseStr = etTotalDose.getText().toString().trim();
                nextDate = etNextDate.getText().toString();
                String careCenter = etCareCenter.getText().toString();

                if (vaccineNameStr.isEmpty() || totalDoseStr.isEmpty() || nextDate.isEmpty() || careCenter.isEmpty() ) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddVaccineActivity.this);
                    alertDialog.setTitle("OOps");
                    alertDialog.setMessage("You have to fill all filed carefully!");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);
                    alertDialog.show();

                    if (totalDoseStr.isEmpty()) {
                        etTotalDose.setHintTextColor(Color.parseColor("red"));
                    }
                    if (totalDoseStr.isEmpty()) {
                        etTotalDose.setHintTextColor(Color.parseColor("red"));
                    }
                    if (careCenter.isEmpty()) {
                        etCareCenter.setHintTextColor(Color.parseColor("red"));
                    }
                    if (careCenter.isEmpty()) {
                        etCareCenter.setHintTextColor(Color.parseColor("red"));
                    }

                }else {
                    int totalDose = Integer.parseInt(totalDoseStr);
                    int remainingDose=totalDose-completeDose;
                    Vaccination vaccine=new Vaccination(profileId,vaccineNameStr,totalDose,completeDose,remainingDose,nextDate,careCenter,eventTime);
                    dbAdapterVaccination.openDB();

                    if(fromWhere.contains("vaccinationActivity")){

                        long insert= dbAdapterVaccination.addNewVaccine(vaccine);
                        if(insert>=0){
                            Toast.makeText(AddVaccineActivity.this, "Data insert Successfully!", Toast.LENGTH_SHORT).show();
                            Intent gotoHealthConditions=new Intent(AddVaccineActivity.this,VaccinationActivity.class);
                            gotoHealthConditions.putExtra("from","addVaccine");
                            gotoHealthConditions.putExtra("id",profileId);
                            gotoHealthConditions.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            gotoHealthConditions.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotoHealthConditions);
                            dbAdapterVaccination.closeDB();

                            Intent myIntent = new Intent(AddVaccineActivity.this, AlarmReceiver.class);
                            pendingIntent = PendingIntent.getBroadcast(AddVaccineActivity.this, 0, myIntent,0);

                            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC, reminderTime, pendingIntent);

                            finish();
                        }
                    }
                    else if (fromWhere.contains("vaccineDetails")){
                        long update= dbAdapterVaccination.updateVaccineInfo(vaccine, vaccineId);

                        if(update>=0){
                            Toast.makeText(AddVaccineActivity.this, "Data Updated Successfully!", Toast.LENGTH_SHORT).show();
                            Intent gotoVaccineDetails=new Intent(AddVaccineActivity.this,VaccineDetailsActivity.class);
                            gotoVaccineDetails.putExtra("from", "addVaccine");
                            gotoVaccineDetails.putExtra("id", vaccineId);
                            gotoVaccineDetails.putExtra("profileId", profileId);
                            gotoVaccineDetails.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            gotoVaccineDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity( gotoVaccineDetails);
                            dbAdapterVaccination.closeDB();

//                            Intent myIntent = new Intent(AddVaccineActivity.this, AlarmReceiver.class);
//                            pendingIntent = PendingIntent.getBroadcast(AddVaccineActivity.this, 0, myIntent,0);
//
//                            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//                            alarmManager.set(AlarmManager.RTC, reminderTime, pendingIntent);

                            finish();
                        }

                    }

                }

            }
        });

//        etDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar c = Calendar.getInstance();
//                final int mYear = c.get(Calendar.YEAR);
//                final int mMonth = c.get(Calendar.MONTH);
//                final int mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog dpd = new DatePickerDialog(AddVaccineActivity.this,
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//                                etDate.setText(dayOfMonth + "/"
//                                        + (monthOfYear + 1) + "/" + year);
//                                date=dayOfMonth+"/"+(monthOfYear + 1)+"/"+year;
//
//                            }
//
//                        }, mYear, mMonth, mDay);
//                dpd.show();
//            }
//        });
        etNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(AddVaccineActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                etNextDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                nextDate=dayOfMonth+"/"+(monthOfYear + 1)+"/"+year;

                                c.set(Calendar.MONTH, monthOfYear+1);
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                c.set(Calendar.HOUR_OF_DAY, 20);
                                c.set(Calendar.MINUTE, 30);
                                c.set(Calendar.SECOND, 0);
                                c.set(Calendar.AM_PM,Calendar.PM);
                                eventTime=c.getTimeInMillis();
                                int noOfDays=1;
                                long oneDay= AlarmManager.INTERVAL_DAY;
                                reminderTime=eventTime-(noOfDays*oneDay);
                                Log.e("Reminder Time",reminderTime+"");
                                Log.e("One Day",oneDay+"");
                                Log.e("Event Time",eventTime+"");

                            }

                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
    }

    private void initialization() {
        etTotalDose= (EditText) findViewById(R.id.etTotalDose);
        etNextDate= (EditText) findViewById(R.id.etNextDoseDate);
        etCareCenter= (EditText) findViewById(R.id.etCareCenter);
        btnSave= (TextView) findViewById(R.id.btnSaveInAddVaccine);
        spVaccineName= (Spinner) findViewById(R.id.spVaccineName);
        vaccineAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,vaccinesName);
        spVaccineName.setAdapter(vaccineAdapter);

        nextDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        etNextDate.setText(nextDate);
        dbAdapterVaccination=new DBAdapter_Vaccination(this);
        vaccinationArrayList=new ArrayList<>();

//        dbAdapterVaccineDoseDate=new DBAdapter_Vaccine_Dose_Date(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent==spVaccineName){
            vaccineNameStr=parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
