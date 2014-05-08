package com.monash.halftone.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.RadioButton;
import android.widget.Toast;

import com.monash.halftone.R;
import com.monash.halftone.model.FilteredImage;
import com.monash.halftone.model.Image;
import com.monash.halftone.model.Image.Filter;

public class ImageViewerFragment extends Fragment implements OnClickListener {
	ImageView ivMain;
	Image image;
	Button bShare, bLoad, bSave;
	RadioButton rbFilter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.image_viewer_fragment, container, false);

		ivMain = (ImageView) view.findViewById(R.id.ivImage);

		String uri = getActivity().getIntent().getExtras().getString("image");

		Bitmap bitmap = BitmapFactory.decodeFile(uri);
		ivMain.setImageBitmap(bitmap);
		Toast.makeText(getActivity(), "Added Image", Toast.LENGTH_LONG).show();

		return view;
	}

	@Override
	public void onClick(View v) {
		//
		switch(v.getId())
		{
		case R.id.bShare:
			shareImage();
			break;
		case R.id.bImageLoad:
			loadImage();
			break;
		case R.id.bSave:
			saveImage();
			break;
		case R.id.rbNone:
			if (((RadioButton) v).isChecked())
				applyImageFilter(Filter.NONE);
			break;
		case R.id.rbHalftone:
			if (((RadioButton) v).isChecked())
				applyImageFilter(Filter.HALFTONE);
			break;
		case R.id.rbGrayscale:
			if (((RadioButton) v).isChecked())
				applyImageFilter(Filter.GRAYSCALE);
			break;
		}
	}

	private void loadImage(){
		//TODO Add load from gallery
	}

	private String saveImage(){
		File file;
		String filename = image.getFilename();
		// Check External Storage State, if mounted and accessible
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) 
		{
			Toast.makeText(getActivity(), "SD Card", Toast.LENGTH_SHORT).show();
			File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + filename);
			dir.mkdirs();
			file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + filename, filename + ".jpg");

			// Get Bitmap from Drawables, then write image to file through fileoutputstream
			//			Bitmap image = BitmapFactory.decodeFile(file.getAbsolutePath());
			//			Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.andoridjellybeanlogo);
			try {
				FileOutputStream fo = new FileOutputStream(file);
				image.getFilteredImage().compress(Bitmap.CompressFormat.JPEG, 100, fo);
			} catch (IOException e) {                       
				e.printStackTrace();
			}
		}
		else // Use Internal Storage
		{
			Toast.makeText(getActivity(), "Internal Storage", Toast.LENGTH_SHORT).show();
			//			Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.andoridjellybeanlogo);
			file = new File(getView().getContext().getFilesDir() + File.separator + filename + ".jpg");
			try {
				FileOutputStream fo = getView().getContext().openFileOutput(filename + ".jpg", Context.MODE_WORLD_READABLE);
				image.getFilteredImage().compress(Bitmap.CompressFormat.JPEG, 100, fo);
			} catch (IOException e) {                       
				e.printStackTrace();
			}
		}

		return file.getAbsolutePath();
	}

	private void shareImage(){
		//TODO Save Image and get file path
		File file = new File(saveImage());

		// Create Share Intent
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("image/*");
		// Get Uri from file location
		Uri uri = Uri.fromFile(file.getAbsoluteFile());
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

		// Start Activity with a chooser menu
		startActivity(Intent.createChooser(shareIntent, "Send your image"));
	}

	private void applyImageFilter(Image.Filter filter){
		if( ((BitmapDrawable)(ivMain.getDrawable())).getBitmap() != null )
		{
			image.setFilter(this.getActivity().getApplicationContext(), filter);
			ivMain.setImageDrawable( new BitmapDrawable(getResources(), image.getFilteredImage()));
		}
	}
}
