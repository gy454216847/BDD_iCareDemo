package thebigbang.com.icare.Database.DBAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thebigbang.com.icare.Database.DBHelper;
import thebigbang.com.icare.Model.Diet_Plan;
import thebigbang.com.icare.Model.Prescriptions;

/**
 * Created by Jakir on 6/29/2015.
 */
public class DBAdapter_Prescriptions {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBAdapter_Prescriptions(Context context){
        dbHelper=new DBHelper(context);
    }

    public void openDB(){
        database=dbHelper.getWritableDatabase();
    }
    public void closeDB(){
        database.close();
    }
    public long addPrescriptions(Prescriptions prescriptions) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.DISEASES_ID_FIELD, prescriptions.getDiseasesId());
        values.put(dbHelper.PRESCRIPTION_TITLE_FIELD, prescriptions.getTitle());
        values.put(dbHelper.PRESCRIPTION_IMAGE_FIELD,prescriptions.getImage());

        long inserted=database.insert(dbHelper.TABLE_PRESCRIPTION,null,values);
        return inserted;

    }

    public ArrayList<Prescriptions> getPrescriptionsImageAndTitle(int diseasesId){
        ArrayList<Prescriptions> prescriptionses=new ArrayList<>();
        database=dbHelper.getReadableDatabase();

        String rawSql="select "+dbHelper.ID_FIELD+","+dbHelper.PRESCRIPTION_IMAGE_FIELD+","+dbHelper.PRESCRIPTION_TITLE_FIELD
                +" from "+dbHelper.TABLE_PRESCRIPTION +" where "+dbHelper.TABLE_PRESCRIPTION+"."+dbHelper.DISEASES_ID_FIELD+" = "+diseasesId;

        Cursor cursor=database.rawQuery(rawSql,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                String title=cursor.getString(cursor.getColumnIndex(dbHelper.PRESCRIPTION_TITLE_FIELD));
                byte[] image=cursor.getBlob(cursor.getColumnIndex(dbHelper.PRESCRIPTION_IMAGE_FIELD));
                int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.ID_FIELD)));

                Prescriptions prescriptions=new Prescriptions();
                prescriptions.setImage(image);
                prescriptions.setTitle(title);
                prescriptions.setId(id);
                prescriptionses.add(prescriptions);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return prescriptionses;
    }
    // Deleting single contact
    public void deletePrescriptions(Prescriptions prescriptions) {
        database = dbHelper.getWritableDatabase();
        database.delete(dbHelper.TABLE_PRESCRIPTION, dbHelper.ID_FIELD + " = ?",
                new String[] { String.valueOf(prescriptions.getId()) });
        database.close();
    }
}
