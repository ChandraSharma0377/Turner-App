package com.turner.app.fragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.turner.app.R;
import com.turner.app.db.DataHelperClass;
import com.turner.app.helper.ShowAlertInformation;
import com.turner.app.main.MainActivity;
import com.turner.app.pojos.ScanItemDto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ScanAssetsFragment extends BarcodeFragment {

	public ScanAssetsFragment(String barcode) {
		barcode_string = barcode;
	}

	public ScanAssetsFragment(ScanItemDto scanItemDto) {
		this.scanItemDtoB = scanItemDto;
	}

	private TextView tv_profit_center, tv_company_code, tv_assets_no, tv_sub_number, tv_scan_datetime,
			tv_gps_coordinate;
	private View view;
	private Button btn_retake, btn_add, btn_save;
	private final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private ScanItemDto scanItemDtoB;
	private Bitmap bitmap_one, bitmap_two;
	private ImageView iv_one, iv_two;
	private EditText edt_remark;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.frag_scan_assets, container, false);
		btn_retake = (Button) view.findViewById(R.id.btn_retake);
		btn_add = (Button) view.findViewById(R.id.btn_add);
		btn_save = (Button) view.findViewById(R.id.btn_save);
		tv_profit_center = (TextView) view.findViewById(R.id.tv_profit_center);
		tv_company_code = (TextView) view.findViewById(R.id.tv_company_code);
		tv_assets_no = (TextView) view.findViewById(R.id.tv_assets_no);
		tv_sub_number = (TextView) view.findViewById(R.id.tv_sub_number);
		tv_scan_datetime = (TextView) view.findViewById(R.id.tv_scan_datetime);
		tv_gps_coordinate = (TextView) view.findViewById(R.id.tv_gps_coordinate);
		edt_remark = (EditText) view.findViewById(R.id.edt_remark);
		iv_one = (ImageView) view.findViewById(R.id.iv_one);
		iv_two = (ImageView) view.findViewById(R.id.iv_two);
		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			if (null == bitmap_one) {
				bitmap_one = photo;
				iv_one.setImageBitmap(photo);

			} else {
				bitmap_two = photo;
				iv_two.setImageBitmap(photo);
			}

		} else if (requestCode == BARCODE_SCAN_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {

				try {
					String contents = data.getStringExtra("SCAN_RESULT");
					String format = data.getStringExtra("SCAN_RESULT_FORMAT");
					if (!contents.equals("") && contents.contains("-")) {
						setData(contents);
						// company code = 4
						// assetno 8
						// space
						// sub no -2
						Log.d("Scaning ", "Scan successful " + "\nformat :" + format + "\ncontents :" + contents);
						HashMap<String, String> postDataParams = new HashMap<String, String>();
						postDataParams.put("Bar_CodeID", contents);
						// bat = new BarcodeTask(postDataParams);
						// bat.execute(Commons.GET_BARCODE_DETAIL);
					} else {
						new ShowAlertInformation(getActivity()).showDialog("Barcode error",
								"Invalid barcode.\nResult is : " + contents);

					}

				} catch (Exception e) {
					e.printStackTrace();
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

	@Override
	public void onResume() {
		super.onResume();
		MainActivity.getMainScreenActivity().actionBarTitle.setText("Scan Assets");

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		if (null != scanItemDtoB) {
			tv_profit_center.setText(scanItemDtoB.getProfitcenter());
			tv_company_code.setText(scanItemDtoB.getCompanycode());
			tv_assets_no.setText(scanItemDtoB.getAssetsno());
			tv_sub_number.setText(scanItemDtoB.getSubno());
			tv_scan_datetime.setText(scanItemDtoB.getScandatetime());
			tv_gps_coordinate.setText(scanItemDtoB.getGpscoordinate());
			edt_remark.setText(scanItemDtoB.getComments());
			edt_remark.setEnabled(false);
			if (scanItemDtoB.getImageOne() != null) {
				ByteArrayInputStream imageStream = new ByteArrayInputStream(scanItemDtoB.getImageOne());
				bitmap_one = BitmapFactory.decodeStream(imageStream);
				iv_one.setImageBitmap(bitmap_one);
			}
			if (scanItemDtoB.getImageTwo() != null) {
				ByteArrayInputStream imageStream1 = new ByteArrayInputStream(scanItemDtoB.getImageTwo());
				bitmap_two = BitmapFactory.decodeStream(imageStream1);
				iv_two.setImageBitmap(bitmap_two);
			}
			btn_add.setText("Edit Assest");
			btn_save.setText("Delete Assest");
			btn_retake.setText("Back");
		} else {
			iv_one.setImageResource(R.drawable.camera);
			iv_two.setImageResource(R.drawable.camera);
			iv_one.setOnClickListener(myListener);
			iv_two.setOnClickListener(myListener);
			setData(barcode_string);
		}
		btn_retake.setOnClickListener(myListener);
		btn_add.setOnClickListener(myListener);
		btn_save.setOnClickListener(myListener);
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		// onBack(getActivity());
	}

	@Override
	public void onStart() {

		super.onStart();
	}

	View.OnClickListener myListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {

			case R.id.btn_retake:

				if (btn_retake.getText().toString().equalsIgnoreCase("Back")) {
					MainActivity.getMainScreenActivity().onBackPressed();
				} else {
					callBarcode();
				}

				break;
			case R.id.btn_add:
				if (btn_add.getText().toString().equalsIgnoreCase("Edit Assest")) {
					btn_add.setText("Add Photo");
					btn_save.setText("Save Asset");
					iv_one.setOnClickListener(myListener);
					iv_two.setOnClickListener(myListener);
					edt_remark.setEnabled(true);

				} else {
					if (null != bitmap_one & null != bitmap_two)
						new ShowAlertInformation(getActivity()).showDialog("Error", "Please delete some photo to add new photo.");
					else {
						Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(cameraIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
					}
				}

				break;
			case R.id.btn_save:

				if (btn_save.getText().toString().equalsIgnoreCase("Delete Assest")) {
					showDialogDelete();
				} else {
					ScanItemDto scanItemDto = new ScanItemDto();
					scanItemDto.setProfitcenter(tv_profit_center.getText().toString().trim());
					scanItemDto.setCompanycode(tv_company_code.getText().toString().trim());
					scanItemDto.setAssetsno(tv_assets_no.getText().toString().trim());
					scanItemDto.setSubno(tv_sub_number.getText().toString().trim());
					scanItemDto.setScandatetime(tv_scan_datetime.getText().toString().trim());
					scanItemDto.setGpscoordinate(tv_gps_coordinate.getText().toString().trim());
					scanItemDto.setComments(edt_remark.getText().toString().trim());
					scanItemDto.setImageOne(getByte(bitmap_one));
					scanItemDto.setImageTwo(getByte(bitmap_two));
					if (null != scanItemDtoB)
						scanItemDto.setUniqueId(scanItemDtoB.getUniqueId());
					new DataHelperClass(getActivity()).Add_SCAN_DATA(scanItemDto);
					DialogInterface.OnClickListener doc = new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							MainActivity.getMainScreenActivity().onBackPressed();
							dialog.dismiss();
						}
					};

					if (null != scanItemDtoB) {
						new ShowAlertInformation(getActivity()).showDialog(
								getActivity().getString(R.string.scan_update_title),
								getActivity().getString(R.string.scan_update_msg), doc);

					} else {
						new ShowAlertInformation(getActivity()).showDialog(
								getActivity().getString(R.string.scan_save_title),
								getActivity().getString(R.string.scan_save_msg), doc);

					}

				}
				break;
			case R.id.iv_one:
				if (null != bitmap_one)
					showDialog(iv_one);
				break;
			case R.id.iv_two:
				if (null != bitmap_two)
					showDialog(iv_two);
				break;

			default:
				break;
			}

		}
	};

	private byte[] getByte(Bitmap photo) {
		byte[] byteArray = null;
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byteArray = stream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return byteArray;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm a");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void showDialog(final ImageView view) {
		new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Delete Image")
				.setCancelable(false).setMessage("Are you sure you want to delete this image?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (view.getId() == R.id.iv_one) {
							bitmap_one = null;
						} else {
							bitmap_two = null;
						}
						view.setImageResource(R.drawable.camera);
						dialog.dismiss();
					}
				}).setNegativeButton("No", null).show();
	}

	public void showDialogDelete() {
		new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false)
				.setTitle(getString(R.string.delete_title)).setMessage(getString(R.string.delete_msg))
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						DataHelperClass DHC = new DataHelperClass(getActivity());
						DHC.deleteRecord(scanItemDtoB.getUniqueId());
						MainActivity.getMainScreenActivity().onBackPressed();
						dialog.dismiss();
					}
				}).setNegativeButton("No", null).show();
	}

	private void setData(String data) {
		tv_scan_datetime.setText(getDateTime());
		String[] out = data.split("-");
		if (out.length == 1) {
			tv_company_code.setText(out[0]);
		} else if (out.length == 2) {
			tv_company_code.setText(out[0]);
			tv_assets_no.setText(out[1]);
		} else if (out.length == 3) {
			tv_company_code.setText(out[0]);
			tv_assets_no.setText(out[1]);
			tv_sub_number.setText(out[2]);
		}
		tv_profit_center.setText("----");

		if (null != MainActivity.getMainScreenActivity().gps)
			tv_gps_coordinate.setText(MainActivity.getMainScreenActivity().gps.getLatitude() + ","
					+ MainActivity.getMainScreenActivity().gps.getLongitude());
		else
			tv_gps_coordinate.setText("0.0,0.0");

	}
}
