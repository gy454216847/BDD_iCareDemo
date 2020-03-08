package thebigbang.com.icare.Database.DBAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thebigbang.com.icare.Database.DBHelper;
import thebigbang.com.icare.Model.Diet_Plan;
import thebigbang.com.icare.Model.Diseases;
import thebigbang.com.icare.Model.Vaccination;

/**
 * Created by Jakir on 6/26/2015.
 */
public class DBAdapter_Diet {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBAdapter_Diet(Context context){
        dbHelper=new DBHelper(context);
    }

    public void openDB(){
        database=dbHelper.getWritableDatabase();
    }
    public void closeDB(){
        database.close();
    }

    public long addNewDietPlan(Diet_Plan dietPlan){
        ContentValues values=new ContentValues();
        values.put(dbHelper.DATE_FIELD, dietPlan.getDate());
        values.put(dbHelper.DAY_FIELD, dietPlan.getDay());
        values.put(dbHelper.BREAKFAST_FIELD, dietPlan.getBreakFast());
        values.put(dbHelper.LUNCH_FIELD,dietPlan.getLunch());
        values.put(dbHelper.SNACKS_FIELD,dietPlan.getAfternoonSnacks());
        values.put(dbHelper.DINNER_FIELD,dietPlan.getDinner());
        values.put(dbHelper.NOTES_FIELD,dietPlan.getNotes());
        values.put(dbHelper.USER_ID_FIELD,dietPlan.getProfileId());

        long inserted=database.insert(dbHelper.TABLE_DIET_PLAN,null,values);
        return inserted;
    }

    public ArrayList<Diet_Plan> getDietPlanDateAndID(int profileId){
        ArrayList<Diet_Plan> diet_plans=new ArrayList<>();
        database=dbHelper.getReadableDatabase();

        String rawSql="select "+dbHelper.ID_FIELD+","+dbHelper.DATE_FIELD+","+dbHelper.DAY_FIELD
                +" from "+dbHelper.TABLE_DIET_PLAN +" where "+dbHelper.TABLE_DIET_PLAN+"."+dbHelper.USER_ID_FIELD+" = "+profileId
                +" ORDER BY "+dbHelper.DATE_FIELD+" ASC";

        Cursor cursor=database.rawQuery(rawSql,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                String date=cursor.getString(cursor.getColumnIndex(dbHelper.DATE_FIELD));
                String day=cursor.getString(cursor.getColumnIndex(dbHelper.DAY_FIELD));
                int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.ID_FIELD)));

                Diet_Plan dietPlan=new Diet_Plan(id,date,day);
                diet_plans.add(dietPlan);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return diet_plans;
    }


    public ArrayList<Diet_Plan> getDietPlanDetails(int dietId){
        ArrayList<Diet_Plan> dietPlans=new ArrayList<Diet_Plan>();
        database=dbHelper.getReadableDatabase();
        String rawSql="select * from "+dbHelper.TABLE_DIET_PLAN+" where "+dbHelper.TABLE_DIET_PLAN+"."+dbHelper.ID_FIELD+" = "
                +dietId;
        Cursor cursor=database.rawQuery(rawSql,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                String date=cursor.getString(cursor.getColumnIndex(dbHelper.DATE_FIELD));
                String day=cursor.getString(cursor.getColumnIndex(dbHelper.DAY_FIELD));
                String breakFast=cursor.getString(cursor.getColumnIndex(dbHelper.BREAKFAST_FIELD));
                String lunch=cursor.getString(cursor.getColumnIndex(dbHelper.LUNCH_FIELD));
                String snacks=cursor.getString(cursor.getColumnIndex(dbHelper.SNACKS_FIELD));
                String dinner=cursor.getString(cursor.getColumnIndex(dbHelper.DINNER_FIELD));
                String notes=cursor.getString(cursor.getColumnIndex(dbHelper.NOTES_FIELD));
                int profileId=Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.USER_ID_FIELD)));

                Diet_Plan dietPlan=new Diet_Plan(profileId,date,day,breakFast,lunch,snacks,dinner,notes);
                dietPlans.add(dietPlan);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return dietPlans;
    }

    public long updateDietPlan(Diet_Plan dietPlan, int dietId){
        ContentValues values=new ContentValues();
        values.put(dbHelper.DATE_FIELD, dietPlan.getDate());
        values.put(dbHelper.DAY_FIELD, dietPlan.getDay());
        values.put(dbHelper.BREAKFAST_FIELD, dietPlan.getBreakFast());
        values.put(dbHelper.LUNCH_FIELD,dietPlan.getLunch());
        values.put(dbHelper.SNACKS_FIELD,dietPlan.getAfternoonSnacks());
        values.put(dbHelper.DINNER_FIELD,dietPlan.getDinner());
        values.put(dbHelper.NOTES_FIELD,dietPlan.getNotes());
        values.put(dbHelper.USER_ID_FIELD,dietPlan.getProfileId());
        long insert=database.update(dbHelper.TABLE_DIET_PLAN,values,DBHelper.ID_FIELD+" = "+dietId,null);

        return insert;
    }


    public void delete(long id) {
        database=dbHelper.getReadableDatabase();
        database.delete(DBHelper.TABLE_DIET_PLAN, DBHelper.ID_FIELD + "=?",
                new String[] { String.valueOf(id) });

        closeDB();
    }

    public void allDelete() {
        database=dbHelper.getReadableDatabase();
        database.delete(dbHelper.TABLE_DIET_PLAN, null, null);
        closeDB();
    }
}
