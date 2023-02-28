package com.example.votingapp.adaptersNlists.AdminSide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.adaptersNlists.UserSide.ACList;

import java.util.List;

public class ACAAdapter extends RecyclerView.Adapter<ACAAdapter.MyViewHolderACA> {

    private final List<ACAList> ACAitems; //items array list
    private final Context ACAcontext; //context




    private OnItemClickListener mListener;

    public ACAAdapter(List<ACAList> ACAitems, Context ACAcontext) {
        this.ACAitems = ACAitems;
        this.ACAcontext = ACAcontext;
    }

    public interface OnItemClickListener {
        void onItemClick(ACAList data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ACAAdapter.MyViewHolderACA onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderACA(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_acacandidates, null));
    }

    @Override
    public void onBindViewHolder( ACAAdapter.MyViewHolderACA acaholder, int position) {

        ACAList ACAlist = ACAitems.get(position);

        acaholder.ACAName.setText(ACAlist.getACAName());
        acaholder.ACAID.setText(ACAlist.getACAMembership()+"");



        ACAList data = ACAitems.get(position);
        acaholder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(data);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ACAitems.size();
    }

    static class MyViewHolderACA extends RecyclerView.ViewHolder {

        private final TextView ACAID, ACAName;
        private final Button mButton;

        public MyViewHolderACA(@NonNull View itemView) {
            super(itemView);

            ACAID = itemView.findViewById(R.id.ACAID);
            ACAName = itemView.findViewById(R.id.ACAName);
            mButton = itemView.findViewById(R.id.btn_edit);

        }
    }
}
