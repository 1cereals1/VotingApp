package com.example.votingapp.adaptersNlists.AdminSide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.adaptersNlists.UserSide.ECList;

import java.util.List;

public class ECAAdapter extends RecyclerView.Adapter<ECAAdapter.MyViewHolderECA> {

    private final List<ECAList> ECAitems; //items array list
    private final Context ECAcontext; //context

    public ECAAdapter(List<ECAList> ECAitems, Context ECAcontext) {
        this.ECAitems = ECAitems;
        this.ECAcontext = ECAcontext;
    }

    @NonNull
    @Override
    public ECAAdapter.MyViewHolderECA onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderECA(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_ecacandidates, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ECAAdapter.MyViewHolderECA ecaholder, int position) {

        ECAList ECAlist = ECAitems.get(position);

        ecaholder.ECAName.setText(ECAlist.getECAName());
        ecaholder.ECAID.setText(ECAlist.getECAMembership()+"");
    }

    @Override
    public int getItemCount() {
        return ECAitems.size();
    }

    static class MyViewHolderECA extends RecyclerView.ViewHolder {

        private final TextView ECAID, ECAName;
        public MyViewHolderECA(@NonNull View itemView) {
            super(itemView);

            ECAID = itemView.findViewById(R.id.ECAID);
            ECAName = itemView.findViewById(R.id.ECAName);

        }
    }
}
