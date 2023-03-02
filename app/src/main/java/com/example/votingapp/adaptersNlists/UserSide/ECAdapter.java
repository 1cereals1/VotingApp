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

public class ECAdapter extends RecyclerView.Adapter<ECAdapter.MyViewHolderEC> {

    private List<ECList> EClist; //items array list
    private Context ECcontext; //context
    private OnItemClickListener mListener;



    public interface OnItemClickListener {
        void onItemClick(ECList item);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ECAdapter(List<ECList> EClist, Context ECcontext) {
        this.EClist = EClist;
        this.ECcontext = ECcontext;
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
    public MyViewHolderEC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderEC(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_ecvotepage, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ECAdapter.MyViewHolderEC echolder, int position) {
        ECList currentItem = EClist.get(position);
        echolder.ECName.setText(currentItem.getECName());
        echolder.ECID.setText(currentItem.getECMembership()+"");
        // Set the click listener on the card view
        echolder.itemView.setOnClickListener(new View.OnClickListener() {
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
        return EClist.size();
    }

    public class MyViewHolderEC extends RecyclerView.ViewHolder implements View.OnClickListener {


        private final TextView ECID, ECName;
        public CardView cardView;

        public MyViewHolderEC(@NonNull View itemView) {
            super(itemView);

            ECID = itemView.findViewById(R.id.ECID);
            ECName = itemView.findViewById(R.id.ECName);
            cardView = itemView.findViewById(R.id.ECvotecard);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ECList clickedItem = EClist.get(position);
                mListener.onItemClick(clickedItem);
            }
        }
    }
}