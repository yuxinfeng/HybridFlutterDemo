package com.hybirdflutter.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hybirdflutter.business.DisposibleObserverOnNextListener;
import com.hybirdflutter.business.ProgressDialogSubcribe;
import com.hybirdflutter.flutterapp.R;
import com.hybirdflutter.model.MovieEntity;
import com.hybirdflutter.model.Subject;
import com.hybirdflutter.model.SubjectResult;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * author：yuxinfeng on 2019-04-24 15:16
 * email：yuxinfeng@corp.netease.com
 */
public class NetWorkActivity extends AppCompatActivity {

    private TextView resultText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_net);
        resultText = findViewById(R.id.tv_textViewnet);
        resultText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMoive();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    // 进行原始网络请求
    private void getMoive() {
//        String baseUrl = "https://api.douban.com/v2/movie/";
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//        MovieService movieService = retrofit.create(MovieService.class);

        // 原生调用
//        Call<MovieEntity> call = movieService.getTopMovie(0, 10);
//        call.enqueue(new Callback<MovieEntity>() {
//            @Override
//            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
//                resultText.setText(response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<MovieEntity> call, Throwable t) {
//                resultText.setText(t.getMessage());
//            }
//        });
        // RxJava调用
        final int i = 0;
        DisposableObserver<List<Subject>> disposableObserver = new DisposableObserver<List<Subject>>() {
            @Override
            protected void onStart() {
                super.onStart();
            }

            @Override
            public void onNext(List<Subject> movieEntity) {
                resultText.setText(movieEntity.toString());
                int a = getI(0);
                System.out.println( a + "\n");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

                Toast.makeText(NetWorkActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();

            }
        };
        DisposibleObserverOnNextListener<List<Subject>> listDisposibleObserverOnNextListener = new DisposibleObserverOnNextListener<List<Subject>>() {
            @Override
            public void onNext(List<Subject> subjects) {
                resultText.setText(subjects.toString());
            }
        };

        HttpMethods.getInstance().getTopMovie(new ProgressDialogSubcribe<>(listDisposibleObserverOnNextListener, NetWorkActivity.this), 0, 10);



    }
    int getI(int a) {
        return a++;
    }
}
