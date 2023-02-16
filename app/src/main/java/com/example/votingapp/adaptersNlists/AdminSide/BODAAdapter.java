package com.example.votingapp.adaptersNlists.AdminSide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.adaptersNlists.UserSide.BODList;

import java.util.List;

public class BODAAdapter extends RecyclerView.Adapter<BODAAdapter.MyViewHolderBODA> {

    private final List<BODAList> BODAitems;
    private final Context contextBODA;

    public BODAAdapter(List<BODAList> BODAitems, Context contextBODA) {
        this.BODAitems = BODAitems;
        this.contextBODA = contextBODA;
    }

    @NonNull
    @Override
    public BODAAdapter.MyViewHolderBODA onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderBODA(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_bodacandidates, null));
    }

    @Override
    public void onBindViewHolder(@NonNull BODAAdapter.MyViewHolderBODA bodaholder, int position) {

        BODAList BODAlist = BODAitems.get(position);

        bodaholder.BODAName.setText(BODAlist.getBODAName());
        bodaholder.BODAID.setText(BODAlist.getBODAMembership()+"");
    }

    @Override
    public int getItemCount() {
        return BODAitems.size();
    }

    static class MyViewHolderBODA extends RecyclerView.ViewHolder {

        private final TextView BODAID, BODAName;
        public MyViewHolderBODA(@NonNull View itemView) {
            super(itemView);

            BODAID = itemView.findViewById(R.id.BODAID);
            BODAName = itemView.findViewById(R.id.BODAName);

        }
    }
}
