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
import android.os.Environment;
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
import androidx.core.content.FileProvider;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;
import static com.github.vipulasri.timelineview.TimelineView.TAG;
import static com.mlclassifier.ecgclaissfier.model.Constants.MY_PREFS_NAME;

public class HomeFragment extends Fragment {

    private static final int REQUEST_TAKE_PHOTO = 192;
    private static final int RC_PERMISSION_CAMEREA = 42;
    private static final int RC_PERMISSION_PICK = 23;
    private HomeViewModel homeViewModel;
    Unbinder unbinder;
    public static final int PICK_IMAGE = 1;
    Bitmap bitmap;
    private int READ_STORAGE_PERMISSION_REQUEST_CODE = 1000;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String[] cameraPermissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public  Uri selectedImage;

    @BindView(R.id.imageClassify)
    ImageView imageClassify;

      @BindView(R.id.fab)
    ImageView fab;

      @BindView(R.id.fabSend)
    ImageView fabSend;

    @BindView(R.id.fabCamera)
    FloatingActionButton fabCamera;


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
        } else if (requestCode == REQUEST_TAKE_PHOTO) {
            try {
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
                        .openInputStream(homeViewModel.photoURI));
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
        File f = new File(requireContext().getCacheDir(), "dummy.jpg");
        f.createNewFile();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
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
        checkPermissionForReadExtertalStorage(false);
    }

    @OnClick(R.id.fabCamera)
    public void takeImage(){
        checkPermissionForReadExtertalStorage(true);
    }

    public boolean checkPermissionForReadExtertalStorage(Boolean isCamera) {
        if (!checkIfAlreadyhavePermission(isCamera)) {
            if(isCamera)
                ActivityCompat.requestPermissions( getActivity(), cameraPermissions,RC_PERMISSION_CAMEREA);
            else
                ActivityCompat.requestPermissions( getActivity(), galleryPermissions,RC_PERMISSION_PICK);
        } else {
            startYourCameraIntent(isCamera);
        }
        return false;
    }



    private boolean checkIfAlreadyhavePermission(Boolean isCamera) {

        int result = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int resultCamera = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);
        int granted = PackageManager.PERMISSION_GRANTED;
        if(isCamera)
            return resultCamera == granted && result == granted;
        return result == granted;
    }
//    Handle Permission Dialog "Allow" and "Deny" button action in onRequestPermission():

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RC_PERMISSION_PICK: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startYourCameraIntent(false);
                } else {
                    Toast.makeText(getActivity(), "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case RC_PERMISSION_CAMEREA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startYourCameraIntent(true);
                } else {
                    Toast.makeText(getActivity(), "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }


    void startYourCameraIntent(Boolean isCamera){
        Intent intent = new Intent();
        if(isCamera) {
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        homeViewModel.photoURI = FileProvider.getUriForFile(requireContext(),
                                "com.mlclassifier.ecgclaissfier.fileprovider",
                                photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, homeViewModel.photoURI);
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        homeViewModel.currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    void setButtonHide(){
        if(bitmap == null){
            fabSend.setVisibility(View.GONE);
        }else {
            fabSend.setVisibility(View.VISIBLE);
        }
    }

}
