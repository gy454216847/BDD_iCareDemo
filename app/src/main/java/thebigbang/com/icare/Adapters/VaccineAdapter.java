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

import thebigbang.com.icare.Activities.Medical.PresentHealthInfo.DiseasesDetailsActivity;
import thebigbang.com.icare.Activities.Medical.Vaccination.VaccineDetailsActivity;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Diseases;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Vaccination;
import thebigbang.com.icare.Model.Diseases;
import thebigbang.com.icare.Model.Vaccination;
import thebigbang.com.icare.R;

/**
 * Created by Jakir on 6/20/2015.
 */
public class VaccineAdapter extends BaseAdapter {

    private ArrayList<Vaccination> vaccinationArrayList;
    private Context context;
    private LayoutInflater inflater;
    private DBAdapter_Vaccination dbAdapter_vaccination;
    private static int profileId;

    public VaccineAdapter(Context context, ArrayList<Vaccination> vaccinationArrayList,int profileId) {
        this.context = context;
        this.vaccinationArrayList = vaccinationArrayList;
        inflater = LayoutInflater.from(context);
        this.profileId=profileId;
        dbAdapter_vaccination = new DBAdapter_Vaccination(context);
    }

    @Override
    public int getCount() {
        return vaccinationArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return vaccinationArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.sample_vaccine_listview, parent,false);

            viewHolder =new ViewHolder();
            viewHolder.tvVaccineName = (TextView) convertView.findViewById(R.id.tvVaccineName);
            viewHolder.tvNextDate = (TextView) convertView.findViewById(R.id.tvNextDate);
            viewHolder.imvArrow= (ImageView) convertView.findViewById(R.id.imvArrowInVaccinations);

            convertView.setTag(viewHolder);
            viewHolder.imvArrow.setTag(vaccinationArrayList.get(position));
            viewHolder.tvVaccineName.setTag(vaccinationArrayList.get(position));
            viewHolder.tvNextDate.setTag(vaccinationArrayList.get(position));

        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvVaccineName.setText(vaccinationArrayList.get(position).getName());
        viewHolder.tvNextDate.setText(vaccinationArrayList.get(position).getNextDate());

        viewHolder.tvVaccineName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = ((Vaccination) v.getTag()).getId();

                Intent gotoVaccineDetails = new Intent(context, VaccineDetailsActivity.class);
                gotoVaccineDetails.putExtra("id", id);
                gotoVaccineDetails.putExtra("profileId",profileId);
                gotoVaccineDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoVaccineDetails.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(gotoVaccineDetails);
            }
        });
        viewHolder.imvArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = ((Vaccination) v.getTag()).getId();
                Intent gotoVaccineDetails = new Intent(context, VaccineDetailsActivity.class);
                gotoVaccineDetails.putExtra("id", id);
                gotoVaccineDetails.putExtra("profileId",profileId);
                gotoVaccineDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoVaccineDetails.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(gotoVaccineDetails);
            }
        });

        viewHolder.tvVaccineName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int id = ((Vaccination) v.getTag()).getId();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Confirm Delete...");
                alertDialog.setMessage("Are you sure you want to delete this?");
                alertDialog.setIcon(R.drawable.ic_delete);

                alertDialog.setPositiveButton("YES",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dbAdapter_vaccination.delete(id);
                        vaccinationArrayList = dbAdapter_vaccination.getVaccineNameAndId(profileId);
                        notifyDataSetChanged();
                    }
                });
                alertDialog.setNeutralButton("All",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0,int arg1) {
                        dbAdapter_vaccination.allDelete();
                        vaccinationArrayList = dbAdapter_vaccination.getVaccineNameAndId(profileId);
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
        TextView tvVaccineName;
        ImageView imvArrow;
        TextView tvNextDate;
        ImageView imvVaccineStatus;
    }
}
