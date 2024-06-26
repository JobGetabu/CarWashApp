package com.job.carwash_getfreewashescoupons.ui;


import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.job.carwash_getfreewashescoupons.R;
import com.job.carwash_getfreewashescoupons.datasource.CustomerInfo;
import com.job.carwash_getfreewashescoupons.util.ClientViewHolder;
import com.job.carwash_getfreewashescoupons.util.Filter;
import com.job.carwash_getfreewashescoupons.viewmodel.FilterViewModel;
import com.ramotion.foldingcell.FoldingCell;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements FilterDialogFragment.FilterListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab_button)
    FloatingActionButton fabButton;
    @BindView(R.id.main_clientlist)
    RecyclerView mainClientlist;
    @BindView(R.id.view_empty)
    LinearLayout viewEmpty;
    @BindView(R.id.text_current_search)
    TextView textCurrentSearch;
    @BindView(R.id.text_current_sort_by)
    TextView textCurrentSortBy;
    @BindView(R.id.button_clear_filter)
    ImageView buttonClearFilter;


    private static final String CUSTOMERINFOCOL = "CustomerInfo";
    private static final String TAG = "MainPage";

    private FilterDialogFragment mFilterDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleSignInClient mGoogleSignInClient;

    private FirestoreRecyclerAdapter adapter;

    private FilterViewModel mViewModel;
    private Query mQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);


        //firebase
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();


        initList();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // Sign in logic here.
                    finish();
                    sendToLogin();
                }else {


                    String userId = mAuth.getCurrentUser().getUid();

                    // Create a reference to the clients collection
                    final CollectionReference clientRef = mFirestore.collection(CUSTOMERINFOCOL);
                    mQuery = clientRef
                            .whereEqualTo("ownerid", userId)
                            .orderBy("regdate", Query.Direction.DESCENDING);

                    setUpList();
                }
            }
        };

        mAuth.addAuthStateListener(mAuthListener);


        //login credentials
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // View model
        mViewModel = ViewModelProviders.of(this).get(FilterViewModel.class);

        // Filter Dialog
        mFilterDialog = new FilterDialogFragment();

        setUpUiObservers();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.main_logout:
                signOutGoogle();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab_button)
    public void onViewClicked() {
        Intent intent = new Intent(this, NewClientActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.filter_bar)
    public void onFilterClicked() {
        // Show the dialog containing filter options
        mFilterDialog.show(getSupportFragmentManager(), FilterDialogFragment.TAG);
    }

    @Override
    public void onStart() {
        super.onStart();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null) {
                    // do stuff
                    // Check if user is signed in (non-null) and update UI accordingly.
                    sendToLogin();
                }
            }
        };

        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    //auth state listener for live changes
    private void authListner() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // Sign in logic here.
                    finish();
                    sendToLogin();
                }
            }
        };
    }

    private void signOutGoogle() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mAuth.signOut();
                        sendToLogin();
                    }
                });
    }

    private void setUpList() {

        FirestoreRecyclerOptions<CustomerInfo> options = new FirestoreRecyclerOptions.Builder<CustomerInfo>()
                .setQuery(mQuery, CustomerInfo.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<CustomerInfo, ClientViewHolder>(options) {
            @NonNull
            @Override
            public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                LayoutInflater vi = LayoutInflater.from(MainActivity.this);
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
            public void onBindViewHolder(@NonNull ClientViewHolder holder, int position, @NonNull CustomerInfo model) {

                holder.init(MainActivity.this, mFirestore);
                holder.setUpUiSmall(model);
                holder.setUpUiExpanded(model.getCustomerId());

                Log.d(TAG, "onBindViewHolder: " + model.toString());

            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();

                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {
                    mainClientlist.setVisibility(View.GONE);
                    viewEmpty.setVisibility(View.VISIBLE);
                } else {
                    mainClientlist.setVisibility(View.VISIBLE);
                    viewEmpty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Snackbar.make(findViewById(android.R.id.content),
                        "Error: check logs for info.", Snackbar.LENGTH_LONG).show();

                Log.d(TAG, "onError: ", e);
            }

            @Override
            public void startListening() {
                super.startListening();

                Log.d(TAG, "startListening: started ");
            }


        };

        adapter.startListening();
        adapter.notifyDataSetChanged();
        mainClientlist.setAdapter(adapter);
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(this.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mainClientlist.setLayoutManager(linearLayoutManager);
        mainClientlist.setHasFixedSize(true);
    }

    @Override
    public void onFilter(Filter filters) {

        // Construct query basic query
        Query query = mFirestore.collection(CUSTOMERINFOCOL);

        query.whereEqualTo("ownerid", mAuth.getCurrentUser().getUid());


        // vehicle (equality filter)
        if (filters.hasVehicle()) {
            query = query.whereEqualTo("vehicletype", filters.getVehicle());
        }

        query.orderBy("regdate", filters.getDateDirection());

        // Update the query
        mQuery = query;
        setUpList();


        //save the filter
        mViewModel.setmFilters(filters);

    }

    private void setUpUiObservers() {
        MediatorLiveData<String> data1 = mViewModel.getVehicleMediatorLiveData();

        data1.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null) {

                    textCurrentSearch.setText(s);
                }
            }
        });

        MediatorLiveData<String> data = mViewModel.getDateMediatorLiveData();
        data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null) {

                    textCurrentSortBy.setText("sorted by "+s);
                }
            }
        });
    }

    private void searchQuery(){
        // Construct query basic query
        Query query = mFirestore.collection(CUSTOMERINFOCOL);

        query.whereEqualTo("ownerid", mAuth.getCurrentUser().getUid());


    }
}
