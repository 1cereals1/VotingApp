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

public class ACAAdapter extends RecyclerView.Adapter<ACAAdapter.MyViewHolderACA> {

    private List<ACAList> ACAlist; //items array list
    private Context ACAcontext; //context
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(ACAList item);
    }

    public ACAAdapter(List<ACAList> ACAlist, Context ACAcontext) {
        this.ACAlist = ACAlist;
        this.ACAcontext = ACAcontext;
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
    public MyViewHolderACA onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderACA(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_acacandidates, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ACAAdapter.MyViewHolderACA acaholder, int position) {
        ACAList currentItem = ACAlist.get(position);
        acaholder.ACAName.setText(currentItem.getACAName());
        acaholder.ACAPosition.setText(currentItem.getACAPosition());
        acaholder.ACAID.setText(currentItem.getACAMembership()+"");
        // Set the click listener on the card view
        acaholder.itemView.setOnClickListener(new View.OnClickListener() {
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
        return ACAlist.size();
    }

    public class MyViewHolderACA extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView ACAID, ACAName, ACAPosition;
        public CardView cardView;

        public MyViewHolderACA(@NonNull View itemView) {
            super(itemView);

            ACAID = itemView.findViewById(R.id.ACAID);
            ACAName = itemView.findViewById(R.id.ACAName);
            ACAPosition = itemView.findViewById(R.id.ACAPosition);
            cardView = itemView.findViewById(R.id.ACAvotecard);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ACAList clickedItem = ACAlist.get(position);
                mListener.onItemClick(clickedItem);
            }
        }
    }
}
