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

public class ClassWorkoutAdapter extends ArrayAdapter<ClassWorkoutDataModel> implements View.OnClickListener {

    private ArrayList<ClassWorkoutDataModel> dataSet;
    Context mctx;

    private static class ViewHolder {
        TextView classWorkout;
        TextView workoutDate;
    }

    public ClassWorkoutAdapter(ArrayList<ClassWorkoutDataModel> data, Context context) {
        super(context, R.layout.workout_row_item, data);
        this.dataSet = data;
        this.mctx = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        ClassWorkoutDataModel workoutDataModel = (ClassWorkoutDataModel) object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClassWorkoutDataModel workoutDataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.workout_row_item, parent, false);
            viewHolder.classWorkout = (TextView) convertView.findViewById(R.id.workoutDescription);
            viewHolder.workoutDate = (TextView) convertView.findViewById(R.id.ClassDate);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mctx, (position > lastPosition)
                ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.classWorkout.setText(workoutDataModel.getClassDescription());
        // Return the completed view to render on screen
        return convertView;
    }
}
