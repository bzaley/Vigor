package com.example.vigor.vigor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * @author Adrian H
 * This is the Class for a plan object we use to contain plans
 * when they are created and updated.
 */
class PlanDataModel {

    String PlanName;
    public Boolean isChecked;

    /**
     * Constructor for object
     * @param PlanName
     * @param isChecked
     */
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

/**
 * @author Adrian H
 * This is a Custom Adapter built for PlanManager.java. It builds and displays
 * plan items based on the class_row_item.xml, it will be called to create and update
 * the displays for those items.
 */
public class CustomPlanAdapter extends ArrayAdapter<PlanDataModel> {

    private List<PlanDataModel> planList;
    private Context context;

    /**
     * Constructor for the Adapter
     * @param planList
     * @param context
     */
    public CustomPlanAdapter(List<PlanDataModel> planList, Context context) {
        super(context, R.layout.plan_row_item, planList);
        this.planList = planList;
        this.context = context;
    }

    /**
     *
     * @return
     */
    @Override
    public int getCount() {
        if (planList != null)
            return planList.size();
        else
            return 0;
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public PlanDataModel getItem(int position) {
        return planList.get(position);
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *
     * @param position
     * @return
     */
    public boolean isChecked(int position) {
        return planList.get(position).isChecked;
    }

    /**
     * Constructor for the holder object used in getView()
     */
    public class PlanHolder {
        public CheckBox active;
        public TextView planName;
    }

    /**
     * This updates and displays the information provided to it
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
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