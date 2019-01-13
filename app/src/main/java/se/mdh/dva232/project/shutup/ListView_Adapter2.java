package se.mdh.dva232.project.shutup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

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
        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
        element e = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //define variables
        TextView NameView = (TextView) convertView.findViewById(R.id.item_name);
        TextView NumberView = (TextView) convertView.findViewById(R.id.item_duration);
        TextView DateView = (TextView) convertView.findViewById(R.id.date_view);


        //set values
        NameView.setText(e.name);
        NumberView.setText(e.start_time+" - "+e.end_time);
        DateView.setText(e.date);

        ImageButton button = (ImageButton) convertView.findViewById(R.id.delete_button);
        button.setImageResource(R.drawable.baseline_delete_black_18dp);

        return convertView;
    }
}