package com.turner.app.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.turner.app.R;

public class ShowAlertInformation {

	private Context context;

	public ShowAlertInformation(Context context) {
		this.context = context;
	}

	public void showDialog(String title, String message) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(title);
		alertDialog.setCancelable(false);
		alertDialog.setMessage(message);
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				dialog.cancel();
			}
		});

		alertDialog.show();
	}

	public void showDialog(String Title, String Message, OnClickListener positiveListner) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(Title);
		alertDialog.setMessage(Message);
		alertDialog.setCancelable(false);
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setNegativeButton("OK", positiveListner);
		alertDialog.show();
	}

}
