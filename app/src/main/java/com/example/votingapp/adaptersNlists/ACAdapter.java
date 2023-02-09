package com.example.votingapp.adaptersNlists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;

import java.util.List;

public class ACAdapter extends RecyclerView.Adapter<ACAdapter.MyViewHolderAC> {

    private final List<ACList> ACitems; //items array list



    private final Context ACcontext; //context


    //constructor for myitems and context
    public ACAdapter(List<ACList> ACitems, Context ACcontext) {
        this.ACitems = ACitems;
        this.ACcontext = ACcontext;
    }


    @NonNull
    @Override
    public ACAdapter.MyViewHolderAC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderAC(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_acvotepage, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ACAdapter.MyViewHolderAC acholder, int position) {

        //getting single item / user details from list
        ACList acList = ACitems.get(position);

        //setting user details to textviews
        acholder.ACID.setText(acList.getACMembership());
        acholder.ACName.setText(acList.getACName());
    }

    @Override
    public int getItemCount() {
        return ACitems.size();
    }

    //get that red off. This is the MyViewHolder class to hold view item for every item in the RecyclerView

    static class MyViewHolderAC extends RecyclerView.ViewHolder {

        //declaring the textviews
        private final TextView ACID, ACName;

        public MyViewHolderAC(@NonNull View itemViewAC) {
            super(itemViewAC);

            //getting TextViews from recycler_adapter_layout_BOD.xml

            ACID = itemViewAC.findViewById(R.id.ACID);
            ACName = itemViewAC.findViewById(R.id.ACName);
        }
    }
}
