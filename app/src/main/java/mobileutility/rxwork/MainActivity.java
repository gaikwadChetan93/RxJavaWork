package mobileutility.rxwork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Observable<String> animalsObservable = getObservable();
        DisposableObserver<String> animalObserver = getObserver();
        DisposableObserver<String> animalCapitalObserver = getCapitalObserver();

        /**
         * filter() is used to filter out the animal names starting with `b`
         * */
        compositeDisposable.add(
                animalsObservable
                        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.toLowerCase().startsWith("b");
                    }
                })
                .subscribeWith(animalObserver)
        );



        /**
         * * filter() is used to filter out the animal names starting with 'c'
         * map() is used to transform all the characters to UPPER case
         */
        compositeDisposable.add(
                animalsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {

                    @Override
                    public boolean test(String s) throws Exception {
                        return s.toLowerCase().startsWith("c");
                    }
                })
                .map(new Function<String, String>() {

                    @Override
                    public String apply(String s) throws Exception {
                             return s.toUpperCase();
                    };
                })
                .subscribeWith(animalCapitalObserver));

    }

    private Observable<String> getObservable() {
        return Observable.just("Ant","beetle", "Bee", "Cat", "Dog", "Fox");
    }

    private DisposableObserver<String> getObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }
    private DisposableObserver<String> getAnimalsObserver() {
        return new DisposableObserver<String>() {

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }

    private DisposableObserver<String> getCapitalObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
