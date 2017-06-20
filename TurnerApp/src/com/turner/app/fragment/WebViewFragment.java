package com.turner.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.turner.app.R;

public class WebViewFragment extends Fragment {

    private WebView webView;

    private final int CASE_ABOUTUS = 0;
    private final int CASE_PRIVACY = 1;
    private final int CASE_TERM = 2;
    private int caseType = -1;

    public WebViewFragment() {
    }

    public WebViewFragment(int caseType) {
        this.caseType = caseType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lay_web, container, false);
        webView = (WebView) rootView.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        switch (caseType) {
            case CASE_ABOUTUS:
                webView.loadUrl("file:///android_asset/html/aboutus.html");
                break;
            case CASE_PRIVACY:
                webView.loadUrl("file:///android_asset/html/privacy.html");
                break;
            case CASE_TERM:
                webView.loadUrl("file:///android_asset/html/term.html");
                break;
            default:
                break;
        }

        return rootView;
    }

    @Override
    public void onResume() {

        super.onResume();
//        switch (caseType) {
//            case CASE_ABOUTUS:
//                MainActivity.getMainScreenActivity().setActionBarTitle("About Us");
//                break;
//            case CASE_PRIVACY:
//                MainActivity.getMainScreenActivity().setActionBarTitle("Privacy Policy");
//                break;
//            case CASE_TERM:
//                MainActivity.getMainScreenActivity().setActionBarTitle("Terms & Conditions");
//                break;
//            default:
//                break;
//        }

    }
}
