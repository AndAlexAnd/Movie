package com.example.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {

    private static final String TAG = "MovieDetailViewModel";

    private final MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>(); // + добавляем getter
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public MovieDetailViewModel(@NonNull Application application) { // переопределяем через ctrl+o или через alt+ins
        super(application);
    }

    public LiveData<List<Trailer>> getTrailers() { // MutableLiveData меняем на LiveData
        return trailers;
    }
    public void loadTrailers(int id){

        Disposable disposable = ApiFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TrailerResponse, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(TrailerResponse trailerResponse) throws Throwable {
                        return trailerResponse.getTrailersList().getTrailers();
                    }
                    // TrailerResponse - тип данных, который находится в цепочке, Object - тип данных, который мы хотим видеть в методе accept
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailerList) throws Throwable {
                        trailers.setValue(trailerList);
                        // устанавливаем в LiveData (trailers) коллекцию фильмов trailerResponse.getTrailersList().getTrailers()
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
            compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() { // ctrl + o
        super.onCleared();
        compositeDisposable.dispose();
    }
}
