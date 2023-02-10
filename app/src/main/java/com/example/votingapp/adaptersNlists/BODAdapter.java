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

public class BODAdapter extends RecyclerView.Adapter<BODAdapter.MyViewHolderBOD> {

    private final List<BODList> BODitems;
    private final Context contextBOD;

    public BODAdapter(List<BODList> BODitems, Context contextBOD) {
        this.BODitems = BODitems;
        this.contextBOD = contextBOD;
    }

    @NonNull
    @Override
    public BODAdapter.MyViewHolderBOD onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderBOD(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_bodvotepage, null));
    }

    @Override
    public void onBindViewHolder(@NonNull BODAdapter.MyViewHolderBOD holder, int position) {

        BODList BODlist = BODitems.get(position);

        holder.BODName.setText(BODlist.getBODName());
        holder.BODID.setText(BODlist.getBODMembership()+"");
    }

    @Override
    public int getItemCount() {
        return BODitems.size();
    }

    static class MyViewHolderBOD extends RecyclerView.ViewHolder {

        private final TextView BODID, BODName;
        public MyViewHolderBOD(@NonNull View itemView) {
            super(itemView);

            BODID = itemView.findViewById(R.id.BODID);
            BODName = itemView.findViewById(R.id.BODName);

        }
    }
}
