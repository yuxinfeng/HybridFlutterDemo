package com.hybirdflutter.network;

import com.hybirdflutter.model.Data;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * author：yuxinfeng on 2019-04-23 16:42
 * email：yuxinfeng@corp.netease.com
 */
public class RxJavaTest {
    
    public static void rxjavaMethod() {

        Flowable.create(new FlowableOnSubscribe<Data>() {
            @Override
            public void subscribe(FlowableEmitter<Data> e) throws Exception {
                Data data = new Data();
                Data data1 = new Data();
                data.setId(1);
                data1.setId(2);
                data.setName("yxf");
                data.setName("zx");
                e.onNext(data);
                e.onNext(data1);
                
            }
        }, BackpressureStrategy.BUFFER).subscribe(new Subscriber<Data>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(Data data) {
                System.out.println(data.getName());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
