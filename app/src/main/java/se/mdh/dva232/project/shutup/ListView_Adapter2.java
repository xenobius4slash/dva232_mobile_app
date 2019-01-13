package se.mdh.dva232.project.shutup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListView_Adapter2 extends ArrayAdapter<EventOutput> {


    public ListView_Adapter2(Context context, ArrayList<EventOutput> elements) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, elements);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //define variables
        TextView NameView = convertView.findViewById(R.id.item_name);
        TextView DateView = convertView.findViewById(R.id.date_view);
        TextView NumberView = convertView.findViewById(R.id.item_duration);
        ImageButton button = convertView.findViewById(R.id.delete_button);


        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
        final EventOutput e = getItem(position);

        //set values
        NameView.setText(e.getName());
        NumberView.setText(e.getStartTime()+" - "+e.getEndTime());
        DateView.setText(e.getStartDate());
        button.setImageResource(R.drawable.baseline_delete_black_18dp);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"button clicked",Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle(getContext().getString(R.string.f3_confirm_delete_title));
                //builder.setMessage("Message");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("confirmation dialog","positive which: "+which+" id: "+e.getId());
                                EventController EC = new EventController(getContext());
                                EC.deleteEventById(e.getId());
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("confirmation dialog","negative which: "+which);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return convertView;
    }
}