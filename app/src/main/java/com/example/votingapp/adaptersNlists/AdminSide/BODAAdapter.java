package com.example.votingapp.adaptersNlists.AdminSide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.UserSideofThings.ACvotepage;
import com.example.votingapp.adaptersNlists.AdminSide.ACAList;

import java.util.List;

public class BODAAdapter extends RecyclerView.Adapter<BODAAdapter.MyViewHolderBODA> {

    private List<BODAList> BODAlist; //items array list
    private Context BODAcontext; //context
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(BODAList item);
    }

    public BODAAdapter(List<BODAList> BODAlist, Context BODAcontext) {
        this.BODAlist = BODAlist;
        this.BODAcontext = BODAcontext;
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
    public MyViewHolderBODA onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderBODA(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_bodacandidates, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BODAAdapter.MyViewHolderBODA bodaholder, int position) {
        BODAList currentItem = BODAlist.get(position);
        bodaholder.BODAName.setText(currentItem.getBODAName());
        bodaholder.BODAPosition.setText(currentItem.getBODAPosition());
        bodaholder.BODAID.setText(currentItem.getBODAMembership()+"");
        // Set the click listener on the card view
        bodaholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(currentItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return BODAlist.size();
    }

    public class MyViewHolderBODA extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView BODAID, BODAName, BODAPosition;
        public CardView cardView;

        public MyViewHolderBODA(@NonNull View itemView) {
            super(itemView);

            BODAID = itemView.findViewById(R.id.BODAID);
            BODAName = itemView.findViewById(R.id.BODAName);
            BODAPosition = itemView.findViewById(R.id.BODAPosition);
            cardView = itemView.findViewById(R.id.BODAvotecard);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                BODAList clickedItem = BODAlist.get(position);
                mListener.onItemClick(clickedItem);
            }
        }
    }
}
