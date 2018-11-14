package com.example.vigor.vigor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomPlanAdapter extends ArrayAdapter<PlanDataModel> implements View.OnClickListener {

    private ArrayList<PlanDataModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        CheckBox active;
    }

    public CustomPlanAdapter(ArrayList<PlanDataModel> data, Context context) {
        super(context, R.layout.plan_row_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        PlanDataModel plandataModel = (PlanDataModel) object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PlanDataModel classdataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.class_row_item, parent, false);
            viewHolder.active = (CheckBox) convertView.findViewById(R.id.PlanName);
//            viewHolder.active.setText(classdataModel.getPlanName());
//            viewHolder.active.setSelected(classdataModel.getIsChecked());

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.active.setText(classdataModel.getPlanName());
        viewHolder.active.setSelected(classdataModel.getIsChecked());
        // Return the completed view to render on screen
        return convertView;
    }
}