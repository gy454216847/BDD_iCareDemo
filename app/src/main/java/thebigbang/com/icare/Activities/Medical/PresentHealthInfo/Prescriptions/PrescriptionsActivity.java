package thebigbang.com.icare.Activities.Medical.PresentHealthInfo.Prescriptions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import thebigbang.com.icare.Adapters.PrescriptionsAdapter;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Prescriptions;
import thebigbang.com.icare.Model.Prescriptions;
import thebigbang.com.icare.R;

public class PrescriptionsActivity extends AppCompatActivity {
    private TextView tvAddNewPrescriptions;
    private ArrayList<Prescriptions> prescriptionsArrayList;
    private PrescriptionsAdapter prescriptionsAdapter;
    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;

    private int diseasesId;
    private static String fromWhere;
    ListView lvPrescriptions;
    byte[] image;
    int imageId;
    Bitmap theImage;
    private DBAdapter_Prescriptions dbAdapterPrescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_prescriptions);

        Bundle extras=getIntent().getExtras();
        fromWhere=extras.getString("from");
        if(fromWhere.contains("diseasesDetails") || fromWhere.contains("onActivityResult") ||
                fromWhere.contains("DisplayPrescriptionsActivity")){
            diseasesId=extras.getInt("diseasesId");
        }

        initializations();
        retrieveDataFromDatabase();
        eventClickListener();
    }

    private void eventClickListener() {
        lvPrescriptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                image = prescriptionsArrayList.get(position).getImage();
                imageId = prescriptionsArrayList.get(position).getId();

                // convert byte to bitmap
                ByteArrayInputStream imageStream = new ByteArrayInputStream(image);
                theImage = BitmapFactory.decodeStream(imageStream);

                Intent gotoDisplayPrescriptionsActivity = new Intent(PrescriptionsActivity.this,DisplayPrescriptionsActivity.class);
                gotoDisplayPrescriptionsActivity.putExtra("imagename", theImage);
                gotoDisplayPrescriptionsActivity.putExtra("imageid", imageId);
                gotoDisplayPrescriptionsActivity.putExtra("diseasesId", diseasesId);
                gotoDisplayPrescriptionsActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                gotoDisplayPrescriptionsActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gotoDisplayPrescriptionsActivity);
            }
        });
        final String[] option = new String[] { "Take from Camera",
                "Select from Gallery" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Option");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    callCamera();
                }
                if (which == 1) {
                    callGallery();
                }

            }
        });
        final AlertDialog dialog = builder.create();

        tvAddNewPrescriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }
    public void callCamera() {
//        Intent cameraIntent = new Intent(
//                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra("crop", "true");
//        cameraIntent.putExtra("aspectX", 0);
//        cameraIntent.putExtra("aspectY", 0);
//        cameraIntent.putExtra("outputX", 200);
//        cameraIntent.putExtra("outputY", 150);
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    public void callGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_GALLERY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case CAMERA_REQUEST:

                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte imageInByte[] = stream.toByteArray();
                    // Inserting Contacts
                    dbAdapterPrescriptions.addPrescriptions(new Prescriptions("prescriptions", imageInByte));
                    Intent i = new Intent(PrescriptionsActivity.this,PrescriptionsActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                }
                break;
            case PICK_FROM_GALLERY:
                Bundle extras2 = data.getExtras();

                if (extras2 != null) {
                    Bitmap yourImage = extras2.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte imageInByte[] = stream.toByteArray();
                    // Inserting Contacts
                    dbAdapterPrescriptions.openDB();
                    Prescriptions prescriptions=new Prescriptions(diseasesId,"title",imageInByte);
                    dbAdapterPrescriptions.addPrescriptions(prescriptions);
                    Intent i = new Intent(PrescriptionsActivity.this,PrescriptionsActivity.class);
                    i.putExtra("from","onActivityResult");
                    i.putExtra("diseasesId",diseasesId);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    dbAdapterPrescriptions.closeDB();
                    finish();
                }

                break;
        }
    }

    private void retrieveDataFromDatabase() {
        prescriptionsArrayList=dbAdapterPrescriptions.getPrescriptionsImageAndTitle(diseasesId);
        prescriptionsAdapter=new PrescriptionsAdapter(this,R.layout.sample_prescriptions_listview,prescriptionsArrayList);
        lvPrescriptions.setAdapter(prescriptionsAdapter);
    }

    private void initializations() {
        tvAddNewPrescriptions= (TextView) findViewById(R.id.tvAddNewPrescriptions);
        lvPrescriptions= (ListView) findViewById(R.id.lvPrescriptions);
        prescriptionsArrayList=new ArrayList<>();
        dbAdapterPrescriptions=new DBAdapter_Prescriptions(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        retrieveDataFromDatabase();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
