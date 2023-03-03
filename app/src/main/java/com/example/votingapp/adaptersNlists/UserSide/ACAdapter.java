package com.example.votingapp.adaptersNlists.UserSide;

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

import java.util.List;

public class ACAdapter extends RecyclerView.Adapter<ACAdapter.MyViewHolderAC> {

    private List<ACList> AClist; //items array list
    private Context ACcontext; //context
    private OnItemClickListener mListener;
    private OnVoteClickListener mVoteListener;

    private boolean oneMoreVoteAllowed;




    public interface OnItemClickListener {
        void onItemClick(ACList item);
    }

    public interface OnVoteClickListener {
        void onVoteClick(int position);
    }

    public void setOnVoteClickListener(OnVoteClickListener listener) {
        mVoteListener = listener;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ACAdapter(List<ACList> AClist, Context ACcontext, OnVoteClickListener listener, boolean oneMoreVoteAllowed) {
        this.AClist = AClist;
        this.ACcontext = ACcontext;
        this.mListener = mListener;
        this.mVoteListener = listener;
        this.oneMoreVoteAllowed = oneMoreVoteAllowed;
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
    public MyViewHolderAC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderAC(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_acvotepage, parent, false), oneMoreVoteAllowed);
    }

    @Override
    public void onBindViewHolder(@NonNull ACAdapter.MyViewHolderAC acholder, int position) {
        ACList currentItem = AClist.get(position);
        acholder.ACName.setText(currentItem.getACName());
        acholder.ACPosition.setText(currentItem.getACPosition());
        acholder.ACID.setText(currentItem.getACMembership()+"");

        // Update the enabled property of the vote button based on oneMoreVoteAllowed
        boolean isVoteEnabled = oneMoreVoteAllowed || currentItem.getACVotes() == 0;
        acholder.ACVotebutton.setEnabled(isVoteEnabled);

        // Set the click listener on the vote button
        acholder.ACVotebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = acholder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && mVoteListener != null) {
                    mVoteListener.onVoteClick(adapterPosition);
                }
            }
        });



        // Set the click listener on the card view
        acholder.itemView.setOnClickListener(new View.OnClickListener() {
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
        return AClist.size();
    }

    public class MyViewHolderAC extends RecyclerView.ViewHolder implements View.OnClickListener {


        private final TextView ACID, ACName, ACPosition;
        public CardView cardView;
        public Button ACVotebutton;
        private boolean oneMoreVoteAllowed;

        public MyViewHolderAC(@NonNull View itemView, boolean oneMoreVoteAllowed) {
            super(itemView);
            ACID = itemView.findViewById(R.id.ACID);
            ACName = itemView.findViewById(R.id.ACName);
            ACPosition = itemView.findViewById(R.id.ACPosition);
            cardView = itemView.findViewById(R.id.ACvotecard);
            ACVotebutton = itemView.findViewById(R.id.ACVotebutton);
            // Update the enabled state of the vote button
            ACVotebutton.setEnabled(oneMoreVoteAllowed);

            itemView.setOnClickListener(this);

            // Store the oneMoreVoteAllowed flag as an instance variable
            this.oneMoreVoteAllowed = oneMoreVoteAllowed;
        }
        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ACList clickedItem = AClist.get(position);
                mListener.onItemClick(clickedItem);
            }
        }
    }
}