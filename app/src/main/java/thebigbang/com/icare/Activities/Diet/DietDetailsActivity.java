package thebigbang.com.icare.Activities.Diet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Diet;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Vaccination;
import thebigbang.com.icare.Model.Diet_Plan;
import thebigbang.com.icare.Model.Vaccination;
import thebigbang.com.icare.R;

public class DietDetailsActivity extends AppCompatActivity {

    private TextView tvBreakFast;
    private TextView tvLunch;
    private TextView tvSnacks;
    private TextView tvDinner;
    private TextView tvNotes;
    private TextView tvDayAndDate;

    private int dietId;
    private int profileId;

    private ArrayList<Diet_Plan> dietPlanArrayList;
    private DBAdapter_Diet dbAdapterDiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_diet_details);

        Bundle extras=getIntent().getExtras();
        dietId =extras.getInt("id");
        profileId=extras.getInt("profileId");

        initializations();
        retrieveDataFromDatabase();
    }

    private void retrieveDataFromDatabase() {
        dietPlanArrayList=dbAdapterDiet.getDietPlanDetails(dietId);
        for(Diet_Plan diet:dietPlanArrayList){
            tvDayAndDate.append(diet.getDay()+"-");
            tvDayAndDate.append(diet.getDate());
            tvBreakFast.setText(diet.getBreakFast());
            tvLunch.setText(diet.getLunch());
            tvSnacks.setText(diet.getAfternoonSnacks());
            tvDinner.setText(diet.getDinner());
            tvNotes.setText(diet.getNotes());
        }
    }

    private void initializations() {
        tvBreakFast= (TextView) findViewById(R.id.tvBreakFastInDietDetails);
        tvLunch= (TextView) findViewById(R.id.tvLunchInDietDetails);
        tvSnacks= (TextView) findViewById(R.id.tvSnacksInDietDetails);
        tvDinner= (TextView) findViewById(R.id.tvDinnerInDeitDetails);
        tvNotes= (TextView) findViewById(R.id.tvNotesInDeitDetails);
        tvDayAndDate= (TextView) findViewById(R.id.tvDayAndDateInDietDetails);

        dietPlanArrayList=new ArrayList<Diet_Plan>();
        dbAdapterDiet=new DBAdapter_Diet(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diet_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.editDietDetails) {
            Intent gotoCreateNewDietPlanActivity = new Intent(DietDetailsActivity.this, CreateNewDietPlanActivity.class);
            gotoCreateNewDietPlanActivity.putExtra("id", profileId);
            Toast.makeText(DietDetailsActivity.this, "Profile Id: "+profileId, Toast.LENGTH_SHORT).show();
            gotoCreateNewDietPlanActivity.putExtra("dietId", dietId);
            gotoCreateNewDietPlanActivity.putExtra("from", "DietDetailsActivity");
            gotoCreateNewDietPlanActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            gotoCreateNewDietPlanActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(gotoCreateNewDietPlanActivity);
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
