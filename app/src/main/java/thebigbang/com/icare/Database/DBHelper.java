package thebigbang.com.icare.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jakir on 6/8/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="icare.db";
    public static final int DATABASE_VERSION=1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // table name
    public static final String TABLE_PROFILE="tbl_profile";
    public static final String TABLE_DISEASES="tbl_diseases";
    public static final String TABLE_PRESCRIPTION="tbl_prescription";
    public static final String TABLE_REPORTS="tbl_reports";
    public static final String TABLE_DOCTORS_PROFILE="table_doctor";
    public static final String TABLE_VACCINATION="table_vaccinations";
    public static final String TABLE_VACCINATION_DOSE ="table_dose_date";
    public static final String TABLE_DIET_PLAN ="table_diet_plan";
    public static final String TABLE_DOCTORS_APPOINTMENT ="table_doctors_appointment";

    // common columns
    public static final String ID_FIELD="id";
    public static final String NAME_FIELD="name";

    // columns for profile table
    public static final String AGE_FIELD="age";
    public static final String DATE_OF_BIRTH_FIELD="birth_day";
    public static final String GENDER_FIELD="gender";
    public static final String BLOOD_GROUP_FIELD="blood_group";
    public static final String BLOOD_PRESSURE_FIELD="blood_pressure";
    public static final String BMI_FIELD="bmi";
    public static final String WEIGHT_FIELD="weight";
    public static final String HEIGHT_FIELD="height";
    public static final String PHONE_FIELD="phone";
    public static final String EMAIL_FIELD="email";

    public static final String PROFILE_TABLE_SQL ="create table "+TABLE_PROFILE+" ( "+ID_FIELD+" INTEGER PRIMARY KEY, "
            +NAME_FIELD+" TEXT, "+AGE_FIELD+" TEXT, "+DATE_OF_BIRTH_FIELD+" TEXT, "+GENDER_FIELD+" TEXT, "
            +BLOOD_GROUP_FIELD+" TEXT, "+BLOOD_PRESSURE_FIELD+" TEXT, "+BMI_FIELD+" TEXT, "+WEIGHT_FIELD+" TEXT, "+HEIGHT_FIELD+" TEXT, "+PHONE_FIELD+" TEXT, "
            +EMAIL_FIELD+" TEXT)";

    // columns for diseases table
    public static final String SYMPTOMS_FIELD="symptoms";
    public static final String DATE_FIELD="date";
    public static final String COMMENTS_FIELD="comments";
    public static final String MEDICINE_FIELD="medicine";
    public static final String USER_ID_FIELD="user_id";

    public static final String DISEASES_TABLE_SQL ="create table "+TABLE_DISEASES+" ( "+ID_FIELD+" INTEGER PRIMARY KEY, "
            +NAME_FIELD+" TEXT, "+SYMPTOMS_FIELD+" TEXT, "+MEDICINE_FIELD+" TEXT, "+COMMENTS_FIELD+" TEXT, "
            +DATE_FIELD+" TEXT, "+USER_ID_FIELD+" TEXT)";

    // columns for prescription table
    public static final String DISEASES_ID_FIELD="diseases_id";
    public static final String DOCTORS_ID_FIELD="doctors_id";
    public static final String PRESCRIPTION_IMAGE_FIELD="images";
    public static final String PRESCRIPTION_TITLE_FIELD="title";

    public static final String PRESCRIPTION_TABLE_SQL ="create table "+TABLE_PRESCRIPTION+" ( "+ID_FIELD+" INTEGER PRIMARY KEY, "
            +DISEASES_ID_FIELD+" TEXT, " +PRESCRIPTION_TITLE_FIELD+" TEXT, "+PRESCRIPTION_IMAGE_FIELD+" BLOB)";

    // columns for reports table
    public static final String PRESCRIPTION_ID_FIELD="prescription_id";
    public static final String REPORTS_IMAGE_FIELD="report_images";
    public static final String DIAGNOSTIC_FIELD="diagnostic_center_name";

    public static final String REPORTS_TABLE_SQL ="create table "+TABLE_REPORTS+" ( "+ID_FIELD+" INTEGER PRIMARY KEY, "
            +PRESCRIPTION_ID_FIELD+" TEXT, "+DIAGNOSTIC_FIELD+" TEXT, "+REPORTS_IMAGE_FIELD+" TEXT)";

    // columns for vaccination table
    public static final String TOTAL_DOSE_FIELD="total_dose";
    public static final String COMPLETE_DOSE_FIELD="complete_dose";
    public static final String REMAINING_DOSE_FIELD="remaining_dose";
    public static final String NEXT_DOSE_DATE_FIELD="next_date";
    public static final String CARE_CENTER_FIELD="care_center";
    public static final String EVENT_TIME_FIELD="event_time";


    public static final String VACCINATION_TABLE_SQL="create table "+TABLE_VACCINATION+" ( "+ID_FIELD+" INTEGER PRIMARY KEY, "
            +NAME_FIELD+" TEXT, "+TOTAL_DOSE_FIELD+" TEXT, "+COMPLETE_DOSE_FIELD+" TEXT, "+REMAINING_DOSE_FIELD+" TEXT, "
            +NEXT_DOSE_DATE_FIELD+" TEXT, "+USER_ID_FIELD+" TEXT, "+CARE_CENTER_FIELD+" TEXT, "+EVENT_TIME_FIELD+" TEXT)";


    //columns for vaccination dose date table
    public static final String VACCINE_ID_FIELD_DOSE="vaccine_id";


    public static final String DOSE_DATE_TABLE_SQL="create table "+ TABLE_VACCINATION_DOSE +" ( "+ID_FIELD+" INTEGER PRIMARY KEY, "
            +VACCINE_ID_FIELD_DOSE+" TEXT, "+DATE_FIELD+" TEXT, "+CARE_CENTER_FIELD+" TEXT)";

    // columns for diet plan Table
    public static final String BREAKFAST_FIELD="breakfast";
    public static final String LUNCH_FIELD="lunch";
    public static final String SNACKS_FIELD="snacks";
    public static final String DINNER_FIELD="dinner";
    public static final String NOTES_FIELD="notes";
    public static final String DAY_FIELD="day";

    public static final String DIET_PLAN_TABLE_SQL="create table "+ TABLE_DIET_PLAN +" ( "+ID_FIELD+" INTEGER PRIMARY KEY, "
            +DATE_FIELD+" TEXT, " +DAY_FIELD+" TEXT, "+BREAKFAST_FIELD+" TEXT, "+LUNCH_FIELD+" TEXT, "+SNACKS_FIELD+" TEXT, "
            +DINNER_FIELD+" TEXT, "+NOTES_FIELD+" TEXT, "+USER_ID_FIELD+" TEXT)";

    // columns for doctors profile table
    public static final String SPECIALITIES_FIELD="specialities";
    public static final String HOSPITAL_FIELD="hospital";
    public static final String CHAMBER_FIELD="chamber";
    public static final String FEE_FIELD="fee";

    public static final String DOCTORS_PROFILE_SQL="create table "+ TABLE_DOCTORS_PROFILE +" ( "+ID_FIELD+" INTEGER PRIMARY KEY, "
            +NAME_FIELD+" TEXT, " +SPECIALITIES_FIELD+" TEXT, "+HOSPITAL_FIELD+" TEXT, "+EMAIL_FIELD+" TEXT, "+PHONE_FIELD+" TEXT, "
            +CHAMBER_FIELD+" TEXT, "+FEE_FIELD+" TEXT, "+USER_ID_FIELD+" TEXT)";

    // columns for doctors appointment table
    public static final String TIME_FIELD="time";

    public static final String DOCTORS_APPOINTMENT_SQL="create table "+ TABLE_DOCTORS_PROFILE +" ( "+ID_FIELD+" INTEGER PRIMARY KEY, "
            +DOCTORS_ID_FIELD+" TEXT, " +DATE_FIELD+" TEXT, "+TIME_FIELD+" TEXT, "+NOTES_FIELD+" TEXT, "+USER_ID_FIELD+" TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PROFILE_TABLE_SQL);
        db.execSQL(DISEASES_TABLE_SQL);
        db.execSQL(PRESCRIPTION_TABLE_SQL);
        db.execSQL(VACCINATION_TABLE_SQL);
        db.execSQL(DOSE_DATE_TABLE_SQL);
        db.execSQL(DIET_PLAN_TABLE_SQL);
        db.execSQL(DOCTORS_PROFILE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISEASES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESCRIPTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINATION_DOSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIET_PLAN);
        db.execSQL("DROP TABLE IF EXISTS " + DOCTORS_PROFILE_SQL);
        onCreate(db);

    }
}
