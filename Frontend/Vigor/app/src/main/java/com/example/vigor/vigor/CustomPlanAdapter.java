package com.example.vigor.vigor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

class PlanDataModel {

    String PlanName;
    public Boolean isChecked;

    //Constructor for object
    public PlanDataModel(String PlanName, boolean isChecked) {
        super();
        this.PlanName = PlanName;
        this.isChecked = isChecked;
    }

    //return methods for different aspects
    public String getPlanName() {
        return PlanName;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void toggle() {
        isChecked = !isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setSelected(boolean checked) {
        isChecked = checked;
    }
}

public class CustomPlanAdapter extends ArrayAdapter<PlanDataModel> {

    private List<PlanDataModel> planList;
    private Context context;

    public CustomPlanAdapter(List<PlanDataModel> planList, Context context) {
        super(context, R.layout.plan_row_item, planList);
        this.planList = planList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (planList != null)
            return planList.size();
        else
            return 0;
    }

    @Override
    public PlanDataModel getItem(int position) {
        return planList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean isChecked(int position) {
        return planList.get(position).isChecked;
    }

    public class PlanHolder {
        public CheckBox active;
        public TextView planName;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        PlanHolder holder = new PlanHolder();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.plan_row_item, null);

        holder.planName = (TextView) v.findViewById(R.id.PlanTextView);
        holder.active = (CheckBox) v.findViewById(R.id.PlanCheckBox);
        holder.active.setOnCheckedChangeListener((PlanManagerActivity) context);

        holder.planName.setText(planList.get(position).getPlanName());
        holder.active.setChecked(planList.get(position).getIsChecked());
        holder.active.setTag(planList.get(position));

        return v;
    }
}