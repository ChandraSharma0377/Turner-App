package com.turner.app.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.turner.app.R;
import com.turner.app.asynctask.AsyncProcess;
import com.turner.app.helper.Commons;
import com.turner.app.helper.ShowAlertInformation;
import com.turner.app.main.MainActivity;

import org.json.JSONObject;

import java.util.HashMap;

public class ForgetPwdFragment extends Fragment {

	private Button btn_reset;
	private EditText edt_email;
	private TextView tv_login_back;
	private LoginTask lat;
	private ProgressDialog progressDialog;

	public ForgetPwdFragment() {
		super();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {

		View view = inflater.inflate(R.layout.lay_forget_pwd, container, false);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		btn_reset = (Button) view.findViewById(R.id.btn_reset);
		edt_email = (EditText) view.findViewById(R.id.edt_email);

		tv_login_back = (TextView) view.findViewById(R.id.tv_login_back);
		tv_login_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				MainActivity.getMainScreenActivity().changeNavigationContentFragment(new LoginFragment(), false);
			}
		});
		btn_reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String email = edt_email.getText().toString().trim();

				edt_email.setError(null);

				if (email.equals("")) {

					edt_email.setError("Please enter email id");
				} else {
					if (MainActivity.getNetworkHelper().isOnline()) {

						HashMap<String, String> postDataParams = new HashMap<String, String>();
						// postDataParams.put("phone_number", "8879337402");
						// 9699076104
						postDataParams.put("phone_number", email);

						new LoginTask(postDataParams).execute(Commons.LOGIN_URL);
					} else {
						new ShowAlertInformation(getActivity()).showDialog("Network error",
								getString(R.string.offline));
					}
				}

			}
		});
		return view;
	}

	@Override
	public void onResume() {

		super.onResume();

		MainActivity.getMainScreenActivity().actionBarTitle.setText("");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
	}

	private class LoginTask extends AsyncProcess {

		public LoginTask(HashMap<String, String> postDataParams) {
			super(postDataParams);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(getActivity(), "", "login please wait...");
			progressDialog.setCancelable(true);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setOnCancelListener(cancelListener);
		}

		@Override
		protected String doInBackground(String... params) {
			return super.doInBackground(params);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (200 == responseCode) {

				String value = result.replace("\\", "");
				if (value.length() > 2)
					value = value.substring(1, value.length() - 1);
				try {

					// JSONArray jarray = new JSONArray(value);
					JSONObject jo = new JSONObject(value);
					// [{"status": "Success","User_ID": "2","UserName": "ashishs
					// ","FirstName": "ashish","LastName": ""}]
					//
					String status = jo.getString("status");

					if (status.equals("Success")) {
						String User_ID = jo.getString("User_ID");
						String FirstName = jo.getString("FirstName");
						String LastName = jo.getString("LastName");
						System.out.println(status + "\n" + User_ID + "\n" + FirstName + "\n" + LastName);
						// MainActivity.getMainScreenActivity()
						// .getSupportFragmentManager().popBackStack();

						MainActivity.getMainScreenActivity().setSharPreferancename(FirstName + " " + LastName, User_ID,
								edt_email.getText().toString().trim(), true);
						MainActivity.getMainScreenActivity().changeNavigationContentFragment(new LandingFragment(),
								false);

					} else {
						progressDialog.dismiss();
						new ShowAlertInformation(getActivity()).showDialog("Error", "Invalid Mobile  Number");
					}
				} catch (Exception e) {
					e.printStackTrace();
					new ShowAlertInformation(getActivity()).showDialog("Error", "No data found");
					progressDialog.dismiss();
				}
				System.out.println("LoginTask result is : " + (result == null ? "" : result));
				progressDialog.dismiss();
			} else {
				Log.i("LoginTask response", result == null ? "" : result);
				new ShowAlertInformation(getActivity()).showDialog("Error", "Error");
				progressDialog.dismiss();
			}

		}

		OnCancelListener cancelListener = new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface arg0) {
				if (null != lat) {
					lat.cancel(true);
					System.out.println("refe" + lat.isCancelled());
					lat = null;
					// activity.getSupportFragmentManager().popBackStack();
				}
			}
		};
	}

}
