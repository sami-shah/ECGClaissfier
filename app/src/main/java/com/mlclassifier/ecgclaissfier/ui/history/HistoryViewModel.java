package com.mlclassifier.ecgclaissfier.ui.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.model.EcgResult;
import com.mlclassifier.ecgclaissfier.repository.HistoryRepository;
import com.mlclassifier.ecgclaissfier.repository.HomeRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<EcgResult>> history;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        history = new MutableLiveData<>();
    }

    public void setHistory(List<EcgResult> list){
        history.setValue(list);
    }

    public LiveData<List<EcgResult>> getHistory(){
        return history;
    }


    public LiveData<String> getText() {
        return mText;
    }

    public void retrieveHistory(MainActivity mainActivity, String userId, HistoryViewModel viewModel){
        HistoryRepository historyRepository = new HistoryRepository();
        historyRepository.retrieveHistories(viewModel, userId, mainActivity);
    }
}