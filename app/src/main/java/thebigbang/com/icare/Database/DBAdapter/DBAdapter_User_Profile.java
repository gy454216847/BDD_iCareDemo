package thebigbang.com.icare.Database.DBAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thebigbang.com.icare.Database.DBHelper;
import thebigbang.com.icare.Model.User_Profile;

/**
 * Created by Jakir on 6/9/2015.
 */
public class DBAdapter_User_Profile {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBAdapter_User_Profile(Context context){
        dbHelper=new DBHelper(context);
    }

    public void openDB(){
        database=dbHelper.getWritableDatabase();
    }
    public void closeDB(){
        database.close();
    }

    public long addNewProfile(User_Profile profile){
        ContentValues values=new ContentValues();
        values.put(dbHelper.NAME_FIELD,profile.getName());
        values.put(dbHelper.AGE_FIELD,profile.getAge());
        values.put(dbHelper.DATE_OF_BIRTH_FIELD,profile.getDateOfBirth());
        values.put(dbHelper.BLOOD_GROUP_FIELD,profile.getBloodGroup());
        values.put(dbHelper.GENDER_FIELD,profile.getGender());
        values.put(dbHelper.WEIGHT_FIELD,profile.getWeight());
        values.put(dbHelper.HEIGHT_FIELD,profile.getHeight());
        values.put(dbHelper.EMAIL_FIELD,profile.getEmail());
        values.put(dbHelper.PHONE_FIELD,profile.getPhone());
        values.put(dbHelper.BLOOD_PRESSURE_FIELD,profile.getBloodPressure());
        values.put(dbHelper.BMI_FIELD,profile.getBMI());

        long inserted=database.insert(dbHelper.TABLE_PROFILE,null,values);

        return inserted;
    }

    public ArrayList<User_Profile> getProfileNameAndId(){
        ArrayList<User_Profile> profiles=new ArrayList<User_Profile>();
        database=dbHelper.getReadableDatabase();

        String rawSql="select "+dbHelper.ID_FIELD+","+dbHelper.NAME_FIELD+" from "+dbHelper.TABLE_PROFILE;

        Cursor cursor=database.rawQuery(rawSql,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                String name=cursor.getString(cursor.getColumnIndex(dbHelper.NAME_FIELD));
                int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.ID_FIELD)));

                User_Profile profile=new User_Profile(id,name);
                profiles.add(profile);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return profiles;
    }

    public ArrayList<User_Profile> getProfileDetailsById(int profileId){
        ArrayList<User_Profile> profiles=new ArrayList<User_Profile>();
        database=dbHelper.getReadableDatabase();
        String rawSql="select * from "+dbHelper.TABLE_PROFILE+" where "+dbHelper.TABLE_PROFILE+"."+dbHelper.ID_FIELD+" = "
                +profileId;;
        Cursor cursor=database.rawQuery(rawSql,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                String name=cursor.getString(cursor.getColumnIndex(dbHelper.NAME_FIELD));
                int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.ID_FIELD)));
                String birthDate=cursor.getString(cursor.getColumnIndex(dbHelper.DATE_OF_BIRTH_FIELD));
                String bloodGroup=cursor.getString(cursor.getColumnIndex(dbHelper.BLOOD_GROUP_FIELD));
                String gender=cursor.getString(cursor.getColumnIndex(dbHelper.GENDER_FIELD));
                double height=Double.parseDouble(cursor.getString(cursor.getColumnIndex(dbHelper.HEIGHT_FIELD)));
                double weight=Double.parseDouble(cursor.getString(cursor.getColumnIndex(dbHelper.WEIGHT_FIELD)));
                String email=cursor.getString(cursor.getColumnIndex(dbHelper.EMAIL_FIELD));
                String phone=cursor.getString(cursor.getColumnIndex(dbHelper.PHONE_FIELD));
                int age=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.AGE_FIELD)));
                String bloodPressure=cursor.getString(cursor.getColumnIndex(dbHelper.BLOOD_PRESSURE_FIELD));
                String bmi=cursor.getString(cursor.getColumnIndex(dbHelper.BMI_FIELD));

                User_Profile profile=new User_Profile(id,name,age,birthDate,gender,bloodGroup,bloodPressure,bmi,height,weight,phone,email);
                profiles.add(profile);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return profiles;
    }

    public ArrayList<User_Profile> getUserInfoByName(String userName){
        ArrayList<User_Profile> profiles=new ArrayList<User_Profile>();
        database=dbHelper.getReadableDatabase();

        String rawSql="select * from "+DBHelper.TABLE_PROFILE+" WHERE "+dbHelper.TABLE_PROFILE+"."+dbHelper.NAME_FIELD+" LIKE '%"+userName+"%'";
        Cursor cursor=database.rawQuery(rawSql,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                String name=cursor.getString(cursor.getColumnIndex(DBHelper.NAME_FIELD));
                int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.ID_FIELD)));

                User_Profile profile=new User_Profile(id,name);
                profiles.add(profile);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return profiles;
    }

    public long updateProfile(User_Profile profile, int profileId){
        ContentValues values=new ContentValues();
        values.put(dbHelper.NAME_FIELD,profile.getName());
        values.put(dbHelper.AGE_FIELD,profile.getAge());
        values.put(dbHelper.DATE_OF_BIRTH_FIELD,profile.getDateOfBirth());
        values.put(dbHelper.BLOOD_GROUP_FIELD,profile.getBloodGroup());
        values.put(dbHelper.GENDER_FIELD,profile.getGender());
        values.put(dbHelper.WEIGHT_FIELD,profile.getWeight());
        values.put(dbHelper.HEIGHT_FIELD,profile.getHeight());
        values.put(dbHelper.EMAIL_FIELD,profile.getEmail());
        values.put(dbHelper.PHONE_FIELD,profile.getPhone());
        values.put(dbHelper.BLOOD_PRESSURE_FIELD,profile.getBloodPressure());
        values.put(dbHelper.BMI_FIELD,profile.getBMI());
        long insert=database.update(dbHelper.TABLE_PROFILE,values,DBHelper.ID_FIELD+" = "+profileId,null);

        return insert;
    }

    public void delete(long id) {
        database=dbHelper.getReadableDatabase();
        database.delete(DBHelper.TABLE_PROFILE, DBHelper.ID_FIELD + "=?",
                new String[] { String.valueOf(id) });

        closeDB();
    }

    public void allDelete() {
        database=dbHelper.getReadableDatabase();
        database.delete(dbHelper.TABLE_PROFILE, null, null);
        closeDB();
    }




}
