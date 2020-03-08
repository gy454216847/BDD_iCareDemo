package thebigbang.com.icare.Database.DBAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thebigbang.com.icare.Database.DBHelper;
import thebigbang.com.icare.Model.Diseases;
import thebigbang.com.icare.Model.User_Profile;

/**
 * Created by Jakir on 6/19/2015.
 */
public class DBAdapter_Diseases {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBAdapter_Diseases(Context context){
        dbHelper=new DBHelper(context);
    }

    public void openDB(){
        database=dbHelper.getWritableDatabase();
    }
    public void closeDB(){
        database.close();
    }

    public long addNewDiseases(Diseases diseases){
        ContentValues values=new ContentValues();
        values.put(dbHelper.NAME_FIELD,diseases.getName());
        values.put(dbHelper.SYMPTOMS_FIELD,diseases.getSymptoms());
        values.put(dbHelper.MEDICINE_FIELD,diseases.getMedicines());
        values.put(dbHelper.COMMENTS_FIELD,diseases.getComments());
        values.put(dbHelper.DATE_FIELD,diseases.getDate());
        values.put(dbHelper.USER_ID_FIELD,diseases.getUserID());


        long inserted=database.insert(dbHelper.TABLE_DISEASES,null,values);

        return inserted;
    }

    public ArrayList<Diseases> getDiseasesNameAndId(int profileId){
        ArrayList<Diseases> diseases=new ArrayList<Diseases>();
        database=dbHelper.getReadableDatabase();

        String rawSql="select "+dbHelper.ID_FIELD+","+dbHelper.NAME_FIELD+" from "+dbHelper.TABLE_DISEASES +" where "+dbHelper.TABLE_DISEASES+"."+dbHelper.USER_ID_FIELD+" = "
                +profileId;

        Cursor cursor=database.rawQuery(rawSql,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                String name=cursor.getString(cursor.getColumnIndex(dbHelper.NAME_FIELD));
                int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.ID_FIELD)));

                Diseases diseasess=new Diseases(id,name);
                diseases.add(diseasess);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return diseases;
    }

    public ArrayList<Diseases> getDiseasesDetailsById(int diseasesId){
        ArrayList<Diseases> diseasess=new ArrayList<Diseases>();
        database=dbHelper.getReadableDatabase();
        String rawSql="select * from "+dbHelper.TABLE_DISEASES+" where "+dbHelper.TABLE_DISEASES+"."+dbHelper.ID_FIELD+" = "
                +diseasesId;
        Cursor cursor=database.rawQuery(rawSql,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                String diseasesName=cursor.getString(cursor.getColumnIndex(dbHelper.NAME_FIELD));
                String date=cursor.getString(cursor.getColumnIndex(dbHelper.DATE_FIELD));
                String symptoms=cursor.getString(cursor.getColumnIndex(dbHelper.SYMPTOMS_FIELD));
                String medicine=cursor.getString(cursor.getColumnIndex(dbHelper.MEDICINE_FIELD));
                String comments=cursor.getString(cursor.getColumnIndex(dbHelper.COMMENTS_FIELD));

                Diseases diseases=new Diseases(diseasesName,symptoms,comments,medicine,date);
                diseasess.add(diseases);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return diseasess;
    }
    public long updateDiseasesInfo(Diseases diseases, int diseasesId){
        ContentValues values=new ContentValues();
        values.put(dbHelper.NAME_FIELD,diseases.getName());
        values.put(dbHelper.SYMPTOMS_FIELD,diseases.getSymptoms());
        values.put(dbHelper.MEDICINE_FIELD,diseases.getMedicines());
        values.put(dbHelper.COMMENTS_FIELD,diseases.getComments());
        values.put(dbHelper.DATE_FIELD,diseases.getDate());
        values.put(dbHelper.USER_ID_FIELD,diseases.getUserID());
        long insert=database.update(dbHelper.TABLE_DISEASES,values,DBHelper.ID_FIELD+" = "+diseasesId,null);

        return insert;
    }


    public void delete(long id) {
        database=dbHelper.getReadableDatabase();
        database.delete(DBHelper.TABLE_DISEASES, DBHelper.ID_FIELD + "=?",
                new String[] { String.valueOf(id) });

        closeDB();
    }

    public void allDelete() {
        database=dbHelper.getReadableDatabase();
        database.delete(dbHelper.TABLE_DISEASES, null, null);
        closeDB();
    }
}
