package com.example.bujo.pilotproject;

/**
 * Created by Neha on 27/3/2017.
 */

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private String[] titles = {"Personal Details","Contact Details","Professional Details"};

    private String[] details = {".",".","."};

    private int[] images = {
            R.drawable.family_details,
            R.drawable.contact_details,
            R.drawable.professional_details};

    private int[] images1 = {

            R.drawable.family_details,
            R.drawable.injection,
            R.drawable.family_details};

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;
        public ImageView backgroundImage;


        public ViewHolder(View itemView) {
            super(itemView);

           // backgroundImage = (ImageView)itemView.findViewById(R.id.background_image);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail =(TextView)itemView.findViewById(R.id.item_detail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    Snackbar.make(v, "Click detected on item " + position,Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    if(position==0){
                        v.getContext().startActivity(new Intent(v.getContext(),ViewBiodata.class));
                    }
                    if(position==1){
                        v.getContext().startActivity(new Intent(v.getContext(),FamilyDetailsForm.class));
                    }
                    if(position==2){
                        v.getContext().startActivity(new Intent(v.getContext(),Physical_CheckupForm.class));
                    }
                    if(position==3){
                        v.getContext().startActivity(new Intent(v.getContext(),UploadDocumentsForm.class));
                    }

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemDetail.setText(details[i]);
        viewHolder.itemImage.setImageResource(images[i]);
        //viewHolder.background_image.setImageResource(images1[i]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}