package com.mlclassifier.ecgclaissfier.ui.analytics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.model.EcgResult;
import com.mlclassifier.ecgclaissfier.repository.AnalyticsRepository;

import java.util.List;

public class AnalyticsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<EcgResult>> listMutableLiveData = new MutableLiveData<>();


    public AnalyticsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Analytics fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<EcgResult>> getListMutableLiveData(){
        return listMutableLiveData;
    }



    public void setListMutableLiveData(List<EcgResult> list){
        listMutableLiveData.setValue(list);
    }

    public void fetchAnalytics(MainActivity mainActivity, AnalyticsViewModel viewModel){
        AnalyticsRepository repository = new AnalyticsRepository();
        repository.fetchAnalytics(mainActivity, viewModel);


    }


}