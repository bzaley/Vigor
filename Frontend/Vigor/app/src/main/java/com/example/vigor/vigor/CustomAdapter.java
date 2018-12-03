package com.example.vigor.vigor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author Adrian Hamill
 * This is the custom adapter made for the listview in ToDoListActivity.java.
 * This creates row items based on the row_item.xml, and will be called to
 * when different elements are changed.
 */
public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener {

    private ArrayList<DataModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtActivity;
        TextView txtSets;
        TextView txtReps;
    }

    /**
     * The Constructor for this adapter
     * @param data
     * @param context
     */
    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        DataModel dataModel = (DataModel) object;
    }

    private int lastPosition = -1;

    /**
     * This is the method that actually updates the view holder with new data when called
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtActivity = (TextView) convertView.findViewById(R.id.Activity);
            viewHolder.txtSets = (TextView) convertView.findViewById(R.id.sets);
            viewHolder.txtReps = (TextView) convertView.findViewById(R.id.reps);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtActivity.setText(dataModel.getexercise());
        viewHolder.txtSets.setText(dataModel.getsets());
        viewHolder.txtReps.setText(dataModel.getreps());
        // Return the completed view to render on screen
        return convertView;
    }
}