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
        void onDeleteClick(int position);
    }
    public void removeItem(int position) {
        BODAlist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, BODAlist.size());
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
        BODAList currentItem = BODAlist.get(bodaholder.getAdapterPosition());
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

        bodaholder.BODdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    int position = bodaholder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onDeleteClick(position);
                    }
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
        public Button BODdelete;

        public MyViewHolderBODA(@NonNull View itemView) {
            super(itemView);

            BODAID = itemView.findViewById(R.id.BODAID);
            BODAName = itemView.findViewById(R.id.BODAName);
            BODAPosition = itemView.findViewById(R.id.BODAPosition);
            cardView = itemView.findViewById(R.id.BODAvotecard);
            BODdelete = itemView.findViewById(R.id.BODDelete);
            itemView.setOnClickListener(this);

            BODdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        mListener.onDeleteClick(position);
                    }
                }
            });
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
