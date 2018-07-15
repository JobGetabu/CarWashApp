package com.job.carwash_getfreewashescoupons.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.job.carwash_getfreewashescoupons.R;
import com.job.carwash_getfreewashescoupons.datasource.CustomerInfo;
import com.job.carwash_getfreewashescoupons.util.ClientViewHolder;
import com.ramotion.foldingcell.FoldingCell;

/**
 * Created by Job on Monday : 7/16/2018.
 */
public class ClientAdapter extends FirestoreAdapter<ClientViewHolder> {

    private Context mContext;
    private FirebaseFirestore mFirestore;

    public static final String TAG = "ClientAdapter";

    public ClientAdapter(Query query,Context mContext,FirebaseFirestore mFirestore) {
        super(query);

        this.mContext = mContext;
        this.mFirestore = mFirestore;
    }

    public void initAdapter(Context mContext,FirebaseFirestore mFirestore){
        this.mContext = mContext;
        this.mFirestore = mFirestore;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater vi = LayoutInflater.from(mContext);
        final FoldingCell cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);

        // attach click listener to folding cell
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cell.toggle(false);
            }
        });

        return new ClientViewHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {


        CustomerInfo model = getSnapshot(position).toObject(CustomerInfo.class);

        holder.init(mContext, mFirestore);
        holder.setUpUiSmall(model);
        holder.setUpUiExpanded(model.getCustomerId());

        Log.d(TAG, "onBindViewHolder: " + model.toString());
    }
}
