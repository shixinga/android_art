package com.shixing.rxjava2learn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
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
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Flowable和Observable的对比：
 *      Flowable由于为了实现响应式拉取而做了很多操作，所以性能上低于Observable
 *      Flowable有响应式拉取的功能，所以可以防止水缸爆满（即防止oom）
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fun1();
//        fun2();
//        fun3();
//        fun4(); //上游用子线程发送事件，下游用ui线程接收事件
//        fun5(); //上游被调用多个线程注册时，只会绑定第一个线程。而下游被调用多个线程时，每调用一次下游就切换一次线程
//        fun6(); //map 事件转化
//        fun7(); //flatMap concatMap ,flatmap不管上下游是不是同一个线程都是无序的(//例子中的Observerable.delay()是通过ScheduleExecutorService来实现定时，而不是通过handler.sendMessageDelay()来实现延时)，而concatMap不管上下游是否是同一个线程都是有序的
//        fun8(); //zip 被观察者合并 ，虽然某个被观察者无限发送事件，但是只要有一个被观察者发送了onComplete(),则不会oom！！即内存不会增大
//        fun9(); //zip 被观察者合并
//        fun10(); //observer1的水缸被爆了
//        fun101()和fun102() 都是通过减少发送放入水缸事件来减少水缸里面的事件数量， 缺点是丢失了大部分的事件，而fun103() 从速度上进行治理, 减缓事件发送进水缸的速度
//        fun101();  //通过过滤器来解决fun10()的水缸爆满问题，本质是通过减少进入水缸的事件数量的确可以缓解上下游流速不均衡的问题
//        fun102();  //通过每隔2秒就取出一个事件来解决fun10()的水缸爆满问题，本质是通过减少进入水缸的事件数量的确可以缓解上下游流速不均衡的问题
//        fun103(); //上游通过降低放入水缸的事件速度（每2秒放入一个事件）来解决fun10()的水缸爆满问题，本质就是通过降低发送速度来解决
//        fun11(); //flowable  上下游在同一个线程
//        fun12(); //flowable  上下游不在同一个线程，但是下游一个事件都没有收到，即onNext()没有被执行,但是onSubscribe()被执行，如果在但是onSubscribe()添加s.request(2); 表示能接收上游的2个事件
//        fun13(); //flowable 上下游不在同一个线程，上游使劲的发送事件，下游的request(Integer.MAX_VALUE)     onError: io.reactivex.exceptions.MissingBackpressureException: create: could not emit value due to lack of requests

//        fun14(); //flowable 上下游不在同一个线程， 用到按钮. 没点击按钮之前，因为创建Flowable传的是BackpressureStrategy.ERROR时，水缸默认大小为128（即水缸能存128个事件），上游事件能执行完，但下游的onNext()不会被执行！ 点击一次，下游的onNext()就会被执行一次，说明下游能够接收事件.
//        fun15(); //BackpressureStrategy.DROP 、BUFFER 、 ERROR 、LATEST
//        fun16(); //上游是怎么知道下游能接收多少个事件的.1.当上下游在同一个线程时，下游必须设置水缸的容量，if不设置，水缸容量为0。2.当上下游不在同一个线程时，不管下游是否设置水缸的容量，上游打印水缸的容量都是128，即水缸容量是128
//        fun17(); //
    }

    public void request17Onclick(View vew) {
        mSubscription.request(100); //必须是96或以上下游才能上游的emitter.requested()才为96！！！
//        mSubscription.request(20);
    }
    private void fun17() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                        Log.d(TAG, "First requested = " + emitter.requested());
                        boolean flag;
                        for (int i = 0; ; i++) {
                            flag = false;
                            while (emitter.requested() == 0) {
                                if (!flag) {
                                    Log.d(TAG, "Oh no! I can't emit value!");
                                    flag = true;
                                }
                            }
                            emitter.onNext(i);
                            Log.d(TAG, "emit " + i + " , requested = " + emitter.requested());
                        }
                    }
                }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }

    private void fun16() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                        Log.d(TAG, "current requested: " + emitter.requested());
                        emitter.onNext(11);
                        Log.d(TAG, "current requested: " + emitter.requested());
                        emitter.onNext(22);
                        Log.d(TAG, "current requested: " + emitter.requested());
                        emitter.onComplete(); //调用完emitter.onComplete()后，下面的onNext()事件没有传到下游里， emitter.requested()不变！！！
                        Log.d(TAG, "current requested: " + emitter.requested());
                        emitter.onNext(33);
                        Log.d(TAG, "current requested: " + emitter.requested());

                    }
                }, BackpressureStrategy.ERROR)
                //下面两行代码（让上下游不运行在同一线程）才能让下游的默认接收事件的数量为128个，即上游的emitter.requested()返回128。注释掉意味着下游的默认接收事件为0，此时if上游发事件则下游的onError()会被调用
                //如果下游mSubscription.request(1000)，上游的emitter.requested()还是会返回128！！！因为每一个线程都有一个requested !!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
