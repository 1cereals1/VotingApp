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

public class ECAdapter extends RecyclerView.Adapter<ECAdapter.MyViewHolderEC> {

    private final List<ECList> ECitems; //items array list
    private final Context ECcontext; //context

    public ECAdapter(List<ECList> ECitems, Context ECcontext) {
        this.ECitems = ECitems;
        this.ECcontext = ECcontext;
    }

    @NonNull
    @Override
    public ECAdapter.MyViewHolderEC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderEC(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_ecvotepage, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ECAdapter.MyViewHolderEC echolder, int position) {

        ECList EClist = ECitems.get(position);

        echolder.ECName.setText(EClist.getECName());
        echolder.ECID.setText(EClist.getECMembership()+"");
    }

    @Override
    public int getItemCount() {
        return ECitems.size();
    }

    static class MyViewHolderEC extends RecyclerView.ViewHolder {

        private final TextView ECID, ECName;
        public MyViewHolderEC(@NonNull View itemView) {
            super(itemView);

            ECID = itemView.findViewById(R.id.ECID);
            ECName = itemView.findViewById(R.id.ECName);

        }
    }
}
