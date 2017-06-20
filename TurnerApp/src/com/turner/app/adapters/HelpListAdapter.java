package com.turner.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.turner.app.R;
import com.turner.app.pojos.HelpListItemDto;

import java.util.ArrayList;

public class HelpListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<HelpListItemDto> helpListItemDtos;
	
	public HelpListAdapter(Context context, ArrayList<HelpListItemDto> helpListItemDtos){
		this.context = context;
		this.helpListItemDtos = helpListItemDtos;
	}

	@Override
	public int getCount() {
		return helpListItemDtos.size();
	}

	@Override
	public Object getItem(int position) {		
		return helpListItemDtos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.help_list_item, null);
        }
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.tv_title_help);

         
        imgIcon.setImageResource(helpListItemDtos.get(position).getIcon());
        txtTitle.setText(helpListItemDtos.get(position).getTitle());

        
        return convertView;
	}

}
