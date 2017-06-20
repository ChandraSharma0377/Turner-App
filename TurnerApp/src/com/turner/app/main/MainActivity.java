package com.turner.app.main;

import java.util.HashMap;
import java.util.Stack;

import com.turner.app.R;
import com.turner.app.db.DBOpenHelperClass;
import com.turner.app.fragment.LandingFragment;
import com.turner.app.fragment.LoginFragment;
import com.turner.app.helper.GPSTracker;
import com.turner.app.helper.NetworkHelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private Boolean exit = false;
	private static MainActivity mainActivity;
	private static NetworkHelper networkHelper;
	public static final String MyPREFERENCES = "AppPref";
	private static SharedPreferences sharedpreferences;

	protected Fragment mFrag;
	protected Fragment cFrag, rootFragment;
	private HashMap<String, Stack<Fragment>> mFragmentsStack;
	public TextView actionBarTitle;

	private final String IS_LOGIN = "IsLoggedIn";
	private final String KEY_EMAIL = "email";
	private final String KEY_ACTIVE_USER_ID = "userName";
	private final String KEY_USER_ID = "userID";
	protected static final String CONTENT_TAG = "contenFragments";
	public DBOpenHelperClass dbHandler;
//	public double latitude;
//	public double longitude;
	private int GPS_REQUEST = 55;

	public GPSTracker gps;

	public static MainActivity getMainScreenActivity() {
		return mainActivity;
	}

	public static NetworkHelper getNetworkHelper() {
		return networkHelper;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainActivity = this;
		networkHelper = new NetworkHelper(this);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		mFragmentsStack = new HashMap<String, Stack<Fragment>>();
		mFragmentsStack.put(CONTENT_TAG, new Stack<Fragment>());

		ActionBarSetup();
		dbHandler = DBOpenHelperClass.getSharedObject(this);
		if (isLoggedIn()) {
			changeNavigationContentFragment(new LandingFragment(), false);
		} else
			changeNavigationContentFragment(new LoginFragment(), false);

		onNewIntent(getIntent());

	}

	private void ActionBarSetup() {
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setCustomView(R.layout.custom_actionbar_view);
		getSupportActionBar().setElevation(0);
		View v = getSupportActionBar().getCustomView();

		actionBarTitle = (TextView) v.findViewById(R.id.title);

		Toolbar parent = (Toolbar) v.getParent();
		parent.setContentInsetsAbsolute(0, 0);
		// actionBarTitle.setText(getString(R.string.app_name));

	}

	public void changeNavigationContentFragment(Fragment frgm, boolean shouldAdd) {

		if (shouldAdd) {

			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			if (null != fm.findFragmentById(R.id.main_fragment))
				ft.hide(fm.findFragmentById(R.id.main_fragment));
			ft.add(R.id.main_fragment, frgm, frgm.getClass().getSimpleName());
			ft.addToBackStack(null);
			// ft.commitAllowingStateLoss();
			ft.commit();
			mFragmentsStack.get(CONTENT_TAG).push(frgm);
		} else {
			mFragmentsStack.get(CONTENT_TAG).clear();
			FragmentManager manager = getSupportFragmentManager();
			manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			manager.beginTransaction().replace(R.id.main_fragment, frgm).commitAllowingStateLoss();
			rootFragment = frgm;
		}

		cFrag = frgm;

	}

	// Checking exitFlag on Back press, if exitFlag is true removing all
	// fragments from back stack and exiting app.
	// if exitFlag is false then allowing default behavior. That is removing
	// current Fragment and loading previous
	// Fragment.
	@Override
	public void onBackPressed() {
		removeFragment();
	}

	public void removeFragment() {
		int statckSize = mFragmentsStack.get(CONTENT_TAG).size();
		if (statckSize == 0) {
			if (exit) {
				finish(); // finish activity
			} else {
				Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
				exit = true;
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						exit = false;
					}
				}, 3 * 1000);

			}

		} else {
			Fragment fragment;
			if (statckSize > 1)
				fragment = mFragmentsStack.get(CONTENT_TAG).elementAt(statckSize - 2);
			else
				fragment = mFragmentsStack.get(CONTENT_TAG).elementAt(statckSize - 1);
			mFragmentsStack.get(CONTENT_TAG).pop();
			FragmentManager fm = this.getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.detach(cFrag);
			ft.commitAllowingStateLoss();
			if (statckSize > 1) {
				cFrag = fragment;
				fragment.onResume();
			} else {
				cFrag = rootFragment;
				rootFragment.onResume();
			}
			super.onBackPressed();
		}

	}

	public static SharedPreferences getSharePreferance() {
		return sharedpreferences;
	}

	public String getActiveUserID() {
		return sharedpreferences.getString(KEY_ACTIVE_USER_ID, "");
	}

	public String getUserID() {
		return sharedpreferences.getString(KEY_USER_ID, "");
	}

	public boolean isLoggedIn() {
		return sharedpreferences.getBoolean(IS_LOGIN, false);
	}

	public void setSharPreferancename(String act_user_id, String userID, String mobileNo, boolean isLogin) {
		Editor editor = sharedpreferences.edit();
		editor.putString(KEY_ACTIVE_USER_ID, act_user_id);
		editor.putString(KEY_USER_ID, userID);
		editor.putString(KEY_EMAIL, mobileNo);
		editor.putBoolean(IS_LOGIN, isLogin);
		editor.commit();
	}

	public static void redirectToFragment(Fragment fragment) {
		Fragment VF = fragment;
		MainActivity.getMainScreenActivity().changeNavigationContentFragment(VF, true);

	}

	public void restartActivity() {
		Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	public void getLocation() {
		try {
			// create class object
			gps = new GPSTracker(MainActivity.this);

			// check if GPS enabled
			if (gps.canGetLocation()) {

//				latitude = gps.getLatitude();
//				longitude = gps.getLongitude();

				// \n is for new line
				// Toast.makeText(getApplicationContext(),
				// "Your Location is - \nLat: " + latitude + "\nLong: " +
				// longitude, Toast.LENGTH_LONG).show();
			} else {
				// can't get location
				// GPS or Network is not enabled
				// Ask user to enable GPS/network in settings
				showSettingsAlert();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("GPS settings");

		// Setting Dialog Message
		alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivityForResult(intent, GPS_REQUEST);
			}
		});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == GPS_REQUEST) {
			try {

				if (null != gps) {
					gps.getLocation();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
