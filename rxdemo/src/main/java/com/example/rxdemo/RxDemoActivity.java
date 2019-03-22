package com.example.rxdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class RxDemoActivity extends AppCompatActivity {

    private Disposable disposable;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_demo);
//        baseRxJava();
//        startSplash();
//        orderSendStr();
//        orderSendBytes();
        iv = findViewById(R.id.iv);

//        getBitmapByAddress();
        //Flowable 用于订阅 Subscriber ， 是支持背压（Backpressure）的
        flatMapDemo();
    }

    private void flatMapDemo() {
        //先----》然后什么
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                List<String> mdatas = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    mdatas.add(String.valueOf(i));
                }
                emitter.onNext(mdatas);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strList) throws Exception {
                        Logger.d("接受到数据   -->"+strList.size());
                    }
                })
                .flatMap(new Function<List<String>, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(final List<String> str) throws Exception {
                        final List<String> strList = new ArrayList<>();
                        for (String s : str) {
                            int anInt = Integer.parseInt(s);
                            if(anInt % 2 == 0){
                                strList.add(s);
                            }
                        }
                        if(!strList.isEmpty()){
                            return new ObservableSource<List<String>>() {
                                @Override
                                public void subscribe(Observer<? super List<String>> observer) {
                                    observer.onNext(strList);
                                }
                            };
                        }

                        return null;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> s) throws Exception {
                        Logger.e("最后接受到数据   "+s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }


    private void getBitmapByAddress() {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_clear);
        Observable.just(drawable)
                .map(new Function<Drawable, Bitmap>() {
                    @Override
                    public Bitmap apply(Drawable drawable) throws Exception {

                        return drawableToBitmap(drawable);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        iv.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap
    {
        int width = drawable.getIntrinsicWidth();// 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;// 取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把drawable内容画到画布中
        return bitmap;
    }

    private void orderSendBytes() {
        String[] bytes = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l"};
        Observable.fromArray(bytes).observeOn(AndroidSchedulers.mainThread())
//                .filter(new Predicate<String>() {   //RxJava filter对数据进行过滤
//                    @Override
//                    public boolean test(String s) throws Exception {
//                        if(s.equals("d") || s.equals("k")){
//                            return true;
//                        }
//                        return false;
//                    }
//                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Logger.e("输出数组数据  " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void orderSendStr() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5, 6); //被观察者
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

                Logger.e("收到数据" + integer.intValue() + "\n" + Thread.currentThread());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())   //子线程订阅
//                .observeOn(AndroidSchedulers.mainThread())   //主线程订阅
                .subscribe(observer);
    }

    private void baseRxJava() {
        //观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Logger.d("观察者发现被观察者发出动作");
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("观察者发现被观察者抛出异常信息");
            }

            @Override
            public void onComplete() {

            }
        };
        //被观察者( RxJava 使用 create() 方法来创建一个 Observable)
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("lison is send");  //被观察者发出动作

                SystemClock.sleep(3000);

                emitter.onError(new Throwable("start exception"));
            }
        });

        observable
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    private void startSplash() {
        //倒计时
        Observable<Long> observe = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .take(3)
                .observeOn(AndroidSchedulers.mainThread());
        observe.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Long aLong) {
                Logger.d(String.valueOf(aLong));
                int intValue = aLong.intValue();
                if (intValue == 2) {
                    Logger.d("跳转");
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}


