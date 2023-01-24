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
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<List<MovieFromDocs>> moviesLiveData = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = "MainViewModel";

    private int page = 1;

    public MainViewModel(@NonNull Application application) {
        super(application);
    } // CTRL+O
    public LiveData<List<MovieFromDocs>> getMoviesLiveData(){
        return moviesLiveData;
    } // ALT+INSERT
    public void loadMovies(){
        Disposable disposable = ApiFactory.apiService.loadMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponseFromDocs>() {
                    @Override
                    public void accept(MovieResponseFromDocs movieResponseFromDocs) throws Throwable {
                        page++;
                    moviesLiveData.setValue(movieResponseFromDocs.getMovies());
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
    protected void onCleared() { //CTRL+O
        super.onCleared();
        compositeDisposable.dispose();
    }
}
