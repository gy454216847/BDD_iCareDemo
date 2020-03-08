package thebigbang.com.icare.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thebigbang.com.icare.Activities.DashboardActivity;
import thebigbang.com.icare.Activities.Medical.DoctorsProfile.DoctorsDetailsActivity;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Doctors_Profile;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_User_Profile;
import thebigbang.com.icare.Model.Doctors;
import thebigbang.com.icare.R;

/**
 * Created by Jakir on 6/10/2015.
 */
public class DoctorProfileListAdapter extends BaseAdapter {

    private ArrayList<Doctors> profileArrayList;
    private Context context;
    private LayoutInflater inflater;
    private DBAdapter_Doctors_Profile dbAdapterDoctorsProfile;
    private static int profileId;

    public DoctorProfileListAdapter(Context context, ArrayList<Doctors> profileArrayList,int profileId) {
        this.context = context;
        this.profileArrayList = profileArrayList;
        inflater = LayoutInflater.from(context);
        this.profileId=profileId;
        dbAdapterDoctorsProfile = new DBAdapter_Doctors_Profile(context);
    }


    @Override
    public int getCount() {
        return profileArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return profileArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.sample_doctor_profile_listview, parent,false);

            viewHolder =new ViewHolder();
            viewHolder.tvDoctorsName = (TextView) convertView.findViewById(R.id.tvDoctorNameInSampleList);
            viewHolder.tvSpecialities = (TextView) convertView.findViewById(R.id.tvSpecialitiesInSampleList);
            viewHolder.tvFee = (TextView) convertView.findViewById(R.id.tvDoctorsFeeInSampleList);
            viewHolder.imvArrow= (ImageView) convertView.findViewById(R.id.imvArrowInHomeScreen);

            convertView.setTag(viewHolder);
            viewHolder.imvArrow.setTag(profileArrayList.get(position));
            viewHolder.tvDoctorsName.setTag(profileArrayList.get(position));

        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvDoctorsName.setText(profileArrayList.get(position).getName());
        viewHolder.tvSpecialities.setText(profileArrayList.get(position).getSpecialities());
        viewHolder.tvFee.setText(profileArrayList.get(position).getFee());
        Toast.makeText(context, "DoctorsId: "+profileArrayList.get(position).getId(), Toast.LENGTH_SHORT).show();

        viewHolder.tvDoctorsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = ((Doctors) v.getTag()).getId();
                Intent gotoDoctorsDetails = new Intent(context, DoctorsDetailsActivity.class);
                gotoDoctorsDetails.putExtra("id", id);
                gotoDoctorsDetails.putExtra("profileId", profileId);
                gotoDoctorsDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoDoctorsDetails.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(gotoDoctorsDetails);
            }
        });
        viewHolder.imvArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = ((Doctors) v.getTag()).getId();
                Intent gotoDoctorsDetails = new Intent(context, DoctorsDetailsActivity.class);
                gotoDoctorsDetails.putExtra("id", id);
                gotoDoctorsDetails.putExtra("profileId", profileId);
                gotoDoctorsDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoDoctorsDetails.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(gotoDoctorsDetails);
            }
        });

        viewHolder.tvDoctorsName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int id = ((Doctors) v.getTag()).getId();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Confirm Delete...");
                alertDialog.setMessage("Are you sure you want to delete this?");
                alertDialog.setIcon(R.drawable.ic_delete);

                alertDialog.setPositiveButton("YES",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dbAdapterDoctorsProfile.delete(id);
                        profileArrayList = dbAdapterDoctorsProfile.getDoctorsNameSpecialityFee(profileId);
                        notifyDataSetChanged();
                    }
                });
                alertDialog.setNeutralButton("All",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0,int arg1) {
                        dbAdapterDoctorsProfile.allDelete();
                        profileArrayList = dbAdapterDoctorsProfile.getDoctorsNameSpecialityFee(profileId);
                        notifyDataSetChanged();
                    }
                });

                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                alertDialog.show();
                return false;
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tvDoctorsName;
        TextView tvSpecialities;
        TextView tvFee;
        ImageView imvArrow;
    }

}
