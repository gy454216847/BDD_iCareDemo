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
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_User_Profile;
import thebigbang.com.icare.Model.User_Profile;
import thebigbang.com.icare.R;

/**
 * Created by Jakir on 6/10/2015.
 */
public class ProfileListAdapter extends BaseAdapter {

    private ArrayList<User_Profile> profileArrayList;
    private Context context;
    private LayoutInflater inflater;
    private DBAdapter_User_Profile dbAdapterUserProfile;

    public ProfileListAdapter(Context context, ArrayList<User_Profile> profileArrayList) {
        this.context = context;
        this.profileArrayList = profileArrayList;
        inflater = LayoutInflater.from(context);
        dbAdapterUserProfile = new DBAdapter_User_Profile(context);
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
            convertView = inflater.inflate(R.layout.sample_profile_listview, parent,false);

            viewHolder =new ViewHolder();
            viewHolder.tvUserProfileName = (TextView) convertView.findViewById(R.id.tvUserProfileName);
            viewHolder.imvArrow= (ImageView) convertView.findViewById(R.id.imvArrowInHomeScreen);
            viewHolder.imvProfilePhoto= (ImageView) convertView.findViewById(R.id.imvProfilePic);

            convertView.setTag(viewHolder);
            viewHolder.imvArrow.setTag(profileArrayList.get(position));
            viewHolder.tvUserProfileName.setTag(profileArrayList.get(position));

        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvUserProfileName.setText(profileArrayList.get(position).getName());

        viewHolder.tvUserProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = ((User_Profile) v.getTag()).getId();
                Intent gotoDashboardActivity = new Intent(context, DashboardActivity.class);
                gotoDashboardActivity.putExtra("id", id);
                gotoDashboardActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoDashboardActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(gotoDashboardActivity);
            }
        });
        viewHolder.imvArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = ((User_Profile) v.getTag()).getId();
                Intent gotoDashboardActivity = new Intent(context, DashboardActivity.class);
                gotoDashboardActivity.putExtra("id", id);
                gotoDashboardActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoDashboardActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(gotoDashboardActivity);
            }
        });

        viewHolder.tvUserProfileName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int id = ((User_Profile) v.getTag()).getId();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Confirm Delete...");
                alertDialog.setMessage("Are you sure you want to delete this?");
                alertDialog.setIcon(R.drawable.ic_delete);

                alertDialog.setPositiveButton("YES",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dbAdapterUserProfile.delete(id);
                        profileArrayList = dbAdapterUserProfile.getProfileNameAndId();
                        notifyDataSetChanged();
                    }
                });
                alertDialog.setNeutralButton("All",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0,int arg1) {
                        dbAdapterUserProfile.allDelete();
                        profileArrayList = dbAdapterUserProfile.getProfileNameAndId();
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
        TextView tvUserProfileName;
        ImageView imvArrow;
        ImageView imvProfilePhoto;
    }

}
