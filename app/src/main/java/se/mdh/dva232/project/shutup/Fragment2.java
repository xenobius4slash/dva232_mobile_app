package se.mdh.dva232.project.shutup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class Fragment2 extends Fragment {

    private static Boolean debugLifecycle = false;
    private Fragment2ListViewAdapter data;

    public Fragment2() {
        // Required empty public constructor
    }

    public static Fragment2 newInstance() {
        if (debugLifecycle) { Log.d("LIFECYCLE_F2", "newInstance()"); }
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (debugLifecycle) { Log.d("LIFECYCLE_F2", "onCreate"); }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (debugLifecycle) { Log.d("LIFECYCLE_F2", "onCreateView(...)"); }
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_fragment2, container, false);

        EventController EC = new EventController(getContext());
        ArrayList<EventOutput> resultEvents =  EC.getAllSavedEvents();
        data = new Fragment2ListViewAdapter(getContext(), resultEvents);
        ListView listView = rootView.findViewById(R.id.listView_Items);
        listView.setAdapter(data);

        // refresh button
        ImageButton btnRefresh = rootView.findViewById(R.id.f2_btn_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventController EC = new EventController(getContext());
                ArrayList<EventOutput> resultEvents =  EC.getAllSavedEvents();
                data = new Fragment2ListViewAdapter(getContext(), resultEvents);
                ListView listView = rootView.findViewById(R.id.listView_Items);
                listView.setAdapter(data);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        if (debugLifecycle) { Log.d("LIFECYCLE_F2", "onStart"); }
        super.onStart();
    }

    @Override
    public void onResume() {
        if (debugLifecycle) { Log.d("LIFECYCLE_F2", "onResume"); }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (debugLifecycle) { Log.d("LIFECYCLE_F2", "onPause"); }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (debugLifecycle) { Log.d("LIFECYCLE_F2", "onStop"); }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (debugLifecycle) { Log.d("LIFECYCLE_F2", "onDestroy"); }
        super.onDestroy();
    }

}
