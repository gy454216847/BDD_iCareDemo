package thebigbang.com.icare.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import thebigbang.com.icare.Activities.AroundMe.AroundMeActivity;
import thebigbang.com.icare.Activities.Profile.CreateProfileActivity;
import thebigbang.com.icare.Adapters.ProfileListAdapter;
import thebigbang.com.icare.Adapters.ToastAdapter;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_User_Profile;
import thebigbang.com.icare.Model.User_Profile;
import thebigbang.com.icare.R;

public class HomeScreenActivity extends AppCompatActivity {

    private ListView lvProfile;
    private TextView tvAddNewProfile;
    private EditText etSearchProfile;
    private RelativeLayout rLayoutFooter;
    private ArrayList<User_Profile> profileArrayList;
    private ProfileListAdapter adapter;
    private DBAdapter_User_Profile dbAdapterUserProfile;
    private ToastAdapter toast;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_home_screen);

        initialization();

    }

    private void initialization() {

        rLayoutFooter = (RelativeLayout) findViewById(R.id.footer);
        lvProfile = (ListView) findViewById(R.id.lvProfile);
        tvAddNewProfile = (TextView) findViewById(R.id.tvAddNew);
        etSearchProfile = (EditText) findViewById(R.id.etSearch);
        dbAdapterUserProfile = new DBAdapter_User_Profile(this);
        toast = new ToastAdapter(this);
        profileArrayList = new ArrayList<User_Profile>();
        profileArrayList = dbAdapterUserProfile.getProfileNameAndId();
        adapter = new ProfileListAdapter(this, profileArrayList);
        lvProfile.setAdapter(adapter);

        eventClickListener();

    }

    private void eventClickListener() {
        tvAddNewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoCreateProfileActivity = new Intent(HomeScreenActivity.this, CreateProfileActivity.class);
                gotoCreateProfileActivity.putExtra("from", "homeScreenActivity");
                gotoCreateProfileActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                gotoCreateProfileActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoCreateProfileActivity);
            }
        });
        etSearchProfile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userName = s.toString().trim();
                profileArrayList = dbAdapterUserProfile.getUserInfoByName(userName);
                adapter = new ProfileListAdapter(HomeScreenActivity.this, profileArrayList);
                lvProfile.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lvProfile.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.aroundMe:
                Intent gotoAroundMeActivity = new Intent(HomeScreenActivity.this, AroundMeActivity.class);
                gotoAroundMeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoAroundMeActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gotoAroundMeActivity);
                break;
            case R.id.settings:
                Intent gotoSettingsActivity = new Intent(HomeScreenActivity.this, SettingActivity.class);
                gotoSettingsActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoSettingsActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gotoSettingsActivity);
                break;
            case R.id.aboutIcare:
                Intent gotoAboutIcareActivity = new Intent(HomeScreenActivity.this, AboutiCAreActivity.class);
                gotoAboutIcareActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoAboutIcareActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gotoAboutIcareActivity);
                break;
            case R.id.exit:
                finish();
                break;
        }
        if (id == R.id.action_search) {
            etSearchProfile.setVisibility(View.VISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(HomeScreenActivity.this);
        alert.setMessage("Are you want to exit ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                finish();

            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

            }
        });
        alert.show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initialization();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
