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



    public interface OnItemClickListener {
        void onItemClick(ACList item);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ACAdapter(List<ACList> AClist, Context ACcontext) {
        this.AClist = AClist;
        this.ACcontext = ACcontext;
        this.mListener = mListener;
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
        return new MyViewHolderAC(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_acvotepage, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ACAdapter.MyViewHolderAC acholder, int position) {
        ACList currentItem = AClist.get(position);
        acholder.ACName.setText(currentItem.getACName());
        acholder.ACID.setText(currentItem.getACMembership()+"");
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


        private final TextView ACID, ACName;
        public CardView cardView;

        public MyViewHolderAC(@NonNull View itemView) {
            super(itemView);

            ACID = itemView.findViewById(R.id.ACID);
            ACName = itemView.findViewById(R.id.ACName);
            cardView = itemView.findViewById(R.id.ACvotecard);
            itemView.setOnClickListener(this);
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