package thebigbang.com.icare.Activities.Medical.PresentHealthInfo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Diseases;
import thebigbang.com.icare.Model.Diseases;
import thebigbang.com.icare.R;

public class AddDiseasesActivity extends AppCompatActivity {
    private EditText etDiseasesName;
    private EditText etSymptoms;
    private EditText etMedicine;
    private EditText etComments;
    private EditText etDate;
    private TextView tvSave;

    private DBAdapter_Diseases dbAdapterDiseases;
    private ArrayList<Diseases> diseasesArrayList;


    private String fromWhere;
    private static String date;
    private static int profileId;
    private static int diseasesId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_add_diseases);

        Bundle extras=getIntent().getExtras();
        fromWhere=extras.getString("from");

        initialization();

        if(fromWhere.contains("diseasesDetails")){
            profileId=extras.getInt("id");
            diseasesId=extras.getInt("diseasesId");
            diseasesArrayList=dbAdapterDiseases.getDiseasesDetailsById(diseasesId);
            setEditTextValueFromDatabase();
            Toast.makeText(getApplicationContext(),"profile Id: "+profileId,Toast.LENGTH_SHORT).show();
            tvSave.setText("Update");
        }
        else if(fromWhere.contains("addDiseases")){
            profileId=extras.getInt("id");
            Toast.makeText(getApplicationContext(),"profile Id: "+profileId,Toast.LENGTH_SHORT).show();
        }
        eventClickListener();

    }

    private void setEditTextValueFromDatabase() {
        for (Diseases diseasesInfo:diseasesArrayList){
            etDiseasesName.setText(diseasesInfo.getName());
            etSymptoms.setText(diseasesInfo.getSymptoms());
            etMedicine.setText(diseasesInfo.getMedicines());
            etComments.setText(diseasesInfo.getComments());
            etDate.setText(diseasesInfo.getDate());

        }
    }

    private void eventClickListener() {
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diseasesName=etDiseasesName.getText().toString().trim();
                String symptoms=etSymptoms.getText().toString();
                String medicine=etMedicine.getText().toString();
                String comments=etComments.getText().toString();
                date=etDate.getText().toString();

                if(diseasesName.isEmpty() || symptoms.isEmpty() || medicine.isEmpty() || comments.isEmpty() || date.isEmpty()){

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddDiseasesActivity.this);
                    alertDialog.setTitle("OOps");
                    alertDialog.setMessage("You have to fill all filed carefully!");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);
                    alertDialog.show();
                    if(diseasesName.isEmpty()){
                        etDiseasesName.setHintTextColor(Color.parseColor("red"));
                    }

                    if(symptoms.isEmpty()){
                        etSymptoms.setHintTextColor(Color.parseColor("red"));
                    }
                    if(medicine.isEmpty()){
                        etMedicine.setHintTextColor(Color.parseColor("red"));
                    }
                    if(comments.isEmpty()){

                        etComments.setHintTextColor(Color.parseColor("red"));
                    }

                }else{
                    Diseases diseases=new Diseases(profileId,diseasesName,symptoms,comments,medicine,date);
                    dbAdapterDiseases.openDB();
                    if(fromWhere.contains("addDiseases")){

                        long insert= dbAdapterDiseases.addNewDiseases(diseases);
                        if(insert>=0){
                            Toast.makeText(AddDiseasesActivity.this, "Data insert Successfully!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),"profile Id Insert: "+profileId,Toast.LENGTH_SHORT).show();
                            Intent gotoHealthConditions=new Intent(AddDiseasesActivity.this,HealthConditionActivity.class);
                            gotoHealthConditions.putExtra("from","addDiseases");
                            gotoHealthConditions.putExtra("id",profileId);
                            gotoHealthConditions.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            gotoHealthConditions.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotoHealthConditions);
                            dbAdapterDiseases.closeDB();
                            finish();
                        }
                    }
                    else if (fromWhere.contains("diseasesDetails")){
                        long update= dbAdapterDiseases.updateDiseasesInfo(diseases, diseasesId);
                        if(update>=0){
                            Toast.makeText(AddDiseasesActivity.this, "Data update Successfully!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),"profile Id Update: "+profileId,Toast.LENGTH_SHORT).show();
                            Intent gotoDiseasesDetails=new Intent(AddDiseasesActivity.this,DiseasesDetailsActivity.class);
                            gotoDiseasesDetails.putExtra("from", "addDiseases");
                            gotoDiseasesDetails.putExtra("id", diseasesId);
                            gotoDiseasesDetails.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            gotoDiseasesDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotoDiseasesDetails);
                            dbAdapterDiseases.closeDB();
                            finish();
                        }
                    }
                }

            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(AddDiseasesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                etDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                date=dayOfMonth+"/"+(monthOfYear + 1)+"/"+year;

                            }

                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
    }

    private void initialization() {
        etDiseasesName= (EditText) findViewById(R.id.etDiseasesName);
        etSymptoms= (EditText) findViewById(R.id.etSymptomsInAddDiseases);
        etMedicine= (EditText) findViewById(R.id.etMedicineInAddDiseases);
        etComments= (EditText) findViewById(R.id.etCommentsInAddDiseases);
        etDate= (EditText) findViewById(R.id.etDateInAddDiseases);
        tvSave= (TextView) findViewById(R.id.btnSaveInAddDiseases);

        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        etDate.setText(date);

        dbAdapterDiseases=new DBAdapter_Diseases(this);
        diseasesArrayList=new ArrayList<>();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
