package com.dongnao.rxjavademo;

import android.graphics.Bitmap;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.GroupedObservable;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
	//    @Test
    public void testCrteate() throws Exception {
//        基于观察者模式
//        被观察角色  顾客
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
//                    e.onNext("1234");
            }
        });
//        观察者  线程调度     泛型  老鸨
        Observer observer=new Observer<String>(){
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext  "+s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
//        订阅
//        observable.subscribe(observer);

//        -------------------------
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


        //---------------遍历文件     5个文件    枚举-------create 快捷创建操作--------------------
        Observable.just("1234","456","789").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
//                删除操作
                System.out.println(" just  onNext"+s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

//----just   (2个参数   )     fromArray  （多个参数） -
    @Test
    public void testFromArray() throws Exception {
        Observable.fromArray(new Integer[]{1,2,3,4}).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("  onNext  "+integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

//        //适合 调用某方法  执行完后 不需要返回参数，
// 比如 数据更新后， 需要从新渲染UI   回调onComplete  中 写invalidate();

        Observable.empty().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                System.out.println("onNext");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }
	
	
	
//    @Test
    public void addition_isCorrect() throws Exception {
//        Observable.create(null);
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i=0;i<1000000;i++) {
//                    1
                    e.onNext(i);
                }
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

//        Flowable ----> Observable 被观察者角色
    }

//    @Test
//    public void testFlowable() throws Exception {
//        Flowable.create(new FlowableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
//                for (int i=0;i<1000000000;i++) {
//                    e.onNext(i);
//                }
//            }
//        }, BackpressureStrategy.ERROR).subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onSubscribe(Subscription s) {
//                System.out.println("onSubscribe");
//                s.request(500);
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                System.out.println("处理  "+integer);
//                try {
//                    Thread.currentThread().sleep(5);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//    }
//


//    @Test
    public void testMap() throws Exception {
        Observable.just("headr.png","bit.png").map(new Function<String, Integer>() {
            @Override
            public Integer apply(String url) throws Exception {
//                ""进行网络请求

                return 1;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Integer bitmap) {
                System.out.println(bitmap);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
    //使用场景   当app  登录前必须先拿到 app的配置（1.0    9.0）  登录
//    @Test
    public void testFlatMap() throws Exception {
        Observable.just("getConfig","login" ).flatMap(new Function<String, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String s) throws Exception {
                return createResponce(s);
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                System.out.println(o);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

//    不谈线程

//比如   一堆书本中   分为语文书   和英语书
//    @Test
    public void testGroupBy() throws Exception {
        Observable.just(1,2,3,4).groupBy(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {

                return integer>2?"A组":"B组";
            }
        }).subscribe(new Consumer<GroupedObservable<String, Integer>>() {
            @Override
            public void accept(final GroupedObservable<String, Integer> stringIntegerGroupedObservable) throws Exception {

                    stringIntegerGroupedObservable.subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            String key = stringIntegerGroupedObservable.getKey();
                            System.out.println("key  "+key+integer);
                        }
                    });
            }
        });
    }
    //buffer操作符是把多个元素打包成一个元素一次过发送数据
//    适用场景   10000条数据插入到数据库中时  每一条数据产生都需要时间
// 如果产生一条 插入一条比较浪给时间，全部一次性插入用户等的太久
// 采取buffer的形式  将10000条  分成 一小段执行
//    @Test
    public void testBuffer() throws Exception {
        Observable.just(1,2,3,4,5,6).buffer(5).subscribe(new Observer<List<Integer>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Integer> integers) {
                System.out.println(""+integers);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }




//     9  + 10  个子文件     -----》大文件
    
//    比如多个文件合并成一个大文件，总是一段一段小文件向积累


//    @Test
    public void testScan() throws Exception {
        Observable.range(1,5).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer+integer2;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }






    private ObservableSource<?> createResponce(final  String s) {
//        4  5 回调地狱
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("登录 "+s);
            }
        });


    }




//    =========================过滤操作符===============================

    //一堆商品中选出已经过期的商品  其他的不处理
//    @Test
    public void testFilter() throws Exception {
        Observable.just(1,2,3,4,5,6).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer>2;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

//
//    rxjava  定时器 心跳协议


//    @Test
    public void testTake() throws Exception {
        Observable.interval(1, TimeUnit.SECONDS).take(5).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });



    }
    //如果想过滤掉重复的元素，可以使用distinct操作符：
//    @Test
    public void testDistinct() throws Exception {
        Observable.just(1,2,2,3,3,6).distinct().subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }


//    ====================条件操作符=====================
//    all


//    @Test
    public void testAll() throws Exception {
        Observable.just(8,5,3,4,5).all(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer>2;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                System.out.println(aBoolean);
            }
        });
    }

//
    //如果要判断被观察者是否包含某一个元素，可以使用contains操作符，


//    @Test
    public void testContainer() throws Exception {
        Observable.just(3,4,5,6).contains(9).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                System.out.println(aBoolean);
            }
        });
    }

//    @Test
    public void testAny() throws Exception {
        Observable.just(1,2,3).any(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer==3;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                System.out.println(aBoolean);
            }
        });
    }
//    isEmpty  是否有事件

//    defaultIfEmpty  如果被观察者不发送任何数据，则会发送默认数据，上述代码中发送的是“1”，故输出结果为“1”。


//skipWhile skipWhile操作符可以接受一个Predicate用于控制跳过开始一段数据，比如：


//    @Test
    public void testSkipWhile() throws Exception {
        Observable.intervalRange(0,5,0,100,TimeUnit.MILLISECONDS)
                .skipWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return aLong<2;
                    }
                }).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println(aLong);
            }
        });
    }

//    --------------合并操作符--------------------

//StartWith
//    @Test
    public void testStartWith() throws Exception {
        Observable.just(3,5,6)
                .startWith(Observable.just(7,8,9))
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });
    }
//    concat


//    @Test
    public void testConcat() throws Exception {
//        concat  最多四个
       Observable.concat(Observable.just(1,2,3),Observable.just(1,2,3),Observable.just(4,5,6))
               .subscribe(new Consumer<Integer>() {
           @Override
           public void accept(Integer integer) throws Exception {
               System.out.println(integer);
           }
       });
    }
//    merge


//    @Test
    public void testMerge() throws Exception {
        Observable observable1=Observable.just(1,2,3,4);

        Observable observable2=Observable.just(6,7,8,9);
        Observable observable3=Observable.just(0,0,0,0);

        Observable.merge(observable1,observable3, observable2).subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println(o);
            }
        });
    }

    @Test
    public void testZip() throws Exception {
        Observable.zip(Observable.just(1, 2, 3)//6  9
                , Observable.just(4, 5), new BiFunction<Integer, Integer, Object>() {
                    @Override
                    public Object apply(Integer integer, Integer integer2) throws Exception {
                        return integer+integer2;
                    }
                }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println(""+o);
            }
        });
//        Flowable.zip
//                (Flowable.just(1,2,3)
//                ,Flowable.just(7,8), new Function<Integer, Integer>() {
//                    @Override
//                    public Integer apply(Integer integer) throws Exception {
//                        return null;
//                    }
//                }).subscribe(new new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//
//            }
//        });

    }
}

