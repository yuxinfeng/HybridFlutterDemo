package com.hybirdflutter.business;

import android.content.Context;
import android.widget.Toast;

import io.reactivex.observers.DisposableObserver;

/**
 * author：yuxinfeng on 2019-04-24 20:01
 * email：yuxinfeng@corp.netease.com
 */
public class ProgressDialogSubcribe<T> extends DisposableObserver<T>  implements ProgressCannelListener{

    private DisposibleObserverOnNextListener mSubscriberOnNextListener;
    private Context context;
    private ProgressDialogHandler progressDialogHandler;

    public ProgressDialogSubcribe(DisposibleObserverOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        progressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog(){
        if (progressDialogHandler != null) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (progressDialogHandler != null) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            progressDialogHandler = null;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        dismissProgressDialog();
    }

    @Override
    public void onComplete() {
        Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
        dismissProgressDialog();
    }

    @Override
    public void onCancelProgress() {
        if (!isDisposed()) {
            dispose();
            dismissProgressDialog();
        }
    }
}
