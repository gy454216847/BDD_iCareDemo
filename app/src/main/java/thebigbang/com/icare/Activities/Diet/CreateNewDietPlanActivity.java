package thebigbang.com.icare.Activities.Diet;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Diet;
import thebigbang.com.icare.Model.Diet_Plan;
import thebigbang.com.icare.R;

public class CreateNewDietPlanActivity extends AppCompatActivity {
    private EditText etBreakFast;
    private EditText etLunch;
    private EditText etAfternoonSnacks;
    private EditText etDinner;
    private EditText etNotes;
    private EditText etDate;
    private TextView btnSave;

    private ArrayList<Diet_Plan> dietPlanArrayList;
    private DBAdapter_Diet dbAdapterDiet;

    private String fromWhere;
    private String dayOfWeek;
    private int profileId;
    private int dietId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_create_new_diet_plan);

        initializations();
        Bundle extras=getIntent().getExtras();
        fromWhere=extras.getString("from");
        if(fromWhere.contains("DietPlanActivity")){
            profileId=extras.getInt("id");
        }
        else if(fromWhere.contains("DietDetailsActivity")) {
            dietId=extras.getInt("dietId");
            profileId=extras.getInt("id");
            retrieveDataFromDatabase();
            btnSave.setText("Update");
        }
        eventClickListener();
    }

    private void retrieveDataFromDatabase() {
        dietPlanArrayList=dbAdapterDiet.getDietPlanDetails(dietId);
        for(Diet_Plan diet:dietPlanArrayList){
            etBreakFast.setText(diet.getBreakFast());
            etLunch.setText(diet.getLunch());
            etAfternoonSnacks.setText(diet.getAfternoonSnacks());
            etDinner.setText(diet.getDinner());
            etNotes.setText(diet.getNotes());
            etDate.setText(diet.getDate());
            dayOfWeek=diet.getDay();
        }

    }

    private void eventClickListener() {
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(CreateNewDietPlanActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                etDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                                Date date = new Date(year, monthOfYear, dayOfMonth-1);
                                dayOfWeek = simpledateformat.format(date);
                                Toast.makeText(CreateNewDietPlanActivity.this, "Day: "+dayOfWeek, Toast.LENGTH_SHORT).show();
                            }

                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAdapterDiet.openDB();
                String breakFast=etBreakFast.getText().toString().trim();
                String lunch=etLunch.getText().toString().trim();
                String snacks=etAfternoonSnacks.getText().toString().trim();
                String dinner=etDinner.getText().toString().trim();
                String notes=etNotes.getText().toString().trim();
                String date=etDate.getText().toString();
                Toast.makeText(CreateNewDietPlanActivity.this, "day"+dayOfWeek, Toast.LENGTH_SHORT).show();
                if(breakFast.isEmpty() || lunch.isEmpty() || snacks.isEmpty()
                        || dinner.isEmpty() || notes.isEmpty() || date.isEmpty()){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateNewDietPlanActivity.this);
                    alertDialog.setTitle("OOps");
                    alertDialog.setMessage("You have to fill all filed carefully!");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);
                    alertDialog.show();

                    if (breakFast.isEmpty()) {
                        etBreakFast.setHintTextColor(Color.parseColor("red"));
                    }
                    if (lunch.isEmpty()) {
                        etLunch.setHintTextColor(Color.parseColor("red"));
                    }
                    if (snacks.isEmpty()) {
                        etAfternoonSnacks.setHintTextColor(Color.parseColor("red"));
                    }
                    if (dinner.isEmpty()) {
                        etDinner.setHintTextColor(Color.parseColor("red"));
                    }
                    if (notes.isEmpty()) {
                        etNotes.setHintTextColor(Color.parseColor("red"));
                    }
                    if (date.isEmpty()) {
                        etDate.setHintTextColor(Color.parseColor("red"));
                    }
                }
                else{

                    Diet_Plan diet=new Diet_Plan(profileId,date,dayOfWeek,breakFast,lunch,snacks,dinner,notes);

                    if(fromWhere.contains("DietPlanActivity")){
                        long insert=dbAdapterDiet.addNewDietPlan(diet);
                        if(insert>=0){
                            Toast.makeText(CreateNewDietPlanActivity.this, "Data Insert Successfully!", Toast.LENGTH_SHORT).show();
                            Intent gotoDietPlanActivity = new Intent(CreateNewDietPlanActivity.this, DietPlanActivity.class);
                            gotoDietPlanActivity.putExtra("id", profileId);
                            gotoDietPlanActivity.putExtra("from", "add");
                            gotoDietPlanActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            gotoDietPlanActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(gotoDietPlanActivity);
                            dbAdapterDiet.closeDB();
                            finish();
                        }

                    }
                    else if(fromWhere.contains("DietDetailsActivity")){
                        long update=dbAdapterDiet.updateDietPlan(diet,dietId);
                        if(update>=0){
                            Toast.makeText(CreateNewDietPlanActivity.this, "Data Insert Successfully!", Toast.LENGTH_SHORT).show();
                            Intent gotoDietDetailsActivity = new Intent(CreateNewDietPlanActivity.this, DietDetailsActivity.class);
                            gotoDietDetailsActivity.putExtra("profileId", profileId);
                            gotoDietDetailsActivity.putExtra("id", dietId);
                            gotoDietDetailsActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            gotoDietDetailsActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(gotoDietDetailsActivity);
                            dbAdapterDiet.closeDB();
                            finish();
                        }

                    }
                }
            }
        });
    }

    private void initializations() {
        etBreakFast= (EditText) findViewById(R.id.etBreakfastInAddDiet);
        etLunch= (EditText) findViewById(R.id.etLunchInAddDiet);
        etAfternoonSnacks= (EditText) findViewById(R.id.etAfternoonSnacksInAddDiet);
        etDinner= (EditText) findViewById(R.id.etDinnerInAddDiet);
        etNotes= (EditText) findViewById(R.id.etNotesInAddDiet);
        etDate= (EditText) findViewById(R.id.etDateInAddDiet);
        btnSave= (TextView) findViewById(R.id.btnSaveInAddDiet);

        dietPlanArrayList=new ArrayList<>();
        dbAdapterDiet=new DBAdapter_Diet(this);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
