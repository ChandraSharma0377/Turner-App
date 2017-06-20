package com.turner.app.adapters;

import java.util.ArrayList;

import com.turner.app.R;
import com.turner.app.pojos.ScanItemDto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class SubmitAssetsAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<ScanItemDto> scanItemDtos;

	public SubmitAssetsAdapter(Context context, ArrayList<ScanItemDto> helpListItemDtos) {
		this.context = context;
		this.scanItemDtos = helpListItemDtos;
	}

	@Override
	public int getCount() {
		return scanItemDtos.size();
	}

	@Override
	public Object getItem(int position) {
		return scanItemDtos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public ArrayList<ScanItemDto> getData() {
		return scanItemDtos;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.submit_assets_list_item, parent, false);
			holder = new ViewHolder();
			holder.cb_submit = (CheckBox) convertView.findViewById(R.id.cb_submit);

			// holder.tv_profit_center = (TextView) convertView
			// .findViewById(R.id.tv_profit_center);
			holder.tv_company_code = (TextView) convertView.findViewById(R.id.tv_company_code);
			holder.tv_scan_datetime = (TextView) convertView.findViewById(R.id.tv_scan_datetime);
			holder.tv_assets_no = (TextView) convertView.findViewById(R.id.tv_assets_no);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.cb_submit.setText(scanItemDtos.get(position).getProfitcenter());
		holder.tv_company_code.setText(scanItemDtos.get(position).getCompanycode());
		holder.tv_assets_no.setText(scanItemDtos.get(position).getAssetsno());
		holder.tv_scan_datetime.setText(scanItemDtos.get(position).getScandatetime());
		holder.cb_submit.setChecked(scanItemDtos.get(position).isIsselect());
		setCheckChangeListener(holder, position);

		return convertView;
	}

	static class ViewHolder {
		CheckBox cb_submit;
		// TextView tv_profit_center;
		TextView tv_company_code;
		TextView tv_assets_no;
		TextView tv_scan_datetime;
	}

	private void setCheckChangeListener(final ViewHolder holder, final int position) {
		holder.cb_submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((CheckBox) v).isChecked()) {
					holder.cb_submit.setChecked(true);
					scanItemDtos.get(position).setIsselect(true);
				} else {
					holder.cb_submit.setChecked(false);
					scanItemDtos.get(position).setIsselect(false);
				}
			}
		});
	}
}
