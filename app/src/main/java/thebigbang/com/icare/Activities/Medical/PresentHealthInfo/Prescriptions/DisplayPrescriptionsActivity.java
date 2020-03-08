package thebigbang.com.icare.Activities.Medical.PresentHealthInfo.Prescriptions;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Prescriptions;
import thebigbang.com.icare.Model.Prescriptions;
import thebigbang.com.icare.R;

public class DisplayPrescriptionsActivity extends AppCompatActivity {
    private Button btnDelete;
    private ImageView imageDetail;
    private int imageId;
    private int diseasesId;
    private Bitmap theImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_display_prescriptions);
        Bundle extras=getIntent().getExtras();
        diseasesId=extras.getInt("diseasesId");
        initializations();
        getDataFromDatabase();
        eventClickListener();
    }

    private void eventClickListener() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBAdapter_Prescriptions dbAdapter_prescriptions=new DBAdapter_Prescriptions(DisplayPrescriptionsActivity.this);
                Prescriptions prescriptions=new Prescriptions();
                prescriptions.setId(imageId);
                dbAdapter_prescriptions.deletePrescriptions(prescriptions);
                Intent i = new Intent(DisplayPrescriptionsActivity.this,PrescriptionsActivity.class);
                i.putExtra("diseasesId",diseasesId);
                i.putExtra("from","DisplayPrescriptionsActivity");
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
    }

    private void getDataFromDatabase() {
        Intent intnt = getIntent();
        theImage = (Bitmap) intnt.getParcelableExtra("imagename");
        imageId = intnt.getIntExtra("imageid", 20);
        imageDetail.setImageBitmap(theImage);
    }

    private void initializations() {
        btnDelete= (Button) findViewById(R.id.btnDeletePrescriptions);
        imageDetail= (ImageView) findViewById(R.id.imvPrescriptionsInDetails);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
