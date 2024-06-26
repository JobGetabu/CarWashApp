package com.job.carwash_getfreewashescoupons.appExecutors;

/**
 * Created by Job on Tuesday : 4/24/2018.
 */
public class UsingTheThreadPoolSample {

    /*
     * Using it for MainThread Tasks
     */
    public void doSomeMainThreadWork(){
        DefaultExecutorSupplier.getInstance().forMainThreadTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // do some Main Thread work here.
                    }
                });
    }

}
