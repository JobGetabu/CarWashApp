package com.job.carwash_getfreewashescoupons.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.job.carwash_getfreewashescoupons.R;

/**
 * Created by Job on Sunday : 7/15/2018.
 */
public class ClientViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;

    public ClientViewHolder(@NonNull View itemView) {
        super(itemView);

        //LayoutInflater.from(mContext).inflate(R.layout.cell_expand,null);
        LayoutInflater.from(mContext).inflate(R.layout.item_collapse,null);
    }
}
