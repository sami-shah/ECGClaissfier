package com.mlclassifier.ecgclaissfier.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.R;
import com.mlclassifier.ecgclaissfier.model.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;
import static com.github.vipulasri.timelineview.TimelineView.TAG;
import static com.mlclassifier.ecgclaissfier.model.Constants.MY_PREFS_NAME;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Unbinder unbinder;
    public static final int PICK_IMAGE = 1;
    Bitmap bitmap;
    private int READ_STORAGE_PERMISSION_REQUEST_CODE = 1000;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public  Uri selectedImage;

    @BindView(R.id.imageClassify)
    ImageView imageClassify;

      @BindView(R.id.fab)
    ImageView fab;

      @BindView(R.id.fabSend)
    ImageView fabSend;


     @BindView(R.id.predictionLayout)
    LinearLayout predictionLayout;


    @BindView(R.id.prediction)
    TextView textView;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        if(unbinder == null){
            unbinder = ButterKnife.bind(this, root);
        }

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals(Constants.VISIBILITY_GONE)) {
                    predictionLayout.setVisibility(View.GONE);
                }else {
                    predictionLayout.setVisibility(View.VISIBLE);
                    textView.setText("Test results: " + s);
                }
            }
        });

        setButtonHide();

        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            //TODO: action
            selectedImage = data.getData();

            try {
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage));
                homeViewModel.selectedBitmap = bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageClassify.setImageBitmap(homeViewModel.selectedBitmap);
            setButtonHide();
        }

    }

    @OnClick(R.id.fabSend)
    public void sendImage(){
        ((MainActivity)getActivity()).showSuccessSnackBar("To be implemented");
//         File attachImage = new File((getRealPathFromURI(selectedImage)));
        File attachImage = null;
        try {
            attachImage = bitmapToFile(homeViewModel.selectedBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences prefs = ((MainActivity)getActivity()).getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
         homeViewModel.sendImage(attachImage, ((MainActivity)getActivity()), prefs, homeViewModel);
    }

    public File bitmapToFile(Bitmap bitmap) throws IOException {
        File f = new File(requireContext().getCacheDir(), "dum");
        f.createNewFile();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return f;
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @OnClick(R.id.fab)
    public void selectImage(){
        checkPermissionForReadExtertalStorage();
    }


    public boolean checkPermissionForReadExtertalStorage() {
        if (!checkIfAlreadyhavePermission()) {
            ActivityCompat.requestPermissions( getActivity(),galleryPermissions,1);
        } else {
            startYourCameraIntent();
        }
        return false;
    }



    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
//    Handle Permission Dialog "Allow" and "Deny" button action in onRequestPermission():

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startYourCameraIntent();
                } else {
                    Toast.makeText(getActivity(), "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }


    void startYourCameraIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    void setButtonHide(){
        if(bitmap == null){
            fabSend.setVisibility(View.GONE);
        }else {
            fabSend.setVisibility(View.VISIBLE);
        }
    }

}
