package com.example.votingapp.adaptersNlists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;

import java.util.List;

public class AdapterCand extends RecyclerView.Adapter<AdapterCand.MyViewHolder> {

    private final List<CandidatesList> itemscand; //items array list



    private final Context contextcand; //context


    //constructor for myitems and context
    public AdapterCand(List<CandidatesList> itemscand, Context contextcand) {
        this.itemscand = itemscand;
        this.contextcand = contextcand;
    }

    @NonNull
    @Override
    public AdapterCand.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_cand, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCand.MyViewHolder holdercand, int position) {

        //getting single item / user details from list
        CandidatesList candidatesList = itemscand.get(position);

        //setting user details to textviews

        holdercand.candID.setText(candidatesList.getCandname());
        holdercand.candFName.setText(candidatesList.getCandnumber());
        holdercand.candPosition.setText(candidatesList.getCandposition());
    }

    @Override
    public int getItemCount() {
        return itemscand.size();
    }

    //get that red off. This is the MyViewHolder class to hold view item for every item in the RecyclerView

    static class MyViewHolder extends RecyclerView.ViewHolder {

        //declaring the textviews
        private final TextView candID, candFName, candPosition;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //getting TextViews from recycler_adapter_layout.xml

            candID = itemView.findViewById(R.id.CandID);
            candFName = itemView.findViewById(R.id.CandFName);
            candPosition = itemView.findViewById(R.id.CandPosition);
        }
    }
}
