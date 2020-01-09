package com.mara.dogsapp.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mara.dogsapp.model.DogBreed;
import com.mara.dogsapp.model.DogDatabase;

public class InfoViewModel extends AndroidViewModel {

    public MutableLiveData<DogBreed> dogLiveData = new MutableLiveData<>();
    private RetrievingDogTask task;

    public InfoViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetch(int uuid) {
        task = new RetrievingDogTask();
        task.execute(uuid);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (task != null) {
            task.cancel(true);
            task = null;
        }
    }

    private class RetrievingDogTask extends AsyncTask<Integer, Void, DogBreed> {

        @Override
        protected DogBreed doInBackground(Integer... integers) {
            int uuid = integers[0];
            return DogDatabase.getInstance(getApplication()).dogDao().getDog(uuid);
        }

        @Override
        protected void onPostExecute(DogBreed dogBreed) {
            dogLiveData.setValue(dogBreed);
        }
    }
}
