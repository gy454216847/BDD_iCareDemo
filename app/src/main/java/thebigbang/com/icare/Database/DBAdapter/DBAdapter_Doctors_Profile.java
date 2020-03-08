package thebigbang.com.icare.Database.DBAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thebigbang.com.icare.Database.DBHelper;
import thebigbang.com.icare.Model.Doctors;

/**
 * Created by Jakir on 6/28/2015.
 */
public class DBAdapter_Doctors_Profile {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBAdapter_Doctors_Profile(Context context){
        dbHelper=new DBHelper(context);
    }

    public void openDB(){
        database=dbHelper.getWritableDatabase();
    }
    public void closeDB(){
        database.close();
    }

    public long addNewDoctorsProfile(Doctors doctor){
        ContentValues values=new ContentValues();
        values.put(dbHelper.NAME_FIELD,doctor.getName());
        values.put(dbHelper.SPECIALITIES_FIELD,doctor.getSpecialities());
        values.put(dbHelper.HOSPITAL_FIELD,doctor.getHospital());
        values.put(dbHelper.EMAIL_FIELD,doctor.getEmail());
        values.put(dbHelper.PHONE_FIELD,doctor.getPhone());
        values.put(dbHelper.CHAMBER_FIELD,doctor.getChamberAddress());
        values.put(dbHelper.USER_ID_FIELD,doctor.getProfileId());
        values.put(dbHelper.FEE_FIELD,doctor.getFee());

        long inserted=database.insert(dbHelper.TABLE_DOCTORS_PROFILE,null,values);

        return inserted;
    }

    public ArrayList<Doctors> getDoctorsNameSpecialityFee(int profileId){
        ArrayList<Doctors> profiles=new ArrayList<Doctors>();
        database=dbHelper.getReadableDatabase();

        String rawSql="select "+dbHelper.ID_FIELD+","+dbHelper.NAME_FIELD+","+dbHelper.SPECIALITIES_FIELD+","+dbHelper.FEE_FIELD
                +" from "+dbHelper.TABLE_DOCTORS_PROFILE +" where "+dbHelper.TABLE_DOCTORS_PROFILE+"."+dbHelper.USER_ID_FIELD+" = "+profileId
                +" ORDER BY "+dbHelper.NAME_FIELD+" ASC";

        Cursor cursor=database.rawQuery(rawSql,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                String name=cursor.getString(cursor.getColumnIndex(dbHelper.NAME_FIELD));
                String specialities=cursor.getString(cursor.getColumnIndex(dbHelper.SPECIALITIES_FIELD));
                String fee=cursor.getString(cursor.getColumnIndex(dbHelper.FEE_FIELD));
                int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.ID_FIELD)));

                Doctors profile=new Doctors(id,name,specialities,fee);
                profiles.add(profile);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return profiles;
    }

    public ArrayList<Doctors> getDoctorsDetailsById(int doctorId){
        ArrayList<Doctors> profiles=new ArrayList<Doctors>();
        database=dbHelper.getReadableDatabase();
        String rawSql="select * from "+dbHelper.TABLE_DOCTORS_PROFILE+" where "+dbHelper.TABLE_DOCTORS_PROFILE+"."+dbHelper.ID_FIELD+" = "
                +doctorId;;
        Cursor cursor=database.rawQuery(rawSql,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                String name=cursor.getString(cursor.getColumnIndex(dbHelper.NAME_FIELD));
                int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.ID_FIELD)));
                String specialities=cursor.getString(cursor.getColumnIndex(dbHelper.SPECIALITIES_FIELD));
                String email=cursor.getString(cursor.getColumnIndex(dbHelper.EMAIL_FIELD));
                String hospital=cursor.getString(cursor.getColumnIndex(dbHelper.HOSPITAL_FIELD));
                String phone=cursor.getString(cursor.getColumnIndex(dbHelper.PHONE_FIELD));
                String chamber=cursor.getString(cursor.getColumnIndex(dbHelper.CHAMBER_FIELD));
                String fee=cursor.getString(cursor.getColumnIndex(dbHelper.FEE_FIELD));
                int profileId=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.USER_ID_FIELD)));

                Doctors profile=new Doctors(id,profileId,name,specialities,phone,email,hospital,chamber,fee);
                profiles.add(profile);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return profiles;
    }

    public long updateDoctorsInfo(Doctors doctor, int doctorId){
        ContentValues values=new ContentValues();
        values.put(dbHelper.NAME_FIELD,doctor.getName());
        values.put(dbHelper.SPECIALITIES_FIELD,doctor.getSpecialities());
        values.put(dbHelper.HOSPITAL_FIELD,doctor.getHospital());
        values.put(dbHelper.EMAIL_FIELD,doctor.getEmail());
        values.put(dbHelper.PHONE_FIELD,doctor.getPhone());
        values.put(dbHelper.CHAMBER_FIELD,doctor.getChamberAddress());
        values.put(dbHelper.USER_ID_FIELD,doctor.getProfileId());
        values.put(dbHelper.FEE_FIELD,doctor.getFee());
        long insert=database.update(dbHelper.TABLE_DOCTORS_PROFILE,values,DBHelper.ID_FIELD+" = "+doctorId,null);

        return insert;
    }


    public void delete(long id) {
        database=dbHelper.getReadableDatabase();
        database.delete(DBHelper.TABLE_DOCTORS_PROFILE, DBHelper.ID_FIELD + "=?",
                new String[] { String.valueOf(id) });

        closeDB();
    }
    public void allDelete() {
        database=dbHelper.getReadableDatabase();
        database.delete(dbHelper.TABLE_DOCTORS_PROFILE, null, null);
        closeDB();
    }
}
