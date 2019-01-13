package se.mdh.dva232.project.shutup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

public class ListView_Adapter extends BaseAdapter{

    private static LayoutInflater inflater = null;
    Context context;
    String [] data;

    //Constructor
    public ListView_Adapter(Context c, String [] d)
    {
        Log.d("listview","entering constructor");

        this.context = c;
        this.data = d;

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Log.d("getView","entering getView function");
        //Declare variables
        final View view = inflater.inflate(R.layout.list_item,parent,false);

        TextView name = (TextView) view.findViewById(R.id.item_name);
        TextView duration = (TextView) view.findViewById(R.id.item_duration);
        ImageButton button = (ImageButton) view.findViewById(R.id.delete_button);

        //set text
        name.setText(data[position]);
        duration.setText(data[position]);
        button.setImageResource(R.drawable.baseline_delete_black_18dp);

        Log.d("getView","name: "+name+" ;Duration: "+duration);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( context, "Delete button clicked", Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
