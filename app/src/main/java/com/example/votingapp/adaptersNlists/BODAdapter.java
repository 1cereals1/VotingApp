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

public class BODAdapter extends RecyclerView.Adapter<BODAdapter.MyViewHolderBOD> {

    private final List<BODList> BODitems; //items array list



    private final Context BODcontext; //context


    //constructor for myitems and context
    public BODAdapter(List<BODList> BODitems, Context BODcontext) {
        this.BODitems = BODitems;
        this.BODcontext = BODcontext;
    }


    @NonNull
    @Override
    public BODAdapter.MyViewHolderBOD onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderBOD(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout_bodvotepage, null));
    }

    @Override
    public void onBindViewHolder(@NonNull BODAdapter.MyViewHolderBOD bodholder, int position) {

        //getting single item / user details from list
        BODList bodList = BODitems.get(position);

        //setting user details to textviews
        bodholder.BODID.setText(bodList.getBODMembership()+"");
        bodholder.BODName.setText(bodList.getBODName());
    }

    @Override
    public int getItemCount() {
        return BODitems.size();
    }

    //get that red off. This is the MyViewHolder class to hold view item for every item in the RecyclerView

    static class MyViewHolderBOD extends RecyclerView.ViewHolder {

        //declaring the textviews
        private final TextView BODID, BODName;

        public MyViewHolderBOD(@NonNull View itemViewBOD) {
            super(itemViewBOD);

            //getting TextViews from recycler_adapter_layout_BOD.xml

            BODID = itemViewBOD.findViewById(R.id.BODID);
            BODName = itemViewBOD.findViewById(R.id.BODName);
        }
    }
}
