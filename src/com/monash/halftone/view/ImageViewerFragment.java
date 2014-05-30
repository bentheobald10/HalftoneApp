package com.monash.halftone.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.monash.halftone.R;
import com.monash.halftone.model.Halftone;
import com.monash.halftone.model.Image;
import com.monash.halftone.model.Image.Filter;
/**
 * The ImageViewerFragment class handles the majority of the app logic, hence the largness of it. It handles every user story.
 * @author Jake Spicer and Ben Theobald
 */
public class ImageViewerFragment extends Fragment implements OnClickListener, OnCheckedChangeListener{
	ImageView ivMain;
	TextView tvRotation;
	Image image;
	Uri uri;
	Button bShare, bText, bSave, bHalfOptions;
	RadioGroup rgFilter;
	int capPos = 0;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.image_viewer_fragment, container, false);
		
		//Connect reference variables to several views
		ivMain = (ImageView) view.findViewById(R.id.ivImage);
		tvRotation = (TextView) view.findViewById(R.id.tvRotation);
		rgFilter = (RadioGroup) view.findViewById(R.id.rgFilters);
		rgFilter.setOnCheckedChangeListener(this);

		String uriString = getActivity().getIntent().getExtras().getString("image");
		uri = Uri.parse(uriString);

		//set-up Image variable
		image = new Image(uri, uriString.toString(), Filter.NONE, 10);
		image.setFilter(Filter.NONE);

		// Add Image to the ImageView
		ivMain.setImageBitmap( image.getImage());

		//Adds name above image if possible
		addNameView(view);

		// Add On-Click Listeners for buttons
		bShare = (Button) view.findViewById(R.id.bShare);
		bShare.setOnClickListener(this);
		bSave = (Button) view.findViewById(R.id.bSave);
		bSave.setOnClickListener(this);
		bText = (Button) view.findViewById(R.id.bText);
		bText.setOnClickListener(this);
		bHalfOptions = (Button) view.findViewById(R.id.bHalfOptions);
		bHalfOptions.setOnClickListener(this);

		return view;
	}
	/**
	 * This adds a textview above the image with the name of the image, if possible
	 * @param view layout
	 */
	private void addNameView(View view) {
		TextView imgName = (TextView) view.findViewById(R.id.imageNameTextView);
		String[] splitResult = (String[])image.getFilename().split("/");

		try{
			imgName.setText(splitResult[6]);
		} catch (RuntimeException e){};		
	}

	@Override
	/**
	 * Switch statement which triggers different methods depending on which button was pressed
	 */
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.bShare:
			shareImage(v);
			break;
		case R.id.bSave:
			saveImage(v);
			break;
		case R.id.bText:
			addCaption();
			break;
		case R.id.bHalfOptions:
			changeHalftoning();
			break;
		}
	}
	/**
	 * This creates and shows the Alert Dialog CaptionFragment, which allows the user to add a caption to an image.
	 */
	private void addCaption() {
		CaptionFragment cf = new CaptionFragment();
		cf.show(getFragmentManager(), "caption");
	}
	
	/**
	 * Caption return methods
	 * @param dialog
	 */
	public void onCapDialogPositiveClick(DialogFragment dialog) {
		CaptionFragment cf = (CaptionFragment)dialog;
		image.addText(cf.getText());
		image.setCaptionPos(cf.getPos());

		if( ((BitmapDrawable)(ivMain.getDrawable())).getBitmap() != null )
		{
			ivMain.setImageDrawable( new BitmapDrawable(getResources(), image.getImage()));
		}
	}
	public void onCapDialogNegativeClick(DialogFragment dialog) {
		//do nothing
	}

	/**
	 * Creates and shows the Alert Dialog HaldtoneOptionsFragment
	 */
	private void changeHalftoning() {
		HalftoneOptionsFragment hf = new HalftoneOptionsFragment();
		hf.show(getFragmentManager(), "options");

	}
	/**
	 * Halftone return methods
	 * @param dialog
	 */
	public void onHalfOpDialogPositiveClick(DialogFragment dialog){
		HalftoneOptionsFragment halftoneFragmentDialog  = (HalftoneOptionsFragment)dialog;
		Halftone.HalftoneShape hShape = halftoneFragmentDialog.getHShape();
		int rotation = halftoneFragmentDialog.getRotationAngle();
		
		image.setRotationAngle(rotation);
		image.setHalftoneShape(hShape);
		
		tvRotation.setText("Rotation Angle: " +  rotation);
	}
	public void onHalfOpDialogNegativeClick(DialogFragment dialog){
		//do nothing
	}

	@Override
	/**
	 * This method checks which Image Filter radio button has been pressed, and triggers the corresponding filtering method
	 */
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId)
		{
		case R.id.rbNone:
			Log.i("ImageViewer", "None");
			applyImageFilter(Filter.NONE);
			break;
		case R.id.rbHalftone:
			Log.i("ImageViewer", "Halftone");
			applyImageFilter(Filter.HALFTONE);
			break;
		case R.id.rbGrayscale:
			Log.i("ImageViewer", "Grayscale");
			applyImageFilter(Filter.GRAYSCALE);
			break;
		case R.id.rbNegative:
			Log.i("ImageViewer", "Negative");
			applyImageFilter(Filter.NEGATIVE);
			break;
		}
	}
	/**
	 * This is the method used to save an image. 
	 * @param layout view
	 * @return the file name of the saved file
	 */
	private String saveImage(View v){
		File file;
		//create filename
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String filename = "PNG_" + timeStamp + "_";

		// Check External Storage State, if mounted and accessible
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) 
		{
			File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "Newspaperfy"); //"Newspaperfy" should be changed to name of application
			dir.mkdirs();
			file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "Newspaperfy", filename + ".jpg");

			try {
				FileOutputStream fo = new FileOutputStream(file);
				image.getImage().compress(Bitmap.CompressFormat.JPEG, 100, fo);
			} catch (IOException e) {                       
				e.printStackTrace();
			}
		}
		else // Use Internal Storage
		{ 				
			Toast.makeText(getActivity(), "Internal Storage", Toast.LENGTH_SHORT).show();
			file = new File(getView().getContext().getFilesDir() + File.separator + filename + ".jpg");

			try {
				FileOutputStream fo = getView().getContext().openFileOutput(filename + ".jpg", Context.MODE_WORLD_READABLE);
				image.getImage().compress(Bitmap.CompressFormat.PNG, 100, fo);
			} catch (IOException e) {                       
				e.printStackTrace();
			}
		}		

		addNameView(v);
		return file.getAbsolutePath();
	}
	/**
	 * This creates an intent, and shares the image to any application that can use it.
	 * @param layout view
	 */
	private void shareImage(View v){
		// Create File from image file path
		File file = new File(saveImage(v));

		// Get Uri from file location
		Uri newUri = Uri.fromFile(file.getAbsoluteFile());
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/*");
		shareIntent.putExtra(Intent.EXTRA_STREAM, newUri);
		shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

		// Start Activity with a chooser menu
		startActivity(Intent.createChooser(shareIntent, "Share image"));
	}
	/**
	 * Executes the AsyncFilter used for each filter
	 * @param filter
	 */
	private void applyImageFilter(Image.Filter filter){
		new AsyncFilter(filter).execute();
	}
	/**
	 * Creates a new Async Task which executes the filtering methods on a new process
	 * @author Jake Spicer and Ben Theobald
	 */
	public class AsyncFilter extends AsyncTask<String, Integer, Void>
	{
		private Filter filter;
		private ProgressDialog pd;

		public AsyncFilter(Filter filter)
		{
			this.filter = filter;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pd = new ProgressDialog(getActivity());
			pd.setTitle("Applying Filter");
			pd.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			image.setFilter(filter);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if( ((BitmapDrawable)(ivMain.getDrawable())).getBitmap() != null )
			{
				ivMain.setImageDrawable( new BitmapDrawable(getResources(), image.getImage()));
				Toast.makeText(getActivity(), "Finished", Toast.LENGTH_SHORT).show();
			}

			pd.dismiss();
		}		
	}	
}
