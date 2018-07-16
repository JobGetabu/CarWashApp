package com.job.carwash_getfreewashescoupons.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.job.carwash_getfreewashescoupons.R;
import com.job.carwash_getfreewashescoupons.appExecutors.DefaultExecutorSupplier;
import com.job.carwash_getfreewashescoupons.datasource.CustomerExtra;
import com.job.carwash_getfreewashescoupons.datasource.CustomerInfo;
import com.job.carwash_getfreewashescoupons.ui.AddWashActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.job.carwash_getfreewashescoupons.ui.AddWashActivity.CUSTOMERIDEXTRA;
import static com.job.carwash_getfreewashescoupons.ui.AddWashActivity.CUSTOMERNAMEEXTRA;
import static com.job.carwash_getfreewashescoupons.ui.AddWashActivity.CUSTOMERPHONEEXTRA;

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

    private static final String TAG = "ClientVH";

    private String CUSTOMEREXTRACOL = "CustomerExtra";
    private String WASHCOL = "Wash";

    private String cusID = "";
    private String cusPhone = "";
    private String cusName = "";

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
        itemColPrice.setText(rating.getPriceMock(customerInfo.getVehicletype()));
        itemColImage.setImageDrawable(rating.getImage(mContext, customerInfo.getVehicletype()));

        cellName.setText(customerInfo.getFirstname() + " " + customerInfo.getLastname());
        cellTypeVehicle.setText(customerInfo.getVehicletype());
        cellVehicleReg.setText(customerInfo.getVehiclereg());
        cellCost.setText(rating.getPrice(customerInfo.getVehicletype()));

        cusID = customerInfo.getCustomerId();
        cusPhone = customerInfo.getPhonenumber();
        cusName = customerInfo.getFirstname() + " " + customerInfo.getLastname();
    }


    public void setUpUiExpanded(final String customerId) {

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


                                                cellVisits.setText(String.valueOf(new Double(customerExtra.getVisits()).intValue()));
                                                cellCoupon.setText(String.valueOf(new Double(customerExtra.getCoupons()).intValue()));
                                                cellRatingNums.setText("(" + customerExtra.getVisits() + ")");
                                                itemColRating.setStepSize(0.5f);
                                                cellRatingStars.setStepSize(0.5f);
                                                itemColRating.setRating(rating.setRating(customerExtra.getVisits()));
                                                cellRatingStars.setRating(rating.setRating(customerExtra.getVisits()));

                                                setWashData(customerId);
                                            }

                                        }
                                    });
                        }
                    }
                });
    }

    private void setWashData(String customerId) {
        mFirestore.collection(WASHCOL)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .whereEqualTo("customerid", customerId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            DefaultExecutorSupplier.getInstance().forMainThreadTasks()
                                    .execute(new Runnable() {
                                        @Override
                                        public void run() {

                                            List<DocumentSnapshot> washdocs = task.getResult().getDocuments();

                                            if (washdocs.size() != 0){
                                                Log.d(TAG, "run: wash list 0 => " + washdocs.get(0));
                                                dealTime(washdocs.get(0).getTimestamp("timestamp"));
                                            }else {
                                                cellDate.setText("- -");
                                                cellYear.setText(" - ");
                                            }
                                        }
                                    });
                        } else {
                            Log.e(TAG, "error ", task.getException());

                        }
                    }
                });
    }

    private void dealTime(Timestamp timestamp) {
        //Timestamp timestamp = model.getTimestamp();
        if (timestamp != null) {

            Date date = timestamp.toDate();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat yearFormat = new SimpleDateFormat("yyyy");


            String day = rating.getDay(c.get(Calendar.DAY_OF_WEEK));
            int daydate = c.get(Calendar.DAY_OF_MONTH);

            cellDate.setText(day + " " + daydate);
            cellYear.setText(yearFormat.format(date));

        }
    }

    @OnClick(R.id.cell_add_btn)
    public void onViewClicked() {

        Intent intent = new Intent(mContext, AddWashActivity.class);
        intent.putExtra(CUSTOMERIDEXTRA, cusID);
        intent.putExtra(CUSTOMERNAMEEXTRA, cusName);
        intent.putExtra(CUSTOMERPHONEEXTRA, cusPhone);
        mContext.startActivity(intent);
    }


}
