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

public class AdapterCand extends RecyclerView.Adapter<AdapterCand.MyViewHolderc> {

    private final List<CandidatesList> itemscand; //items array list



    private final Context contextcand; //context


    //constructor for myitems and context
    public AdapterCand(List<CandidatesList> itemscand, Context contextcand) {
        this.itemscand = itemscand;
        this.contextcand = contextcand;
    }

    @NonNull
    @Override
    public AdapterCand.MyViewHolderc onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderc(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_cand, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCand.MyViewHolderc holdercand, int position) {

        //getting single item / user details from list
        CandidatesList candidatesList = itemscand.get(position);

        //setting user details to textviews
        holdercand.candID.setText(candidatesList.getCandnumber());
        holdercand.candFName.setText(candidatesList.getCandname());
        holdercand.candPosition.setText(candidatesList.getCandposition());
    }

    @Override
    public int getItemCount() {
        return itemscand.size();
    }

    //get that red off. This is the MyViewHolder class to hold view item for every item in the RecyclerView

    static class MyViewHolderc extends RecyclerView.ViewHolder {

        //declaring the textviews
        private final TextView candID, candFName, candPosition;

        public MyViewHolderc(@NonNull View itemView1) {
            super(itemView1);

            //getting TextViews from recycler_adapter_layout.xml

            candID = itemView1.findViewById(R.id.CandID);
            candFName = itemView1.findViewById(R.id.CandFName);
            candPosition = itemView1.findViewById(R.id.CandPosition);
        }
    }
}
