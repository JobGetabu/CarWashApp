package com.job.carwash_getfreewashescoupons.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.job.carwash_getfreewashescoupons.R;
import com.job.carwash_getfreewashescoupons.datasource.CustomerInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by Job on Sunday : 7/15/2018.
 */
public class ClientViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_col_image)
    ImageView itemColImage;
    @BindView(R.id.item_col_name)
    TextView itemColName;
    @BindView(R.id.item_col_rating)
    MaterialRatingBar itemColRating;
    @BindView(R.id.item_col_num_ratings)
    TextView itemColNumRatings;
    @BindView(R.id.item_col_phonenum)
    TextView itemColPhonenum;
    @BindView(R.id.item_col_price)
    TextView itemColPrice;

    private Context mContext;
    private FirebaseFirestore mFirestore;
    private Rating rating;

    public ClientViewHolder(@NonNull View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);
        //LayoutInflater.from(mContext).inflate(R.layout.cell_expand,null);
        //LayoutInflater.from(mContext).inflate(R.layout.item_collapse, null);
    }

    public void init(Context mContext,FirebaseFirestore mFirestore){
        this.mFirestore = mFirestore;
        this.mContext = mContext;
        rating = new Rating();
    }

    public void setUpUiSmall(CustomerInfo customerInfo){

        itemColName.setText(customerInfo.getFirstname()+" "+customerInfo.getLastname());
        itemColPhonenum.setText(customerInfo.getPhonenumber());
        itemColPrice.setText(rating.getPrice(customerInfo.getVehicletype()));

    }

    public void setUpUiExpanded(String customerId){

    }
}
