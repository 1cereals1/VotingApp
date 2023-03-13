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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ECAdapter extends RecyclerView.Adapter<ECAdapter.MyViewHolderEC> {

    private List<ECList> EClist; //items array list

    private Context ECcontext; //context
    private OnItemClickListener mListener;
    private OnVoteClickListener mVoteListener;
    private List<Integer> votedCandidateIds = new ArrayList<>();

    private boolean oneMoreVoteAllowed;
    private int numVotesRemaining;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private RecyclerView ECrv;

    public void setRecyclerView(RecyclerView ECrv) {
        this.ECrv = ECrv;
    }

    private boolean isCandidateVotedByUser(int position) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String userId = user.getUid();

        ECList candidate = EClist.get(position);
        return candidate.getVotedBy().contains(userId);
    }

    public void disableVoteButtonForCandidate(int position) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String userId = user.getUid();

        ECList candidate = EClist.get(position);
        if (candidate.getVotedBy().contains(userId)) {
            // User has already voted for this candidate, disable the vote button
            candidate.setVoteButtonEnabled(false);
            notifyItemChanged(position);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(ECList item);
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



    public ECAdapter(List<ECList> EClist, Context ECcontext, OnVoteClickListener listener, boolean oneMoreVoteAllowed, int numVotesRemaining) {
        this.EClist = EClist;
        this.ECcontext = ECcontext;
        this.mListener = mListener;
        this.mVoteListener = listener;
        this.oneMoreVoteAllowed = oneMoreVoteAllowed;
        this.numVotesRemaining = numVotesRemaining;

    }




    @NonNull
    @Override
    public MyViewHolderEC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderEC(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_ecvotepage, parent, false), oneMoreVoteAllowed);
    }

    @Override
    public void onBindViewHolder(@NonNull ECAdapter.MyViewHolderEC echolder, int position) {
        ECList currentItem = EClist.get(position);
        echolder.ECName.setText(currentItem.getECName());
        echolder.ECPosition.setText(currentItem.getECPosition());
        echolder.ECID.setText(currentItem.getECMembership()+"");




        // Set the click listener on the vote button
        echolder.ECVotebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = echolder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && mVoteListener != null) {
                    // Check if the user has already voted for the candidate
                    if (!isCandidateVotedByUser(adapterPosition)) {
                        // Notify the listener that the user has clicked the vote button
                        mVoteListener.onVoteClick(adapterPosition);

                        // Disable the vote button for this candidate
                        votedCandidateIds.add(adapterPosition);
                        ECList candidate = EClist.get(adapterPosition);
                        candidate.setVoteButtonEnabled(false);
                        notifyItemChanged(adapterPosition);
                    }
                }
            }
        });


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


        private final TextView ECID, ECName, ECPosition;
        public CardView cardView;
        public Button ECVotebutton;
        private boolean oneMoreVoteAllowed;


        public MyViewHolderEC(@NonNull View itemView, boolean oneMoreVoteAllowed) {
            super(itemView);
            ECID = itemView.findViewById(R.id.ECID);
            ECName = itemView.findViewById(R.id.ECName);
            ECPosition = itemView.findViewById(R.id.ECPosition);
            cardView = itemView.findViewById(R.id.ECvotecard);
            ECVotebutton = itemView.findViewById(R.id.ECVotebutton);
            if (votedCandidateIds.contains(getBindingAdapterPosition())) {
                ECVotebutton.setEnabled(false);
            } else {
                ECVotebutton.setEnabled(oneMoreVoteAllowed);
            }

            // Update the enabled state of the vote button
            ECVotebutton.setEnabled(oneMoreVoteAllowed);

            itemView.setOnClickListener(this);




            // Store the oneMoreVoteAllowed flag as an instance variable
            this.oneMoreVoteAllowed = oneMoreVoteAllowed;
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