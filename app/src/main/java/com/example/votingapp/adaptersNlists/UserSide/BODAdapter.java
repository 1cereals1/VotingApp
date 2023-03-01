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

public class BODAdapter extends RecyclerView.Adapter<BODAdapter.MyViewHolderBOD> {

    private List<BODList> BODlist; //items array list
    private Context BODcontext; //context
    private OnItemClickListener mListener;



    public interface OnItemClickListener {
        void onItemClick(BODList item);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public BODAdapter(List<BODList> BODlist, Context BODcontext) {
        this.BODlist = BODlist;
        this.BODcontext = BODcontext;
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
    public MyViewHolderBOD onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderBOD(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_bodvotepage, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BODAdapter.MyViewHolderBOD bodholder, int position) {
        BODList currentItem = BODlist.get(position);
        bodholder.BODName.setText(currentItem.getBODName());
        bodholder.BODID.setText(currentItem.getBODMembership()+"");
        // Set the click listener on the card view
        bodholder.itemView.setOnClickListener(new View.OnClickListener() {
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
        return BODlist.size();
    }

    public class MyViewHolderBOD extends RecyclerView.ViewHolder implements View.OnClickListener {


        private final TextView BODID, BODName;
        public CardView cardView;

        public MyViewHolderBOD(@NonNull View itemView) {
            super(itemView);

            BODID = itemView.findViewById(R.id.BODID);
            BODName = itemView.findViewById(R.id.BODName);
            cardView = itemView.findViewById(R.id.BODvotecard);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                BODList clickedItem = BODlist.get(position);
                mListener.onItemClick(clickedItem);
            }
        }
    }
}