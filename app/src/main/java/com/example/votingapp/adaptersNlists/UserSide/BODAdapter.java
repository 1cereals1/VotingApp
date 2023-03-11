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

public class BODAdapter extends RecyclerView.Adapter<BODAdapter.MyViewHolderBOD> {

    private List<BODList> BODlist; //items array list

    private Context BODcontext; //context
    private OnItemClickListener mListener;
    private OnVoteClickListener mVoteListener;
    private List<Integer> votedCandidateIds = new ArrayList<>();

    private boolean oneMoreVoteAllowed;
    private int numVotesRemaining;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://online-voting-ma-default-rtdb.firebaseio.com/");
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private RecyclerView BODrv;

    public void setRecyclerView(RecyclerView BODrv) {
        this.BODrv = BODrv;
    }

    private boolean isCandidateVotedByUser(int position) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String userId = user.getUid();

        BODList candidate = BODlist.get(position);
        return candidate.getVotedBy().contains(userId);
    }

    public void disableVoteButtonForCandidate(int position) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String userId = user.getUid();

        BODList candidate = BODlist.get(position);
        if (candidate.getVotedBy().contains(userId)) {
            // User has already voted for this candidate, disable the vote button
            candidate.setVoteButtonEnabled(false);
            notifyItemChanged(position);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(BODList item);
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



    public BODAdapter(List<BODList> BODlist, Context BODcontext, OnVoteClickListener listener, boolean oneMoreVoteAllowed, int numVotesRemaining) {
        this.BODlist = BODlist;
        this.BODcontext = BODcontext;
        this.mListener = mListener;
        this.mVoteListener = listener;
        this.oneMoreVoteAllowed = oneMoreVoteAllowed;
        this.numVotesRemaining = numVotesRemaining;

    }




    @NonNull
    @Override
    public MyViewHolderBOD onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderBOD(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_bodvotepage, parent, false), oneMoreVoteAllowed);
    }

    @Override
    public void onBindViewHolder(@NonNull BODAdapter.MyViewHolderBOD bodholder, int position) {
        BODList currentItem = BODlist.get(position);
        bodholder.BODName.setText(currentItem.getBODName());
        bodholder.BODPosition.setText(currentItem.getBODPosition());
        bodholder.BODID.setText(currentItem.getBODMembership()+"");




        // Set the click listener on the vote button
        bodholder.BODVotebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = bodholder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && mVoteListener != null) {
                    // Check if the user has already voted for the candidate
                    if (!isCandidateVotedByUser(adapterPosition)) {
                        // Notify the listener that the user has clicked the vote button
                        mVoteListener.onVoteClick(adapterPosition);

                        // Disable the vote button for this candidate
                        votedCandidateIds.add(adapterPosition);
                        BODList candidate = BODlist.get(adapterPosition);
                        candidate.setVoteButtonEnabled(false);
                        notifyItemChanged(adapterPosition);
                    }
                }
            }
        });


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


        private final TextView BODID, BODName, BODPosition;
        public CardView cardView;
        public Button BODVotebutton;
        private boolean oneMoreVoteAllowed;


        public MyViewHolderBOD(@NonNull View itemView, boolean oneMoreVoteAllowed) {
            super(itemView);
            BODID = itemView.findViewById(R.id.BODID);
            BODName = itemView.findViewById(R.id.BODName);
            BODPosition = itemView.findViewById(R.id.BODPosition);
            cardView = itemView.findViewById(R.id.BODvotecard);
            BODVotebutton = itemView.findViewById(R.id.BODVotebutton);
            if (votedCandidateIds.contains(getBindingAdapterPosition())) {
                BODVotebutton.setEnabled(false);
            } else {
                BODVotebutton.setEnabled(oneMoreVoteAllowed);
            }

            // Update the enabled state of the vote button
            BODVotebutton.setEnabled(oneMoreVoteAllowed);

            itemView.setOnClickListener(this);




            // Store the oneMoreVoteAllowed flag as an instance variable
            this.oneMoreVoteAllowed = oneMoreVoteAllowed;
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