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

public class ECAAdapter extends RecyclerView.Adapter<ECAAdapter.MyViewHolderECA> {

    private List<ECAList> ECAlist; //items array list
    private Context ECAcontext; //context
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(ECAList item);
    }

    public ECAAdapter(List<ECAList> ECAlist, Context ECAcontext) {
        this.ECAlist = ECAlist;
        this.ECAcontext = ECAcontext;
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
    public MyViewHolderECA onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderECA(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_ecacandidates, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ECAAdapter.MyViewHolderECA ecaholder, int position) {
        ECAList currentItem = ECAlist.get(position);
        ecaholder.ECAName.setText(currentItem.getECAName());
        ecaholder.ECAPosition.setText(currentItem.getECAPosition());
        ecaholder.ECAID.setText(currentItem.getECAMembership()+"");
        // Set the click listener on the card view
        ecaholder.itemView.setOnClickListener(new View.OnClickListener() {
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
        return ECAlist.size();
    }

    public class MyViewHolderECA extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView ECAID, ECAName, ECAPosition;
        public CardView cardView;

        public MyViewHolderECA(@NonNull View itemView) {
            super(itemView);

            ECAID = itemView.findViewById(R.id.ECAID);
            ECAName = itemView.findViewById(R.id.ECAName);
            ECAPosition = itemView.findViewById(R.id.ECAPosition);
            cardView = itemView.findViewById(R.id.ECAvotecard);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ECAList clickedItem = ECAlist.get(position);
                mListener.onItemClick(clickedItem);
            }
        }
    }
}
