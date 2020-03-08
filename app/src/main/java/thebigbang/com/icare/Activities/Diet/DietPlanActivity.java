package thebigbang.com.icare.Activities.Diet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import thebigbang.com.icare.Adapters.DietAdapter;
import thebigbang.com.icare.Adapters.VaccineAdapter;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Diet;
import thebigbang.com.icare.Model.Diet_Plan;
import thebigbang.com.icare.R;

public class DietPlanActivity extends AppCompatActivity {
    private ListView lvDietDay;
    private TextView tvAddNewDietPlan;
    private ArrayList<Diet_Plan> dietPlanArrayList;
    private DBAdapter_Diet dbAdapterDiet;
    private DietAdapter adapterDiet;

    private int profileId;
    private String fromWhere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_diet_plan);

        initializations();
        retrieveDataFromDatabase();
        eventClickListener();
    }

    private void retrieveDataFromDatabase() {
        Bundle extras=getIntent().getExtras();
        profileId=extras.getInt("id");
        dietPlanArrayList=dbAdapterDiet.getDietPlanDateAndID(profileId);
        adapterDiet=new DietAdapter(this,dietPlanArrayList,profileId);
        lvDietDay.setAdapter(adapterDiet);
    }

    private void eventClickListener() {
        tvAddNewDietPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoCreateNewDietPlanActivity = new Intent(DietPlanActivity.this, CreateNewDietPlanActivity.class);
                gotoCreateNewDietPlanActivity.putExtra("id", profileId);
                gotoCreateNewDietPlanActivity.putExtra("from", "DietPlanActivity");
                gotoCreateNewDietPlanActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoCreateNewDietPlanActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(gotoCreateNewDietPlanActivity);
            }
        });
    }

    private void initializations() {
        lvDietDay= (ListView) findViewById(R.id.lvDiet);
        tvAddNewDietPlan= (TextView) findViewById(R.id.tvAddNewDietPlan);

        dietPlanArrayList=new ArrayList<>();
        dbAdapterDiet=new DBAdapter_Diet(this);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        retrieveDataFromDatabase();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
