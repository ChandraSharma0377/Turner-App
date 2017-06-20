package com.turner.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BarcodeFragment extends Fragment {
	protected final int BARCODE_SCAN_REQUEST_CODE = 200;
	protected String barcode_string = "";

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	protected void callBarcode() {
		String packageString = getActivity().getPackageName();
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.setPackage(packageString);
		intent.putExtra("SCAN_MODE", "SCAN_MODE");
		startActivityForResult(intent, BARCODE_SCAN_REQUEST_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == BARCODE_SCAN_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {

				try {
					String contents = data.getStringExtra("SCAN_RESULT");
					String format = data.getStringExtra("SCAN_RESULT_FORMAT");
					// if(contents.length()==15){
					barcode_string = contents;
					// }else{
					// //wrong barcode
					// }

				} catch (Exception e) {
					e.printStackTrace();
					barcode_string = "";
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				// To Handle cancel
				Log.i("Scaning ", "Scan unsuccessful");
			}
		}

	}
}
