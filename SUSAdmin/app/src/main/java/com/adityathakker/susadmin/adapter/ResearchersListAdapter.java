package com.adityathakker.susadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adityathakker.susadmin.R;
import com.adityathakker.susadmin.model.ResearcherPojo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aditya Thakker (Github: @adityathakker) on 6/10/16.
 */

public class ResearchersListAdapter extends RecyclerView.Adapter<ResearchersListAdapter.ResearchersListViewHolder> {
    public static final String TAG = ResearchersListAdapter.class.getSimpleName();
    private Context context;
    private OnItemClickListener clickListener;
    private ArrayList<ResearcherPojo> researcherPojoArrayList;

    public ResearchersListAdapter(Context context, ArrayList<ResearcherPojo> researcherPojoArrayList) {
        this.context = context;
        this.researcherPojoArrayList = researcherPojoArrayList;
    }

    @Override
    public ResearchersListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.researchers_list_row, parent, false);
        ResearchersListViewHolder viewHolder = new ResearchersListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ResearchersListViewHolder holder, int position) {
        ResearcherPojo researcherPojo = researcherPojoArrayList.get(position);
        holder.name.setText(researcherPojo.getFirstname() + " " + researcherPojo.getLastname());
        holder.email.setText(researcherPojo.getEmail());
        holder.workshopName.setText(researcherPojo.getWorkshopName());
    }

    @Override
    public int getItemCount() {
        return researcherPojoArrayList!=null?researcherPojoArrayList.size():0;
    }

    class ResearchersListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.researchers_list_row_name)
        TextView name;
        @BindView(R.id.researchers_list_row_email)
        TextView email;
        @BindView(R.id.researchers_list_row_workshop)
        TextView workshopName;
        public ResearchersListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition(), researcherPojoArrayList.get(getAdapterPosition()));
        }
    }

    //Click Listener Interface
    public interface OnItemClickListener {
        public void onItemClick(View view, int position, ResearcherPojo current);
    }

    //Setting up the click listener
    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
