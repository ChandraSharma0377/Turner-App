package com.turner.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.turner.app.R;
import com.turner.app.adapters.HelpListAdapter;
import com.turner.app.main.MainActivity;
import com.turner.app.pojos.HelpListItemDto;

import java.util.ArrayList;

public class HelpListFragment extends Fragment {
    private ListView listview;
    private HelpListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {

        View view = inflater.inflate(R.layout.frag_helplist, container, false);
        listview = (ListView) view.findViewById(R.id.listview);

        ArrayList<HelpListItemDto> helpList = new ArrayList<HelpListItemDto>();
        helpList.add(new HelpListItemDto(R.drawable.about, getString(R.string.about_the_app)));
        helpList.add(new HelpListItemDto(R.drawable.scan_asset, getString(R.string.scan_assest)));
        helpList.add(new HelpListItemDto(R.drawable.server_small, getString(R.string.submit_assest)));
        helpList.add(new HelpListItemDto(R.drawable.contact, getString(R.string.contact_us)));
        helpList.add(new HelpListItemDto(R.drawable.term, getString(R.string.term_of_use)));

        adapter = new HelpListAdapter(getActivity(), helpList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //	Bundle bundle = new Bundle();
                switch (position) {
                    case 0:
                        MainActivity.redirectToFragment(new WebViewFragment(0));
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;

                    case 4:
                        MainActivity.redirectToFragment(new WebViewFragment(2));
                        break;
                    default:
                        break;
                }

            }
        });
        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
        MainActivity.getMainScreenActivity().actionBarTitle.setText("Help");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

}
