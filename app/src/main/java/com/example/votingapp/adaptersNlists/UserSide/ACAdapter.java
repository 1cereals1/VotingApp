package com.example.votingapp.adaptersNlists.UserSide;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.AdminSideofThings.AdminsEmployees;
import com.example.votingapp.AdminSideofThings.Register;
import com.example.votingapp.R;

import java.util.List;

public class ACAdapter extends RecyclerView.Adapter<ACAdapter.MyViewHolderAC> {

    private final List<ACList> ACitems; //items array list
    private final Context ACcontext; //context

    public ACAdapter(List<ACList> ACitems, Context ACcontext) {
        this.ACitems = ACitems;
        this.ACcontext = ACcontext;
    }

    @NonNull
    @Override
    public ACAdapter.MyViewHolderAC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderAC(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_acvotepage, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ACAdapter.MyViewHolderAC acholder, int position) {

        ACList AClist = ACitems.get(position);

        acholder.ACName.setText(AClist.getACName());
        acholder.ACID.setText(AClist.getACMembership()+"");
    }

    @Override
    public int getItemCount() {
        return ACitems.size();
    }

    static class MyViewHolderAC extends RecyclerView.ViewHolder {

        private final TextView ACID, ACName;
        private final Button ACButton;
        public MyViewHolderAC(@NonNull View itemView) {
            super(itemView);

            ACID = itemView.findViewById(R.id.ACID);
            ACName = itemView.findViewById(R.id.ACName);
            ACButton = itemView.findViewById(R.id.btn_edit);

            ACButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
