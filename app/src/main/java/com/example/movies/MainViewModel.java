package com.example.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {


    private final MutableLiveData<List<MovieFromDocs>> moviesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false); // можно сразу установить значение
        // добавляем getter alt+ins
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = "MainViewModel";
    private int page = 1;

    public LiveData<Boolean> getIsLoading() { // убираем Mutable - делаем неизменяемым
        return isLoading;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        loadMovies(); // viewModel.loadMovies(); - удаляем viewModel.
    } // CTRL+O
    public LiveData<List<MovieFromDocs>> getMoviesLiveData(){

        return moviesLiveData;
    } // ALT+INSERT

    public void filterMovie(){

    }

    public void loadMovies(){
        Boolean loading = isLoading.getValue();//получаем значение LiveData
        if (loading != null && loading) { // loading не равен null && loading равен true
            return; // Тогда мы выйдем из этого метода. Код ниже не будет исполнен.
        }
        Disposable disposable = ApiFactory.apiService.loadMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() { // действие во время в момент подписки (начала загрузки)
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoading.setValue(false); // действие во время окончания подписки (остановки загрузки)
                    }
                })
                .subscribe(new Consumer<MovieResponseFromDocs>() {
                    @Override
                    public void accept(MovieResponseFromDocs movieResponseFromDocs) throws Throwable {
                    List<MovieFromDocs> loadedMovies = moviesLiveData.getValue(); //получать данные из LiveData - .getValue(); Например moviesLiveData.getValue();
                        //Создаем новую коллекцию для извлечения данных из LiveData
                    if (loadedMovies != null) {
                        loadedMovies.addAll(movieResponseFromDocs.getMovies()); // addAll добавляем новые фильмы в новую коллекцию к существующим
                        moviesLiveData.setValue(loadedMovies); // вставляем новую коллекцию в LiveData
                    } else { // если это первая загрузка
                        moviesLiveData.setValue(movieResponseFromDocs.getMovies()); // добавляем первые фильмы в основную коллекцию
                    }
                page++;
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
