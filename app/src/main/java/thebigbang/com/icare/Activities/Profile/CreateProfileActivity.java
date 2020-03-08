package thebigbang.com.icare.Activities.Profile;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import thebigbang.com.icare.Activities.HomeScreenActivity;
import thebigbang.com.icare.Adapters.ToastAdapter;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_User_Profile;
import thebigbang.com.icare.Model.User_Profile;
import thebigbang.com.icare.R;

public class CreateProfileActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    private TextView btnSave;
    private EditText etName;
    private EditText etDateOfBirth;
    private EditText etHeight;
    private EditText etWeight;
    private EditText etPhone;
    private EditText etEmail;

    private RadioGroup rgGender;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private Spinner spBloodGroup;
    private Spinner spBloodPressure;

    private User_Profile profile;
    private DBAdapter_User_Profile dbAdapterUserProfile;
    private ArrayList<User_Profile> profileArrayList;
    private ToastAdapter toastAdapter;
    ArrayAdapter<String> adapterBGroup;
    ArrayAdapter<String> adapterBPressure;

    private String fromWhere;
    private static String gender;
    private static int profileId;
    private static String date;
    private static String strBloodGroup;
    private static String strBloodPressure;
    private static int age;
    private static double bmi;
    private static String strBMI;

    private String[] bloodGroup= {"A+","A-","AB+","AB-","B+", "B-", "O+","O-"};
    private String[] bloodPressure= {"Normal","High","Low"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_create_profile);

        Bundle extras=getIntent().getExtras();
        fromWhere=extras.getString("from");
        initialization();
        if (fromWhere.contains("profileActivity")){
            profileId=extras.getInt("id");
            profileArrayList= dbAdapterUserProfile.getProfileDetailsById(profileId);
            setEditTextValueFromDatabase();
            btnSave.setText("Update");
        }
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rbMale){
                    gender=rbMale.getText().toString();
                }
                else if (checkedId==R.id.rbFemale){
                    gender=rbFemale.getText().toString();
                }
            }
        });

        clickListener();
    }

    private void setEditTextValueFromDatabase() {

        for (User_Profile profile:profileArrayList){
            etName.setText(profile.getName());
            age=profile.getAge();
            strBloodGroup=profile.getBloodGroup();
            strBloodPressure=profile.getBloodPressure();
            etDateOfBirth.setText(profile.getDateOfBirth());
            etHeight.setText(String.valueOf(profile.getHeight()));
            etWeight.setText(String.valueOf(profile.getWeight()));
            etEmail.setText(profile.getEmail());
            etPhone.setText(profile.getPhone());
            gender=profile.getGender();
            if (gender.contains("Male")){
                rbMale.setChecked(true);
            }
            else if (gender.contains("Female")){
                rbFemale.setChecked(true);
            }
            spBloodGroup.setSelection(adapterBGroup.getPosition(strBloodGroup));
            spBloodPressure.setSelection(adapterBPressure.getPosition(strBloodPressure));

        }
    }

    private void clickListener() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                String name=etName.getText().toString().trim();
                String dateOfBirth= etDateOfBirth.getText().toString();
                String weightStr=etWeight.getText().toString().trim();
                String heightStr=etHeight.getText().toString().trim();
                String email=etEmail.getText().toString().trim();
                String phoneStr=etPhone.getText().toString().trim();

                double weight=0;
                double height=0;

                if(name.isEmpty()|| weightStr.isEmpty() ||
                        heightStr.isEmpty() || phoneStr.isEmpty() || dateOfBirth.isEmpty() ||
                        email.isEmpty()){
                    btnSave.getBackground().setAlpha(100);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateProfileActivity.this);
                    alertDialog.setTitle("OOps");
                    alertDialog.setMessage("You have to fill all filed carefully!");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_info);
                    alertDialog.show();
                    if(name.isEmpty()){
                        etName.setHintTextColor(Color.parseColor("red"));
                    }

                    if(heightStr.isEmpty()){
                        etHeight.setHintTextColor(Color.parseColor("red"));
                    }
                    if(weightStr.isEmpty()){
                        etWeight.setHintTextColor(Color.parseColor("red"));
                    }
                    if(strBloodGroup.isEmpty()){

                    }
                    if(dateOfBirth.isEmpty()){
                        etDateOfBirth.setHintTextColor(Color.parseColor("red"));
                    }
                    if(email.isEmpty()){
                        etEmail.setHintTextColor(Color.parseColor("red"));
                    }
                    if(phoneStr.isEmpty()){
                        etPhone.setHintTextColor(Color.parseColor("red"));
                    }

                }
                else{
                    btnSave.getBackground().setAlpha(255);
                    weight=Double.parseDouble(weightStr);
                    height=Double.parseDouble(heightStr);
                    calculateBMI(height,weight);

                    dbAdapterUserProfile.openDB();
                    User_Profile profile=new User_Profile(name,age,dateOfBirth,gender,strBloodGroup,strBloodPressure,strBMI,height,weight,phoneStr, email);
                    Toast.makeText(getApplicationContext(),"BGROUP: "+strBloodGroup+"BP: "+strBloodPressure+
                    "BMI: "+strBMI,Toast.LENGTH_LONG).show();
                    if(fromWhere.contains("homeScreenActivity")){
                        long insert= dbAdapterUserProfile.addNewProfile(profile);
                        if(insert>=0){
                            toastAdapter.toast("Data insert successfully!!");
                            Intent gotoHomeScreen=new Intent(CreateProfileActivity.this,HomeScreenActivity.class);
                            gotoHomeScreen.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            gotoHomeScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotoHomeScreen);
                            dbAdapterUserProfile.closeDB();
                            finish();
                        }
                        else {
                            toastAdapter.toast("Data insertion failed!!");
                            dbAdapterUserProfile.closeDB();
                        }

                    }
                    else if (fromWhere.contains("profileActivity")){
                        long updateProfile= dbAdapterUserProfile.updateProfile(profile,profileId);
                        if(updateProfile>=0){
                            toastAdapter.toast("Data Updated Successfully!!");
                            Intent gotoProfileActivity=new Intent(CreateProfileActivity.this,ProfileActivity.class);
                            gotoProfileActivity.putExtra("id",profileId);
                            gotoProfileActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            gotoProfileActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(gotoProfileActivity);
                            finish();
                        }
                    }
                }

            }
        });

        etDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(CreateProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                etDateOfBirth.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                date=dayOfMonth+"/"+(monthOfYear + 1)+"/"+year;

                                Calendar today = Calendar.getInstance();
                                age = today.get(Calendar.YEAR) - year;
                                Toast.makeText(CreateProfileActivity.this, "AGE: "+age, Toast.LENGTH_SHORT).show();

                            }

                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        spBloodGroup.setOnItemSelectedListener(CreateProfileActivity.this);
        spBloodPressure.setOnItemSelectedListener(CreateProfileActivity.this);
    }

    private void calculateBMI(double height, double weight) {
        Double heightMeters=height*0.3048;
        bmi = (weight / (heightMeters * heightMeters));
        if (bmi >= 30) {
            strBMI = "OBESE";
        } else if (bmi >= 25) {
            strBMI = "OVERWEIGHT";
        } else if (bmi >= 18.5) {
            strBMI = "IDEAL";
        } else {
            strBMI = "UNDERWEIGHT";
        }
    }

    private void initialization() {
        btnSave= (TextView) findViewById(R.id.btnSave);
        etName= (EditText) findViewById(R.id.etName);
        etHeight= (EditText) findViewById(R.id.etHeight);
        etWeight= (EditText) findViewById(R.id.etWeight);
        etDateOfBirth = (EditText) findViewById(R.id.etDateofBirth);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etPhone= (EditText) findViewById(R.id.etPhone);

        rgGender= (RadioGroup) findViewById(R.id.rgGender);
        rbMale= (RadioButton) findViewById(R.id.rbMale);
        rbFemale= (RadioButton) findViewById(R.id.rbFemale);
        spBloodGroup= (Spinner) findViewById(R.id.spBloodGroup);
        spBloodPressure= (Spinner) findViewById(R.id.spBP);
        adapterBGroup = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bloodGroup);
        adapterBPressure = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, bloodPressure);
        spBloodGroup.setAdapter(adapterBGroup);
        spBloodPressure.setAdapter(adapterBPressure);

        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        etDateOfBirth.setText(date);

        profile=new User_Profile();
        dbAdapterUserProfile =new DBAdapter_User_Profile(this);
        profileArrayList=new ArrayList<User_Profile>();
        toastAdapter=new ToastAdapter(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent==spBloodGroup){
            strBloodGroup=parent.getItemAtPosition(position).toString();
        }
        if (parent==spBloodPressure){
            strBloodPressure=parent.getItemAtPosition(position).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
