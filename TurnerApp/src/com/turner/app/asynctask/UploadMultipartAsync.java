package com.turner.app.asynctask;

import java.util.ArrayList;

import com.turner.app.main.MainActivity;
import com.turner.app.pojos.ResponseDto;
import com.turner.app.pojos.ScanItemDto;

import android.os.AsyncTask;
import android.util.Log;

public class UploadMultipartAsync extends AsyncTask<String, Void, ArrayList<ResponseDto>> {

	protected int responseCode = 0;
	private ArrayList<ScanItemDto> postDataList;

	protected ArrayList<ResponseDto> responselist = new ArrayList<ResponseDto>();

	public UploadMultipartAsync() {
	}

	public UploadMultipartAsync(ArrayList<ScanItemDto> postDataList) {
		this.postDataList = postDataList;
	}

	@Override
	protected ArrayList<ResponseDto> doInBackground(String... params) {
		try {

			String charset = "UTF-8";
			String requestURL = params[0].toString();
			for (int i = 0; i < postDataList.size(); i++) {
				ScanItemDto scan = postDataList.get(i);
				MultipartUtility multipart = new MultipartUtility(requestURL, charset);
				multipart.addHeaderField("x-api-key", "422ECF82D48A9D5A9EA9A573544674C0");
				multipart.addHeaderField("Accept", "application/json");
				multipart.addFormField("user_id", MainActivity.getMainScreenActivity().getUserID());
				multipart.addFormField("act_user_id", MainActivity.getMainScreenActivity().getActiveUserID());
				multipart.addFormField("profile_center", scan.getProfitcenter());
				multipart.addFormField("company_code", scan.getCompanycode());
				multipart.addFormField("asset_number", scan.getAssetsno());
				multipart.addFormField("sub_number", scan.getSubno());
				multipart.addFormField("gps_coordinates", scan.getGpscoordinate());
				multipart.addFormField("comment", scan.getComments());
				multipart.addFormField("created_by", "1");
				multipart.addFormField("modified_by", "1");
				multipart.addFormField("status", "1");
				if (null != scan.getImageOne())
					multipart.addFilePart("image[]", scan.getImageOne());
				if (null != scan.getImageTwo())
					multipart.addFilePart("image[]", scan.getImageTwo());

				String response = multipart.finish();
				ResponseDto rdo = new ResponseDto();
				rdo.setUniqueId(scan.getUniqueId());
				rdo.setStatus(response);
				responselist.add(rdo);
				Log.v("rht", "Line : " + response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return responselist;
	}

}
