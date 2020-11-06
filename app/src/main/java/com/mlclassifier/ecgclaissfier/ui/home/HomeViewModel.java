package com.mlclassifier.ecgclaissfier.ui.home;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.model.Constants;
import com.mlclassifier.ecgclaissfier.model.EcgResult;
import com.mlclassifier.ecgclaissfier.repository.HomeRepository;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HomeViewModel extends AndroidViewModel {

    private static final String TAG = "HomeViewModel";
    private MutableLiveData<String> mText;
    private MutableLiveData<String> mPredictedResults = new MutableLiveData<>();

    Application application;

    Bitmap selectedBitmap;
    String currentPhotoPath;
    Uri photoURI;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        mPredictedResults.setValue(Constants.VISIBILITY_GONE);
    }

    public void setmPredictedResults(List<EcgResult> list){
        mPredictedResults.setValue(list.get(0).getEcg_result());
    }

    public LiveData<String> getText() {
        return mPredictedResults;
    }

    public void sendImage(File image, MainActivity activity, SharedPreferences prefs, HomeViewModel viewModel){
        File imageClassify = image;
        RequestBody imageClassifyBody = RequestBody.create(MediaType.parse("image/jpg"), imageClassify);
        MultipartBody.Part fileuploadAttach = MultipartBody.Part.createFormData("attachment", imageClassify.getName(), imageClassifyBody);
        HomeRepository homeRepository = new HomeRepository();
        String id  = prefs.getString(Constants.ID, "not-found");
        Log.d(TAG, "sendImage: "+ id);

        homeRepository.sendImageToServer(fileuploadAttach, imageClassify.getName(), "image/jpg", id,activity, viewModel);

    }



}