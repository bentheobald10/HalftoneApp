package com.monash.halftone.view;

import android.app.Activity;
import android.os.Bundle;
import com.monash.halftone.R;

public class ImageViewerActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new ImageViewerFragment()).commit();
		}

	}
}
