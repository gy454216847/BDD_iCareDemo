package thebigbang.com.icare.Adapters;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jakir on 6/10/2015.
 */
public class ToastAdapter {
    private Context context;
    public ToastAdapter(Context context) {
        this.context=context;
    }

    public void toast(String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }
}
