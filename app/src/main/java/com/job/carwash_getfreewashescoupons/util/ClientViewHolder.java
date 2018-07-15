package com.job.carwash_getfreewashescoupons.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.job.carwash_getfreewashescoupons.R;
import com.job.carwash_getfreewashescoupons.appExecutors.DefaultExecutorSupplier;
import com.job.carwash_getfreewashescoupons.datasource.CustomerExtra;
import com.job.carwash_getfreewashescoupons.datasource.CustomerInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.cell_name)
    TextView cellName;
    @BindView(R.id.cell_rating_stars)
    MaterialRatingBar cellRatingStars;
    @BindView(R.id.cell_rating_nums)
    TextView cellRatingNums;
    @BindView(R.id.cell_date)
    TextView cellDate;
    @BindView(R.id.cell_year)
    TextView cellYear;
    @BindView(R.id.cell_visits)
    TextView cellVisits;
    @BindView(R.id.cell_type_vehicle)
    TextView cellTypeVehicle;
    @BindView(R.id.cell_cost)
    TextView cellCost;
    @BindView(R.id.cell_vehicle_reg)
    TextView cellVehicleReg;
    @BindView(R.id.cell_coupon)
    TextView cellCoupon;
    @BindView(R.id.cell_add_btn)
    TextView cellAddBtn;
    @BindView(R.id.cell_info)
    TextView cellInfo;

    private Context mContext;
    private FirebaseFirestore mFirestore;
    private Rating rating;

    private String CUSTOMEREXTRACOL = "CustomerExtra";

    public ClientViewHolder(@NonNull View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        //LayoutInflater.from(mContext).inflate(R.layout.cell_expand, null);
        //LayoutInflater.from(mContext).inflate(R.layout.item_collapse, null);
    }

    public void init(Context mContext, FirebaseFirestore mFirestore) {
        this.mFirestore = mFirestore;
        this.mContext = mContext;
        rating = new Rating();
    }

    public void setUpUiSmall(CustomerInfo customerInfo) {

        itemColName.setText(customerInfo.getFirstname() + " " + customerInfo.getLastname());
        itemColPhonenum.setText(customerInfo.getPhonenumber());
        itemColPrice.setText(rating.getPrice(customerInfo.getVehicletype()));

        cellName.setText(customerInfo.getFirstname() + " " + customerInfo.getLastname());
        cellTypeVehicle.setText(customerInfo.getVehicletype());
        cellVehicleReg.setText(customerInfo.getVehiclereg());

    }


    public void setUpUiExpanded(String customerId) {


        mFirestore.collection(CUSTOMEREXTRACOL).document(customerId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<DocumentSnapshot> dbtask) {

                        if (dbtask.isSuccessful()) {

                            DefaultExecutorSupplier.getInstance().forMainThreadTasks()
                                    .execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            // do some Main Thread work here.

                                            CustomerExtra customerExtra = dbtask.getResult().toObject(CustomerExtra.class);

                                            if (customerExtra != null) {

                                                cellVisits.setText(customerExtra.getVisits());
                                                cellCoupon.setText(customerExtra.getCoupons());
                                            }

                                        }
                                    });
                        }
                    }
                });
    }

    @OnClick(R.id.cell_add_btn)
    public void onViewClicked() {
    }
}
