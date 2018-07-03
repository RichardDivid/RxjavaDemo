package com.dongnao.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "dongnao";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Observable.interval(1, TimeUnit.SECONDS).take(5).subscribe(new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Long aLong) {
//                Log.i("dongnao", "onNext: "+aLong);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i("dongnao", "onComplete: ");
//            }
//        });
//  0 1   2  3  4
//        Observable.intervalRange(0,5,5,100,TimeUnit.MILLISECONDS)
//                .skipWhile(new Predicate<Long>() {
//                    @Override
//                    public boolean test(Long aLong) throws Exception {
//                        return aLong<3;
//                    }
//                }).subscribe(new Consumer<Long>() {
//            @Override
//            public void accept(Long aLong) throws Exception {
//                System.out.println(aLong);
//            }
//        });
//  并行执行  时间顺序
//        Flowable.merge(
//                Flowable.intervalRange(0,4,1,1,TimeUnit.SECONDS),
//                Flowable.intervalRange(4,4,1,1,TimeUnit.SECONDS))
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Log.i(TAG, "accept: "+aLong);
//                    }
//                });
//
        Flowable.mergeDelayError(Flowable.create(new FlowableOnSubscribe<Publisher<?>>() {
            @Override
            public void subscribe(FlowableEmitter<Publisher<?>> e) throws Exception {
                    e.onError(new NullPointerException());
            }
        },BackpressureStrategy.BUFFER)
                ,Flowable.intervalRange(3,3,1,2,TimeUnit.SECONDS))
                .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG, "accept: "+o);
            }
        });
    }
}
