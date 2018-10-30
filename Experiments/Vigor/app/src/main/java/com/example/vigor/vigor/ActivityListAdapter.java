package com.example.vigor.vigor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class ActivityListAdapter extends ArrayAdapter<ActivityStore>{
    private static final String Tag = "ActivityListAdapter";
    private Context mContext;
    int mResource;

    public ActivityListAdapter(Context context, int resource, ArrayList<ActivityStore> objects) {
        super(context, resource, objects);
        mContext = mContext;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String Activity = getItem(position).getActivity();
        int Amount= getItem(position).getAmount();
        String AssignedBy = getItem(position).getAssignedBy();

        ActivityStore activityStore = new ActivityStore(Activity, Amount, AssignedBy);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvActivity = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvAssignedBy = (TextView) convertView.findViewById(R.id.textView3);

        tvActivity.setText(Activity);
        tvAmount.setText(Amount);
        tvAssignedBy.setText(AssignedBy);

        return convertView;
    }
}
