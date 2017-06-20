
package com.turner.app.fragment;

import com.turner.app.R;
import com.turner.app.helper.ShowAlertInformation;
import com.turner.app.main.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LandingFragment extends BarcodeFragment implements View.OnClickListener {
	private LinearLayout lay_scan, lay_submit, lay_help, lay_sign_out;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {

		View view = inflater.inflate(R.layout.lay_landing, container, false);
		lay_scan = (LinearLayout) view.findViewById(R.id.lay_scan);
		lay_submit = (LinearLayout) view.findViewById(R.id.lay_submit);
		lay_help = (LinearLayout) view.findViewById(R.id.lay_help);
		lay_sign_out = (LinearLayout) view.findViewById(R.id.lay_sign_out);
		lay_scan.setOnClickListener(this);
		lay_submit.setOnClickListener(this);
		lay_help.setOnClickListener(this);
		lay_sign_out.setOnClickListener(this);

		return view;
	}

	@Override
	public void onResume() {

		super.onResume();
		MainActivity.getMainScreenActivity().actionBarTitle.setText("ASSET AUDIT TRACKER");
		MainActivity.getMainScreenActivity().getLocation();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.lay_scan:
			callBarcode();
			break;
		case R.id.lay_submit:
			MainActivity.redirectToFragment(new SubmitAssetsFragment());
			break;
		case R.id.lay_help:
			MainActivity.redirectToFragment(new HelpListFragment());
			break;
		case R.id.lay_sign_out:
			SharedPreferences.Editor editor = MainActivity.getSharePreferance().edit();
			editor.clear();
			editor.commit();
			MainActivity.getMainScreenActivity().restartActivity();
			break;
		default:
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == BARCODE_SCAN_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {

				try {
					String contents = data.getStringExtra("SCAN_RESULT");
					String format = data.getStringExtra("SCAN_RESULT_FORMAT");
					if (!contents.equals("") && contents.contains("-")) {
						MainActivity.redirectToFragment(new ScanAssetsFragment(contents));
					} else {
						new ShowAlertInformation(getActivity()).showDialog("Barcode error",
								"Invalid barcode.\nResult is : " + contents);
					}

				} catch (Exception e) {
					e.printStackTrace();
					barcode_string = "";
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				// To Handle cancel
				Log.i("Scaning ", "Scan unsuccessful");
			}
		} else if (resultCode == getActivity().RESULT_CANCELED) {
			Toast.makeText(getActivity(), "User cancelled image capture", Toast.LENGTH_SHORT).show();

		} else {
			Toast.makeText(getActivity(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
		}

	}
}
