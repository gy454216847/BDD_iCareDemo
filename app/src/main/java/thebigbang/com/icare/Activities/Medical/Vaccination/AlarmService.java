package thebigbang.com.icare.Activities.Medical.Vaccination;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Vaccination;
import thebigbang.com.icare.Model.Vaccination;
import thebigbang.com.icare.R;

/**
 * Created by Jakir on 6/30/2015.
 */
public class AlarmService extends Service {

    private NotificationManager mManager;
    private ArrayList<Vaccination> vaccinationList;
    private DBAdapter_Vaccination dBAdapterVaccination;

    private static long eventTime;
    private static long currentTime;
    private static String vaccineName;
    private static String nextDate;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onCreate() {

    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        vaccinationList=new ArrayList<>();
        dBAdapterVaccination=new DBAdapter_Vaccination(this);
        vaccinationList=dBAdapterVaccination.getVaccineInfoAndEventTime();
        for(Vaccination vaccine:vaccinationList){
            eventTime=vaccine.getEventTime();
            vaccineName=vaccine.getName();
            nextDate=vaccine.getNextDate();
        }
        Log.e("Event TIme In Service",eventTime+"");
        Log.e("Vaccine Name In Service",vaccineName+"");
        Log.e("Next Date In Service",nextDate+"");
        long reminderTime=eventTime-AlarmManager.INTERVAL_DAY;

        currentTime= System.currentTimeMillis();
        long timeDifference=eventTime-currentTime;
        if(timeDifference< AlarmManager.INTERVAL_DAY){
            mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
            Intent intent1 = new Intent(this.getApplicationContext(), VaccinationActivity.class);

            Notification notification = new Notification(R.drawable.ic_launcher, "This is a test message!", System.currentTimeMillis());
            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.setLatestEventInfo(this.getApplicationContext(), vaccineName, nextDate, pendingNotificationIntent);

            mManager.notify(0, notification);
        }
        else{
            Log.e("Not Alarm Time",currentTime+"");
        }
        Log.e("Current Time",currentTime+"");

    }
    @Override
    public void onDestroy() {
        Toast.makeText(AlarmService.this, "Alarm Service Stop!", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

}