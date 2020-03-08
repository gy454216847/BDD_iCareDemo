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
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Diseases;
import thebigbang.com.icare.Model.Diseases;
import thebigbang.com.icare.R;

/**
 * Created by Jakir on 6/19/2015.
 */
public class DiseasesAdapter extends BaseAdapter {

    private ArrayList<Diseases> diseasesArrayList;
    private Context context;
    private LayoutInflater inflater;
    private DBAdapter_Diseases dbAdapterDiseases;
    private static int profileId;

    public DiseasesAdapter(Context context, ArrayList<Diseases> diseasesArrayList,int profileId) {
        this.context = context;
        this.profileId=profileId;
        this.diseasesArrayList = diseasesArrayList;
        inflater = LayoutInflater.from(context);
        dbAdapterDiseases = new DBAdapter_Diseases(context);
    }


    @Override
    public int getCount() {
        return diseasesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return diseasesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.sample_diseases_listview, parent,false);

            viewHolder =new ViewHolder();
            viewHolder.tvDiseasesName = (TextView) convertView.findViewById(R.id.tvDiseasesNameInHealthConditions);
            viewHolder.imvArrow= (ImageView) convertView.findViewById(R.id.imvArrowInHealthConditions);

            convertView.setTag(viewHolder);
            viewHolder.imvArrow.setTag(diseasesArrayList.get(position));
            viewHolder.tvDiseasesName.setTag(diseasesArrayList.get(position));


        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvDiseasesName.setText(diseasesArrayList.get(position).getName());

        viewHolder.tvDiseasesName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = ((Diseases) v.getTag()).getId();

                Intent gotoDiseasesDetails = new Intent(context, DiseasesDetailsActivity.class);
                gotoDiseasesDetails.putExtra("id", id);
                gotoDiseasesDetails.putExtra("profileId",profileId);
                Toast.makeText(context,"profile Id: "+profileId,Toast.LENGTH_SHORT).show();
                gotoDiseasesDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoDiseasesDetails.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(gotoDiseasesDetails);
            }
        });
        viewHolder.imvArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = ((Diseases) v.getTag()).getId();
                Intent gotoDiseasesDetails = new Intent(context, DiseasesDetailsActivity.class);
                gotoDiseasesDetails.putExtra("id", id);
                gotoDiseasesDetails.putExtra("profileId",profileId);
                Toast.makeText(context,"profile Id: "+profileId,Toast.LENGTH_SHORT).show();
                gotoDiseasesDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoDiseasesDetails.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(gotoDiseasesDetails);
            }
        });

        viewHolder.tvDiseasesName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int id = ((Diseases) v.getTag()).getId();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Confirm Delete...");
                alertDialog.setMessage("Are you sure you want to delete this?");
                alertDialog.setIcon(R.drawable.ic_delete);

                alertDialog.setPositiveButton("YES",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dbAdapterDiseases.delete(id);
                        diseasesArrayList = dbAdapterDiseases.getDiseasesNameAndId(profileId);
                        notifyDataSetChanged();
                    }
                });
                alertDialog.setNeutralButton("All",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0,int arg1) {
                        dbAdapterDiseases.allDelete();
                        diseasesArrayList = dbAdapterDiseases.getDiseasesNameAndId(profileId);
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
        TextView tvDiseasesName;
        ImageView imvArrow;
    }

}
