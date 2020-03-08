package thebigbang.com.icare.Activities.Profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import thebigbang.com.icare.Adapters.ToastAdapter;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_User_Profile;
import thebigbang.com.icare.Model.User_Profile;
import thebigbang.com.icare.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvGender;
    private TextView tvAge;
    private TextView tvHeight;
    private TextView tvWeight;
    private TextView tvBirthDate;
    private TextView tvBloodGroup;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvEditPersonalInfo;

    private ArrayList<User_Profile> profileArrayList;
    private DBAdapter_User_Profile dbAdapterUserProfile;
    private ToastAdapter toast;
    private static int profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_profile);

        initialization();
        retrieveDataFromDatabase();
        clickListener();

    }

    private void clickListener() {
        tvEditPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoCreateProfileActivity=new Intent(ProfileActivity.this,CreateProfileActivity.class);
                gotoCreateProfileActivity.putExtra("from","profileActivity");
                gotoCreateProfileActivity.putExtra("id",profileId);
                startActivity(gotoCreateProfileActivity);
            }
        });
    }

    private void retrieveDataFromDatabase() {
        Bundle extras=getIntent().getExtras();
        profileId=extras.getInt("id");
        profileArrayList= dbAdapterUserProfile.getProfileDetailsById(profileId);
        setValueInTextField();
    }

    private void setValueInTextField() {
        for(User_Profile profile:profileArrayList ){
            tvName.setText(profile.getName());
            tvGender.setText(profile.getGender());
            tvAge.setText(String.valueOf(profile.getAge()));
            tvHeight.setText(String.valueOf(profile.getHeight())+" Feet");
            tvWeight.setText(String.valueOf(profile.getWeight())+" Kgs");
            tvBirthDate.setText(profile.getDateOfBirth());
            tvBloodGroup.setText(profile.getBloodGroup());
            tvEmail.setText(profile.getEmail());
            tvPhone.setText(profile.getPhone());
        }
    }

    private void initialization() {
        tvName= (TextView) findViewById(R.id.tvNameInProfile);
        tvGender= (TextView) findViewById(R.id.tvGenderInProfile);
        tvAge= (TextView) findViewById(R.id.tvAgeInProfile);
        tvEmail= (TextView) findViewById(R.id.tvEmailInProfile);
        tvPhone= (TextView) findViewById(R.id.tvPhoneInProfile);
        tvBirthDate= (TextView) findViewById(R.id.tvDateOfBirthInProfile);
        tvBloodGroup= (TextView) findViewById(R.id.tvBloodGroupInProfile);
        tvHeight= (TextView) findViewById(R.id.tvHeightInProfile);
        tvWeight= (TextView) findViewById(R.id.tvWeightInpProfile);
        tvEditPersonalInfo= (TextView) findViewById(R.id.tvEditPersonalInfoInProfile);

        profileArrayList=new ArrayList<User_Profile>();
        dbAdapterUserProfile =new DBAdapter_User_Profile(this);
        toast=new ToastAdapter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
