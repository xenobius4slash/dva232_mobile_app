package se.mdh.dva232.project.shutup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ListView_Adapter2 extends ArrayAdapter<element> {


    public ListView_Adapter2(Context context, List<element> elements) {
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
        element e = getItem(position);

        //set values
        NameView.setText(e.name);
        NumberView.setText(e.start_time+" - "+e.end_time);
        DateView.setText(e.date);
        button.setImageResource(R.drawable.baseline_delete_black_18dp);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"button clicked",Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}