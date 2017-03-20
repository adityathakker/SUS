package com.adityathakker.susadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adityathakker.susadmin.R;
import com.adityathakker.susadmin.model.WorkshopPojo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 5/10/16.
 */

public class WorkShopsListAdapter extends RecyclerView.Adapter<WorkShopsListAdapter.WorkShopsListViewHolder> {
    public static final String TAG = WorkShopsListAdapter.class.getSimpleName();
    private Context context;
    private OnItemClickListener clickListener;
    private ArrayList<WorkshopPojo> workshopPojoArrayList;

    public WorkShopsListAdapter(Context context, ArrayList<WorkshopPojo> workshopPojoArrayList) {
        this.context = context;
        this.workshopPojoArrayList = workshopPojoArrayList;
    }

    @Override
    public WorkShopsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workshops_list_row, parent, false);
        WorkShopsListViewHolder viewHolder = new WorkShopsListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WorkShopsListViewHolder holder, int position) {
        WorkshopPojo workshopPojo = workshopPojoArrayList.get(position);
        holder.workshopTitle.setText(workshopPojo.getName());
        holder.systemName.setText(workshopPojo.getSystemName());
    }

    @Override
    public int getItemCount() {
        return workshopPojoArrayList!=null?workshopPojoArrayList.size():0;
    }

    class WorkShopsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.workshops_list_row_workshop_title)
        TextView workshopTitle;
        @BindView(R.id.workshops_list_row_system_name)
        TextView systemName;

        public WorkShopsListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition(), workshopPojoArrayList.get(getAdapterPosition()));
        }
    }

    //Click Listener Interface
    public interface OnItemClickListener {
        public void onItemClick(View view, int position, WorkshopPojo workshopPojo);
    }

    //Setting up the click listener
    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
