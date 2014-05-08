package com.monash.halftone.view;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.monash.halftone.R;

public class MainFragment extends Fragment implements OnClickListener{
	Button bCamera, bLoadGallery;
	static final int REQUEST_TAKE_PHOTO = 1;
	static final int REQUEST_GALLERY_PHOTO = 2;
	static File photoFile = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_menu_fragment, container, false);

		bLoadGallery = (Button) view.findViewById(R.id.bGallery);
		bLoadGallery.setOnClickListener( this);

		bCamera = (Button) view.findViewById(R.id.bPhoto);
		bCamera.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.bGallery:
			startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), REQUEST_GALLERY_PHOTO);
			break;

		case R.id.bPhoto:
			// create Intent to take a picture and return control to the calling application
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
			}
			if (photoFile != null) {
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
				intent.putExtra("return-data", true);
				// start the image capture Intent
				startActivityForResult(intent, REQUEST_TAKE_PHOTO);
			}
			break;

		}		
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);

		if(resultCode == Activity.RESULT_OK){
			if(requestCode == REQUEST_GALLERY_PHOTO)
			{
				Toast.makeText(getActivity(), "Gallery", Toast.LENGTH_SHORT).show();
				
				Uri selectedImage = data.getData();
	            String[] filePathColumn = {MediaStore.Images.Media.DATA};

	            Cursor cursor = getActivity().getContentResolver().query(
	                               selectedImage, filePathColumn, null, null, null);
	            cursor.moveToFirst();

	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String filePath = cursor.getString(columnIndex);
	            cursor.close();
	            Intent intent = new Intent(this.getActivity(), ImageViewerActivity.class);
				intent.putExtra("image", filePath);
				startActivity(intent);
			}
			else if(requestCode == REQUEST_TAKE_PHOTO)
			{
				Toast.makeText(getActivity(), "Camera", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this.getActivity(), ImageViewerActivity.class);
				intent.putExtra("image", photoFile.getAbsolutePath());
				startActivity(intent);
			}
		}
	}

	private void setUp(){

	}

	private void takePhoto(){

	}
	String mCurrentPhotoPath;
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "PNG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName,".png", storageDir);

		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}

}
