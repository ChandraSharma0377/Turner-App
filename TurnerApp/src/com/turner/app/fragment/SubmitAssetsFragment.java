package com.turner.app.fragment;

import java.util.ArrayList;

import org.json.JSONObject;

import com.turner.app.R;
import com.turner.app.adapters.SubmitAssetsAdapter;
import com.turner.app.asynctask.UploadMultipartAsync;
import com.turner.app.db.DataHelperClass;
import com.turner.app.helper.Commons;
import com.turner.app.helper.ShowAlertInformation;
import com.turner.app.main.MainActivity;
import com.turner.app.pojos.ResponseDto;
import com.turner.app.pojos.ScanItemDto;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

public class SubmitAssetsFragment extends Fragment implements View.OnClickListener {
	private ListView listview;
	private SubmitAssetsAdapter adapter;
	private Button btn_back, btn_submit, btn_delete;
	private ArrayList<ScanItemDto> dummy = new ArrayList<ScanItemDto>();
	private ProgressDialog progressDialog;
	private CheckBox cb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {

		View view = inflater.inflate(R.layout.frag_submit_assets, container, false);
		listview = (ListView) view.findViewById(R.id.listview);
		btn_back = (Button) view.findViewById(R.id.btn_back);
		btn_submit = (Button) view.findViewById(R.id.btn_submit);
		btn_delete = (Button) view.findViewById(R.id.btn_delete);
		cb = (CheckBox) view.findViewById(R.id.cb_submit);

		cb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (((CheckBox) v).isChecked()) {
					for (int i = 0; i < dummy.size(); i++) {
						dummy.get(i).setIsselect(true);
					}
				} else {
					for (int i = 0; i < dummy.size(); i++) {
						dummy.get(i).setIsselect(false);
					}
				}
				adapter = new SubmitAssetsAdapter(getActivity(), dummy);
				listview.setAdapter(adapter);

			}
		});
		btn_back.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		btn_delete.setOnClickListener(this);
		return view;
	}

	@Override
	public void onResume() {

		super.onResume();
		setListData();
		MainActivity.getMainScreenActivity().actionBarTitle.setText("Submit Assets");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {

		case R.id.btn_back:
			MainActivity.getMainScreenActivity().onBackPressed();
			break;
		case R.id.btn_submit:
			if (dummy.size() > 0) {
				uploadData();
			} else {
				new ShowAlertInformation(getActivity()).showDialog("Error", "No data to upload.");

			}

			break;
		case R.id.btn_delete:
			if (dummy.size() > 0) {
				deleteData();
			} else {
				new ShowAlertInformation(getActivity()).showDialog("Error", "No data to delete.");
			}
			break;

		default:
			break;
		}

	}

	private void uploadData() {
		ArrayList<ScanItemDto> temp = new ArrayList<>();

		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getData().get(i).isIsselect()) {
				temp.add(adapter.getData().get(i));
			}
		}
		if (temp.size() == 0) {
			new ShowAlertInformation(getActivity()).showDialog("Error", "Please select data to upload.");
		} else {
			new UploadTask(temp).execute(Commons.SUBMIT_DETAILS);
		}

	}

	private void deleteData() {
		final ArrayList<ScanItemDto> temp = new ArrayList<>();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getData().get(i).isIsselect()) {
				temp.add(adapter.getData().get(i));
			}
		}
		if (temp.size() == 0) {
			new ShowAlertInformation(getActivity()).showDialog("Error", "Please select data to delete.");
		} else {
			
			new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle(getString(R.string.delete_title)).setMessage(getString(R.string.delete_msg))
			.setCancelable(false)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					for (int i = 0; i < temp.size(); i++) {
						DataHelperClass DHC = new DataHelperClass(getActivity());
						DHC.deleteRecord(temp.get(i).getUniqueId());
					}
					setListData();
					dialog.dismiss();
				}
			}).setNegativeButton("No", null).show();
		}
	}

	private class UploadTask extends UploadMultipartAsync {

		public UploadTask(ArrayList<ScanItemDto> postDataParams) {
			super(postDataParams);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(getActivity(), "", "uploading please wait...");
			progressDialog.setCancelable(true);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setOnCancelListener(cancelListener);
		}

		@Override
		protected ArrayList<ResponseDto> doInBackground(String... params) {
			return super.doInBackground(params);
		}

		@Override
		protected void onPostExecute(ArrayList<ResponseDto> result) {
			super.onPostExecute(result);
			String output = "";
			int successcount=0;
			if (result.size() > 0) {
				for (int i = 0; i < result.size(); i++) {
					try {
						ResponseDto rdo = result.get(i);
						JSONObject jo = new JSONObject(rdo.getStatus());
						String status = jo.getString("status");
						String message = jo.getString("message");
						if (status.equals("1")) {
							++successcount;
							DataHelperClass DHC = new DataHelperClass(getActivity());
							DHC.deleteRecord(rdo.getUniqueId());
						} else {

						}
						output += "\n" + message;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			String msg ="";
			if(successcount ==result.size())
				msg="All assets submitted!";
			if(successcount <result.size())
				msg=successcount+" assets out of "+result.size()+" submitted!";
			new ShowAlertInformation(getActivity()).showDialog("Submit Assets", msg);
			setListData();
			cb.setChecked(false);
			progressDialog.dismiss();
		}

		OnCancelListener cancelListener = new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface arg0) {
				// if (null != lat) {
				// lat.cancel(true);
				// System.out.println("refe" + lat.isCancelled());
				// lat = null;
				// // activity.getSupportFragmentManager().popBackStack();
				// }
			}
		};
	}

	private void setListData() {
		DataHelperClass DHC = new DataHelperClass(getActivity());
		if (!DHC.isRecordExist()) {
			Toast.makeText(getActivity(), "No data to display", Toast.LENGTH_LONG).show();
			dummy.clear();
		} else {
			dummy = DHC.getScanData();
		}
		adapter = new SubmitAssetsAdapter(getActivity(), dummy);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				MainActivity.redirectToFragment(new ScanAssetsFragment(dummy.get(position)));
			}
		});

	}
}
