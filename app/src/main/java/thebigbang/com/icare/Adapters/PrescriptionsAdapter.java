package thebigbang.com.icare.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import thebigbang.com.icare.Model.Prescriptions;
import thebigbang.com.icare.R;

/**
 * Created by Jakir on 6/29/2015.
 */
public class PrescriptionsAdapter extends ArrayAdapter<Prescriptions> {
    Context context;
    int layoutResourceId;
    ArrayList<Prescriptions> data=new ArrayList<Prescriptions>();

    public PrescriptionsAdapter(Context context, int layoutResourceId, ArrayList<Prescriptions> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ImageHolder();
            holder.tvPrescriptionsTitle = (TextView) row.findViewById(R.id.tvPrescriptionsTitle);
            holder.imvPrescriptions = (ImageView)row.findViewById(R.id.imvPrescriptions);
            row.setTag(holder);
        }
        else
        {
            holder = (ImageHolder)row.getTag();
        }

        Prescriptions picture = data.get(position);
        holder.tvPrescriptionsTitle.setText(picture.getTitle());
        //convert byte to bitmap take from contact class

        byte[] outImage=picture.getImage();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.imvPrescriptions.setImageBitmap(theImage);
        return row;

    }

    static class ImageHolder
    {
        ImageView imvPrescriptions;
        TextView tvPrescriptionsTitle;
    }

}
