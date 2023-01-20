package com.example.votingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final List<MyItems> items; //items array list
    private final Context context; //context


    //constructor for myitems and context
    public MyAdapter(List<MyItems> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        //getting single item / user details from list
        MyItems myItems = items.get(position);

        //setting user details to textviews
        holder.fnames.setText(myItems.getFname());
        holder.idnumbers.setText(myItems.getIdnumber());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //get that red off. This is the MyViewHolder class to hold view item for every item in the RecyclerView

    static class MyViewHolder extends RecyclerView.ViewHolder {

        //declaring the textviews
        private final TextView fnames, idnumbers;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //getting TextViews from recycler_adapter_layout.xml
            fnames = itemView.findViewById(R.id.FullNameTV);
            idnumbers = itemView.findViewById(R.id.CandID);
        }
    }
}
