package thebigbang.com.icare.Database.DBAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thebigbang.com.icare.Database.DBHelper;
import thebigbang.com.icare.Model.Vaccination;

/**
 * Created by Jakir on 6/20/2015.
 */
public class DBAdapter_Vaccination {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBAdapter_Vaccination(Context context){
        dbHelper=new DBHelper(context);
    }

    public void openDB(){
        database=dbHelper.getWritableDatabase();
    }
    public void closeDB(){
        database.close();
    }

    public long addNewVaccine(Vaccination vaccine){
        ContentValues values=new ContentValues();
        values.put(dbHelper.NAME_FIELD,vaccine.getName());
        values.put(dbHelper.TOTAL_DOSE_FIELD,vaccine.getTotalDose());
        values.put(dbHelper.COMPLETE_DOSE_FIELD,vaccine.getCompleteDose());
        values.put(dbHelper.REMAINING_DOSE_FIELD,vaccine.getRemainingDose());
        values.put(dbHelper.NEXT_DOSE_DATE_FIELD,vaccine.getNextDate());
        values.put(dbHelper.CARE_CENTER_FIELD,vaccine.getCareCenter());
        values.put(dbHelper.USER_ID_FIELD,vaccine.getProfileId());
        values.put(dbHelper.EVENT_TIME_FIELD,vaccine.getEventTime());

        long inserted=database.insert(dbHelper.TABLE_VACCINATION,null,values);

        return inserted;
    }
    public long updateVaccineInfo(Vaccination vaccination, int vaccineId){
        ContentValues values=new ContentValues();
        values.put(dbHelper.NAME_FIELD,vaccination.getName());
        values.put(dbHelper.TOTAL_DOSE_FIELD,vaccination.getTotalDose());
        values.put(dbHelper.COMPLETE_DOSE_FIELD,vaccination.getCompleteDose());
        values.put(dbHelper.REMAINING_DOSE_FIELD,vaccination.getRemainingDose());
        values.put(dbHelper.NEXT_DOSE_DATE_FIELD,vaccination.getNextDate());
        values.put(dbHelper.CARE_CENTER_FIELD,vaccination.getCareCenter());
        values.put(dbHelper.USER_ID_FIELD,vaccination.getProfileId());
        values.put(dbHelper.EVENT_TIME_FIELD,vaccination.getEventTime());
        long insert=database.update(dbHelper.TABLE_VACCINATION,values,DBHelper.ID_FIELD+" = "+vaccineId,null);

        return insert;
    }

    public long updateVaccineDoseInfo(Vaccination vaccination, int vaccineId){
        ContentValues values=new ContentValues();
        values.put(dbHelper.COMPLETE_DOSE_FIELD,vaccination.getCompleteDose());
        values.put(dbHelper.REMAINING_DOSE_FIELD,vaccination.getRemainingDose());
        values.put(dbHelper.NEXT_DOSE_DATE_FIELD,vaccination.getNextDate());
        values.put(dbHelper.CARE_CENTER_FIELD,vaccination.getCareCenter());
        values.put(dbHelper.EVENT_TIME_FIELD,vaccination.getEventTime());
        long insert=database.update(dbHelper.TABLE_VACCINATION,values,DBHelper.ID_FIELD+" = "+vaccineId,null);

        return insert;
    }

    public ArrayList<Vaccination> getVaccineNameAndId(int profileId){
        ArrayList<Vaccination> vaccinations=new ArrayList<Vaccination>();
        database=dbHelper.getReadableDatabase();

        String rawSql="select "+dbHelper.ID_FIELD+","+dbHelper.NAME_FIELD+","+dbHelper.NEXT_DOSE_DATE_FIELD
                +" from "+dbHelper.TABLE_VACCINATION +" where "+dbHelper.TABLE_VACCINATION+"."+dbHelper.USER_ID_FIELD+" = "+profileId
                +" ORDER BY "+dbHelper.NEXT_DOSE_DATE_FIELD+" ASC";

        Cursor cursor=database.rawQuery(rawSql,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                String name=cursor.getString(cursor.getColumnIndex(dbHelper.NAME_FIELD));
                String nextDate=cursor.getString(cursor.getColumnIndex(dbHelper.NEXT_DOSE_DATE_FIELD));
                int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.ID_FIELD)));

                Vaccination vaccination=new Vaccination(id,name,nextDate);
                vaccinations.add(vaccination);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return vaccinations;
    }
    public ArrayList<Vaccination> getVaccineInfoAndEventTime(){
        ArrayList<Vaccination> vaccinations=new ArrayList<Vaccination>();
        database=dbHelper.getReadableDatabase();

        String rawSql="select * from "+dbHelper.TABLE_VACCINATION ;

        Cursor cursor=database.rawQuery(rawSql,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                String name=cursor.getString(cursor.getColumnIndex(dbHelper.NAME_FIELD));
                String nextDate=cursor.getString(cursor.getColumnIndex(dbHelper.NEXT_DOSE_DATE_FIELD));
                long eventTime = Long.parseLong(cursor.getString(cursor.getColumnIndex(dbHelper.EVENT_TIME_FIELD)));

                Vaccination vaccination=new Vaccination();
                vaccination.setName(name);
                vaccination.setNextDate(nextDate);
                vaccination.setEventTime(eventTime);
                vaccinations.add(vaccination);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return vaccinations;
    }

    public ArrayList<Vaccination> getVaccineDetailsById(int vaccineId){
        ArrayList<Vaccination> vaccinations=new ArrayList<Vaccination>();
        database=dbHelper.getReadableDatabase();
        String rawSql="select * from "+dbHelper.TABLE_VACCINATION+" where "+dbHelper.TABLE_VACCINATION+"."+dbHelper.ID_FIELD+" = "
                +vaccineId;
        Cursor cursor=database.rawQuery(rawSql,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                String vaccineName=cursor.getString(cursor.getColumnIndex(dbHelper.NAME_FIELD));
                int totalDose=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.TOTAL_DOSE_FIELD)));
                int completeDose=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.COMPLETE_DOSE_FIELD)));
                int remainingDose=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.REMAINING_DOSE_FIELD)));
                String nextDate=cursor.getString(cursor.getColumnIndex(dbHelper.NEXT_DOSE_DATE_FIELD));
                String careCenter=cursor.getString(cursor.getColumnIndex(dbHelper.CARE_CENTER_FIELD));
                int profileId=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.USER_ID_FIELD)));
                long eventTime=Long.parseLong(cursor.getString(cursor.getColumnIndex(dbHelper.EVENT_TIME_FIELD)));

                Vaccination vaccine=new Vaccination(profileId,vaccineName,totalDose,completeDose,remainingDose,nextDate,careCenter,eventTime);
                vaccinations.add(vaccine);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return vaccinations;
    }

    public void delete(long id) {
        database=dbHelper.getReadableDatabase();
        database.delete(DBHelper.TABLE_VACCINATION, DBHelper.ID_FIELD + "=?",
                new String[] { String.valueOf(id) });

        closeDB();
    }
    public void allDelete() {
        database=dbHelper.getReadableDatabase();
        database.delete(dbHelper.TABLE_VACCINATION, null, null);
        closeDB();
    }
}
