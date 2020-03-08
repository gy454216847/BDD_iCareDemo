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

import thebigbang.com.icare.Activities.Diet.DietDetailsActivity;
import thebigbang.com.icare.Database.DBAdapter.DBAdapter_Diet;
import thebigbang.com.icare.Model.Diet_Plan;
import thebigbang.com.icare.R;

/**
 * Created by Jakir on 6/20/2015.
 */
public class DietAdapter extends BaseAdapter {

    private ArrayList<Diet_Plan> dietPlanArrayList;
    private Context context;
    private LayoutInflater inflater;
    private DBAdapter_Diet dbAdapter_diet;
    private static int profileId;

    public DietAdapter(Context context, ArrayList<Diet_Plan> diet_plans,int profileId) {
        this.context = context;
        this.profileId=profileId;
        this.dietPlanArrayList = diet_plans;
        inflater = LayoutInflater.from(context);
        dbAdapter_diet = new DBAdapter_Diet(context);
    }

    @Override
    public int getCount() {
        return dietPlanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dietPlanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.sample_diet_plan_listview, parent,false);

            viewHolder =new ViewHolder();
            viewHolder.tvDay = (TextView) convertView.findViewById(R.id.tvNameOfDay);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDateOfDietPlan);
            viewHolder.imvArrow= (ImageView) convertView.findViewById(R.id.imvArrowInDietList);

            convertView.setTag(viewHolder);
            viewHolder.imvArrow.setTag(dietPlanArrayList.get(position));
            viewHolder.tvDay.setTag(dietPlanArrayList.get(position));
            viewHolder.tvDate.setTag(dietPlanArrayList.get(position));

        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tvDate.setText(dietPlanArrayList.get(position).getDate());
        viewHolder.tvDay.setText(dietPlanArrayList.get(position).getDay());

        viewHolder.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = ((Diet_Plan) v.getTag()).getId();

                Intent gotoDietDetails = new Intent(context, DietDetailsActivity.class);
                gotoDietDetails.putExtra("id", id);
                gotoDietDetails.putExtra("profileId", profileId);
                gotoDietDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoDietDetails.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(gotoDietDetails);
            }
        });
        viewHolder.imvArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = ((Diet_Plan) v.getTag()).getId();
                Intent gotoDietDetails = new Intent(context, DietDetailsActivity.class);
                gotoDietDetails.putExtra("id", id);
                gotoDietDetails.putExtra("profileId", profileId);
                gotoDietDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gotoDietDetails.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(gotoDietDetails);
            }
        });


        viewHolder.tvDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int id = ((Diet_Plan) v.getTag()).getId();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Confirm Delete...");
                alertDialog.setMessage("Are you sure you want to delete this?");
                alertDialog.setIcon(R.drawable.ic_delete);

                alertDialog.setPositiveButton("YES",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dbAdapter_diet.delete(id);
                        dietPlanArrayList = dbAdapter_diet.getDietPlanDateAndID(profileId);
                        notifyDataSetChanged();
                    }
                });
                alertDialog.setNeutralButton("All",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0,int arg1) {
                        dbAdapter_diet.allDelete();
                        dietPlanArrayList = dbAdapter_diet.getDietPlanDateAndID(profileId);
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
        TextView tvDate;
        TextView tvDay;
        ImageView imvArrow;

    }
}
