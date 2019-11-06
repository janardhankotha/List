package com.android.list.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.list.R;

public class ViewHolder_List extends RecyclerView.ViewHolder{


    public TextView texttitle, textdesc;
    public ImageView imgprofile;


    public ViewHolder_List(View itemView) {

        super(itemView);


        texttitle = (TextView)itemView.findViewById(R.id.texttitle);
        textdesc = (TextView)itemView.findViewById(R.id.textdesc);
        imgprofile = (ImageView)itemView.findViewById(R.id.imgprofile);


    }



}
