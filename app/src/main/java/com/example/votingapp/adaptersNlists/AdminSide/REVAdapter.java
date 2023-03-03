package com.example.votingapp.adaptersNlists.AdminSide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;

import java.util.List;

public class REVAdapter extends RecyclerView.Adapter<REVAdapter.MyViewHolder> {

    private final List<REVList> items; //items array list
    private final Context context; //context


    // constructor
    public REVAdapter(List<REVList> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public REVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate( R.layout.review_recycler_adapter, null));
    }

    @Override
    public void onBindViewHolder(@NonNull REVAdapter.MyViewHolder holder, int position) {

        //getting single / user details form list
        REVList revList = items.get(position);

        //setting user details to TextViews
        holder.name.setText(revList.getname());
        holder.position.setText(revList.getposition());
        holder.id.setText(revList.getid());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //MyViewHolder class to hold view reference for every item in the RecyclerView
    // we will learn more about this in RecyclerView tutorials series
    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView position;
        private TextView id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            ///getting TextViews from recyclerview_adpater_layout.xml file
            name = itemView.findViewById(R.id.RevNAME);
            position = itemView.findViewById(R.id.RevPOSITION);
            id = itemView.findViewById(R.id.RevID);

        }
    }
}
