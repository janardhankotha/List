package com.android.list.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.list.Pojo.Pojo_List;
import com.android.list.R;
import com.android.list.ViewHolder.ViewHolder_List;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
public class Adapter_List extends RecyclerView.Adapter<ViewHolder_List> {

    private ArrayList<Pojo_List> mListFeeds;
    private Context mContext;
    private int mPreviousPosition = 0;

    public Adapter_List(Context context, ArrayList<Pojo_List> feedList){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;



    }



    @Override
    public ViewHolder_List onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null);
        ViewHolder_List rcv = new ViewHolder_List(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final ViewHolder_List holder, final int position) {
        final Pojo_List feederInfo = mListFeeds.get(position);




        if (feederInfo.getTitle() == null || feederInfo.getTitle().trim().length() == 0 || feederInfo.getTitle().trim().equals("null")){
            holder.texttitle.setText("");
        }else{
            holder.texttitle.setText(feederInfo.getTitle());
        }

        if (feederInfo.getDesc() == null || feederInfo.getDesc().trim().length() == 0 || feederInfo.getDesc().trim().equals("null")){
            holder.textdesc.setText("");
        }else{
            holder.textdesc.setText(feederInfo.getDesc());
        }


        if (feederInfo.getImage() == null || feederInfo.getImage().equals("0")||feederInfo.getImage().equals("")||feederInfo.getImage().equals("null")){
            holder.imgprofile.setImageResource(R.drawable.defaultimg);
        }else {
            Glide.with(mContext)
                    .load(Uri.parse(feederInfo.getImage()))
                    // .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //.skipMemoryCache(true)
                    .error(R.drawable.defaultimg)
                    .into(holder.imgprofile);

        }





        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }


}

