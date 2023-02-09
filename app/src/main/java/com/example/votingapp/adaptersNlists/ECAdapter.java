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

public class ECAdapter extends RecyclerView.Adapter<ECAdapter.MyViewHolderEC> {

    private final List<ECList> ECitems; //items array list



    private final Context ECcontext; //context


    //constructor for myitems and context
    public ECAdapter(List<ECList> ECitems, Context ECcontext) {
        this.ECitems = ECitems;
        this.ECcontext = ECcontext;
    }


    @NonNull
    @Override
    public ECAdapter.MyViewHolderEC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderEC(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_ecvotepage, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ECAdapter.MyViewHolderEC echolder, int position) {

        //getting single item / user details from list
        ECList ecList = ECitems.get(position);

        //setting user details to textviews
        echolder.ECID.setText(ecList.getECMembership());
        echolder.ECName.setText(ecList.getECName());
    }

    @Override
    public int getItemCount() {
        return ECitems.size();
    }

    //get that red off. This is the MyViewHolder class to hold view item for every item in the RecyclerView

    static class MyViewHolderEC extends RecyclerView.ViewHolder {

        //declaring the textviews
        private final TextView ECID, ECName;

        public MyViewHolderEC(@NonNull View itemViewEC) {
            super(itemViewEC);

            //getting TextViews from recycler_adapter_layout_BOD.xml

            ECID = itemViewEC.findViewById(R.id.ECID);
            ECName = itemViewEC.findViewById(R.id.ECName);
        }
    }
}
