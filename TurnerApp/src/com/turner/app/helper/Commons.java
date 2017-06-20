package com.turner.app.helper;

public class Commons {

	private static final String BASE_URL = "http://122.15.117.253:88/api/";
	public static final String LOGIN_URL = BASE_URL + "user/login";
	public static final String SUBMIT_DETAILS = BASE_URL + "scandetails/addscandetails";
	//http://122.15.117.253:88/api/scandetails/addscandetails
	public static boolean isValidEmail(CharSequence target) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}
}
