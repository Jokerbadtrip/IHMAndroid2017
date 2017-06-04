package fr.unice.polytech.ihmandroid.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.unice.polytech.ihmandroid.R;
import fr.unice.polytech.ihmandroid.model.Event;

/**
 * Created by MSI on 03/06/2017.
 */

public class EventAdapter extends ArrayAdapter {


    private List<Event> events;

    private TextView name, description, date;


    public EventAdapter(@NonNull Context context, @NonNull List<Event> objects) {
        super(context, 0, objects);
        this.events = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.event_tile_fragment, null);
        } else {
            view = convertView;
        }

        name = (TextView) view.findViewById(R.id.event_name);
        description = (TextView) view.findViewById(R.id.event_description);
        date = (TextView) view.findViewById(R.id.event_date);

        name.setText(getItem(position).getName());
        description.setText(getItem(position).getDescription());
        //date.setText(getItem(position).getDate());

        return view;

    }


    @Override
    public Event getItem(int position) {
        return events.get(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }


}