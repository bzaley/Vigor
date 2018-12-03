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
 * @author Adrian H
 * This is a Custom Adapter built for the ClassTableActivity.java. It builds and displays
 * class items based on the class_row_item.xml, it will be called to create and update
 * the displays for those items.
 */
public class CustomClassAdapter extends ArrayAdapter<ClassDataModel> implements View.OnClickListener {

    private ArrayList<ClassDataModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtClassName;
        TextView txtClassId;
        TextView txtClassDescription;
    }

    /**
     * The constructor for this adapter
     * @param data
     * @param context
     */
    public CustomClassAdapter(ArrayList<ClassDataModel> data, Context context) {
        super(context, R.layout.class_row_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        ClassDataModel classdataModel = (ClassDataModel) object;
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
        ClassDataModel classdataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.class_row_item, parent, false);
            viewHolder.txtClassName = (TextView) convertView.findViewById(R.id.Activity);
            viewHolder.txtClassId = (TextView) convertView.findViewById(R.id.ClassId);
            viewHolder.txtClassDescription = (TextView) convertView.findViewById(R.id.ClassDescription);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtClassName.setText(classdataModel.getClassName());
        viewHolder.txtClassId.setText(classdataModel.getClassId() + "");
        viewHolder.txtClassDescription.setText(classdataModel.getBillboard());
        // Return the completed view to render on screen
        return convertView;
    }
}