//                        mSubscription.request(8);  //上游的emitter.requested()是8
//                        mSubscription.request(100); //上游的emitter.requested()是108， 就是一个简单的加法，减法在上游做，上游每发送一个onNext(),emitter.requested()就减1
                        mSubscription.request(1000); //上游的emitter.requested()是1000的前提是上下游必须运行在同一线程中！！！！if上下游不在同一线程中，则上游的emitter.requested()是128！！！
//                        mSubscription.request(1); //不管上下游是否在同一个线程，下游都只会接收一个事件
                    }
                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }
                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void fun15() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
                }
            }
//        }, BackpressureStrategy.DROP).subscribeOn(Schedulers.io())
        }, BackpressureStrategy.LATEST).subscribeOn(Schedulers.io()) // 相比于drop， Latest总是能获取到最后最新的事件
//        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()) // 由于上游一直发送事件，所以导致水缸爆满，导致没有可用内存
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }

    private void fun14() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
//                Log.d(TAG, "emit 1");
//                emitter.onNext(1);
//                Log.d(TAG, "emit 2");
//                emitter.onNext(2);
//                Log.d(TAG, "emit 3");
//                emitter.onNext(3);
//                Log.d(TAG, "emit complete");
//                emitter.onComplete();

                for (int i = 0; i < 128; i++) { //改成129会抛 io.reactivex.exceptions.MissingBackpressureException: create: could not emit value due to lack of requests. 此时ERROR替换为BackpressureStrategy.BUFFER就不会抛异常，因为水缸容量变为无穷
                    Log.d(TAG, "emit " + i);
                    emitter.onNext(i);
                }

            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()) //水缸默认大小是128
//        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()) //水缸默认大小是无穷
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;  //把Subscription保存起来
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    public void request14Onclick(View view) {
        mSubscription.request(1);
    }
    public void request15Onclick(View view) {
        mSubscription.request(128);
    }
    private void fun13() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                int i = 0;
                while(true) {
                    e.onNext(i++);
//                    Thread.sleep(1000);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        //可以把request()当作下游处理事件的能力
                        s.request(Integer.MAX_VALUE); //这里的onNext()会被调用3次，然后onComplete()被调用。因为上游就发送了3个事件
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError: " + t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void fun12() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();

            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        //可以把request()当作下游处理事件的能力
                        s.request(2); //onNext()会被调用2次，即能接收上游的2个事件
//                        s.request(200); //这里的onNext()会被调用3次，然后onComplete()被调用。因为上游就发送了3个事件
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError: " + t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void fun103() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
                    Thread.sleep(2000);  //每次发送完事件延时2秒
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "" + integer);
                    }
                });
    }

    private void fun102() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())
                .sample(2, TimeUnit.SECONDS)  //sample取样
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "" + integer);
                    }
                });

    }

    private void fun101() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 100 == 0;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "" + integer);
                    }
                });

    }

    private void fun11() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2); //执行到这一行代码时下游抛了io.reactivex.exceptions.MissingBackpressureException: create: could not emit value due to lack of requests
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();

            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);  //注意这句代码,会抛backpresureeException，因为上下游处于同一个线程，上游发了3个，而下游只拉取一个
//                        s.request(Long.MAX_VALUE);  //注意这句代码
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void fun10() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {   //无限循环发事件
                    emitter.onNext(i);
                    Log.d(TAG, "subscribe: " + i);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                ;

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
//                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                ;

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.w(TAG, throwable);
            }
        });

    }

    private void fun9() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Log.d(TAG, "subscribe: send : 1" + ", " + Thread.currentThread().getName());
                Thread.sleep(1000);

                e.onNext(2);
                Log.d(TAG, "subscribe: send : 2" + ", " + Thread.currentThread().getName());
                Thread.sleep(1000);

                e.onNext(3);
                Log.d(TAG, "subscribe: send : 3" + ", " + Thread.currentThread().getName());
                Thread.sleep(1000);

                e.onNext(4);
                Log.d(TAG, "subscribe: send : 4" + ", " + Thread.currentThread().getName());
                Thread.sleep(1000);

                e.onComplete();
                Log.d(TAG, "subscribe: complet");
//                Thread.sleep(1000);
            }
        }).subscribeOn(Schedulers.io());
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");
                Log.d(TAG, "subscribe: A" + ", " + Thread.currentThread().getName());
                Thread.sleep(1000);

                Log.d(TAG, "subscribe: B" + ", " + Thread.currentThread().getName());
                e.onNext("B");
                Thread.sleep(1000);

                e.onNext("C");
                Log.d(TAG, "subscribe: C" + ", " + Thread.currentThread().getName());
                Thread.sleep(1000);

                e.onComplete();
                Log.d(TAG, "subscribe: complete");
