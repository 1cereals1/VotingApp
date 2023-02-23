package com.example.votingapp.adaptersNlists.UserSide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.votingapp.UserSideofThings.Review;
import com.example.votingapp.adaptersNlists.AdminSide.ACAAdapter;
import com.example.votingapp.adaptersNlists.AdminSide.ACAList;

import java.security.AccessController;
import java.util.List;

public class ACAdapter extends RecyclerView.Adapter<ACAdapter.MyViewHolderAC> {

    private List<ACList> ACitems; //items array list
    private Context ACcontext; //context
    private ACAdapter.OnItemClickListener mListener;

    public ACAdapter(List<ACList> ACitems, Context ACcontext) {
        this.ACitems = ACitems;
        this.ACcontext = ACcontext;
    }

    public interface OnItemClickListener {
        void onItemClick(ACList data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private int selectedPosition = RecyclerView.NO_POSITION;

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    @NonNull
    @Override
    public ACAdapter.MyViewHolderAC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderAC(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_acvotepage, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ACAdapter.MyViewHolderAC acholder, int position) {

        ACList AClist = ACitems.get(position);
        ACList currentItem = ACitems.get(position);

        acholder.ACName.setText(AClist.getACName());
        acholder.ACID.setText(AClist.getACMembership()+"");

        ACList data = ACitems.get(position);
        acholder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(data);
                }
            }
        });
        acholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oldSelectedPosition = getSelectedPosition();
                setSelectedPosition(acholder.getAdapterPosition());
                notifyItemChanged(oldSelectedPosition);
                notifyItemChanged(getSelectedPosition());
            }
        });

        if (getSelectedPosition() == position) {
            acholder.itemView.setBackgroundColor(Color.BLUE);
        } else {
            acholder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return ACitems.size();
    }

    static class MyViewHolderAC extends RecyclerView.ViewHolder {

        private final TextView ACID, ACName;
        private final Button mButton;
        public MyViewHolderAC(@NonNull View itemView) {
            super(itemView);

            ACID = itemView.findViewById(R.id.ACID);
            ACName = itemView.findViewById(R.id.ACName);
            mButton = itemView.findViewById(R.id.btn_edit);


        }
    }
}
