package thebigbang.com.icare.Database.DBAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import thebigbang.com.icare.Database.DBHelper;
import thebigbang.com.icare.Model.Vaccine_Dose;

/**
 * Created by Jakir on 6/21/2015.
 */
public class DBAdapter_Vaccine_Dose {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBAdapter_Vaccine_Dose(Context context){
        dbHelper=new DBHelper(context);
    }

    public void openDB(){
        database=dbHelper.getWritableDatabase();
    }
    public void closeDB(){
        database.close();
    }


    public long addDoseDate(Vaccine_Dose dose){
        ContentValues values=new ContentValues();
        values.put(dbHelper.VACCINE_ID_FIELD_DOSE,dose.getVaccineId());
        values.put(dbHelper.DATE_FIELD,dose.getDate());
        values.put(dbHelper.CARE_CENTER_FIELD,dose.getCareCenter());

        long inserted=database.insert(dbHelper.TABLE_VACCINATION_DOSE,null,values);

        return inserted;
    }
}