//                Thread.sleep(1000);
            }
        }).subscribeOn(Schedulers.io());
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                Log.d(TAG, "apply: " +Thread.currentThread().getName());
                return integer + ":" + s;
            }
        })
                //没有下面这句话意味着observer的onNext()是在zip()所属的线程运行
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: " + value + "," + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }
    private void fun8() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Log.d(TAG, "subscribe: send : 1");
                e.onNext(2);
                Log.d(TAG, "subscribe: send : 2");
                e.onNext(3);
                Log.d(TAG, "subscribe: send : 3");
                e.onNext(4);
                Log.d(TAG, "subscribe: send : 4");
                e.onComplete();
                Log.d(TAG, "subscribe: complet");
            }
        });
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");
                Log.d(TAG, "subscribe: A");
                Log.d(TAG, "subscribe: B");
                e.onNext("B");
                e.onNext("C");
                Log.d(TAG, "subscribe: C");
                e.onComplete();
                Log.d(TAG, "subscribe: complete");
            }
        });
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + ":" + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    private void fun7() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() { //flatMap并不保证事件的顺序，
//        }).concatMap(new Function<Integer, ObservableSource<String>>() { //concatMap保证事件的顺序
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 4; ++i) {
                    list.add("even " + integer + ":" + i);
                }
                Log.d(TAG, "apply: " + Thread.currentThread().getName());
                if (integer == 2) {
                    return Observable.fromIterable(list).delay(100, TimeUnit.MILLISECONDS); //通过ScheduleExecutorService来实现定时，而不是通过handler.sendMessageDelay()来实现延时
                } else {
                    return Observable.fromIterable(list);
                }
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: " + s);
            }
        });
    }

    private void fun6() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                Log.d(TAG, "apply: 将integer 转为String并返回");
                return "结果是：" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: " + s);
            }
        });
    }

    private void fun5() {
        //例1
        //打印结果
//        08-18 08:50:40.538 13037-13076/com.shixing.rxjava2learn D/MainActivity: subscribe: RxNewThreadScheduler-1,send 1
//        08-18 08:50:40.576 13037-13037/com.shixing.rxjava2learn D/MainActivity: accept: main, receive 1
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                Log.d(TAG, "subscribe: " + Thread.currentThread().getName() + ",send 1");
//                e.onNext(1);
//            }
//        }).subscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "accept: " +Thread.currentThread().getName() +", receive 1");
//                    }
//                });

        //例2
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "subscribe: " + Thread.currentThread().getName() + ",send 1");
                e.onNext(1);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "doOnNext(io thread) accept: " + Thread.currentThread().getName() + ", receive 1");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                //会没输出，因为没有subscribe,所以在doOnNext()后面加上subscribe()就等价于下面的代码
              /*  .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "doonnext  222 accept: ");
                    }
                })*/
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "main thread accept: " +Thread.currentThread().getName() +", receive 1");
                    }
                });
    }

    private void fun4() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "subscribe: " + Thread.currentThread().getName() + ",send 1");
                Thread.sleep(2000);
                e.onNext(1);
            }
        })
//                .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Consumer<Integer>() {
              @Override
              public void accept(Integer integer) throws Exception {
                  Log.d(TAG, "accept: " +Thread.currentThread().getName() +", receive 1");
              }
          });
        Log.d(TAG, "mainaaa: " +Thread.currentThread().getName());
    }

    private void fun3() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
                Log.d(TAG, "emit 4");
                emitter.onNext(4);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "onNext: " + integer);
            }
        });

    }

    private void fun1() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "subscribe: " + e);
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
//                e.onComplete();
                e.onNext(4);
                e.onError(null);
                e.onNext(5);
            }
        });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: " + Thread.currentThread().getName() + ",disposable=" + d);
            }

            @Override

            public void onNext(Integer value) {
                Log.d(TAG, "onNext: " + Thread.currentThread().getName() + ",value=" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: " + Thread.currentThread().getName());
            }
        };
        observable
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    private void fun2() {
        //被观察者的创建方式3
        /*String[] arr = {"kk","LLL","aa","cccc"};
        Observable o3 = Observable.fromArray(arr);
        o3.subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });*/

        //被观察者的创建方式2
       /* Observable observable = Observable.just("haha", "csx");
        observable.subscribe(new Observer<String>(){

            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });*/
        //被观察者的创建方式1
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
                Log.d(TAG, "emit 4");
                emitter.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable mDisposable;
            private int i;

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribe disposable=" + d);
                mDisposable = d;
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext: " + value + ",mDisposable=" + mDisposable);
                i++;
                if (i == 2) {
                    Log.d(TAG, "dispose：" + ",mDisposable=" + mDisposable);
                    mDisposable.dispose();
                    Log.d(TAG, "isDisposed : " + mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error" + ",mDisposable=" + mDisposable);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete" + ",mDisposable=" + mDisposable);
            }
        });

    }
}
