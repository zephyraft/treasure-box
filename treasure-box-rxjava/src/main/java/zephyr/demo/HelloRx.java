package zephyr.demo;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HelloRx {

    public static void main(String[] args) {
        simple();
        chain();
    }

    // 朴素调用
    private static void simple() {
        // 事件源
        Observable<String> observable = Observable.create(emitter -> {
            // 创建事件源
            emitter.onNext("hello"); // 产生事件
            emitter.onNext("www.zephyr.com");
            emitter.onNext("let's study");
        });

        // 消费者
        Consumer<String> consumer = s -> System.out.println(Thread.currentThread().getName() + " == consumer == " + s);

        // 异步订阅
        observable.observeOn(Schedulers.newThread()).subscribe(consumer);

        // 同步订阅
        observable.subscribe(consumer);

        // 观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(Thread.currentThread().getName() + " == observer == " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);
    }

    // 链式调用
    private static void chain() {
        Observable.create(emitter -> {
            // 创建事件源
            emitter.onNext("hello"); // 产生事件
            emitter.onNext("www.zephyr.com");
            emitter.onNext("let's study");
        })
                .observeOn(Schedulers.newThread())
                .subscribe(s -> System.out.println(Thread.currentThread().getName() + " == chain consumer == " + s));
    }

}
