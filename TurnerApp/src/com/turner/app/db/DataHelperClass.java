package com.turner.app.db;

import java.util.ArrayList;

import com.turner.app.pojos.ScanItemDto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataHelperClass {
	private Context mContext;

	public DataHelperClass(Context con) {
		mContext = con;
	}

	public void Add_SCAN_DATA(ScanItemDto scanItemDto) {
		DBOpenHelperClass DBOHC = DBOpenHelperClass.getSharedObject(mContext);
		SQLiteDatabase SQDB = DBOHC.getWritableDatabase();
		ContentValues values = new ContentValues();
	
		values.put(DBOHC.COLUMN_COMMENT, scanItemDto.getComments());
		values.put(DBOHC.COLUMN_IMAGE_1, scanItemDto.getImageOne());
		values.put(DBOHC.COLUMN_IMAGE_2, scanItemDto.getImageTwo());
		SQDB.beginTransaction();
		try {
			if(!isRecordExist(scanItemDto.getUniqueId())){
				values.put(DBOHC.COLUMN_PROFIT_CENTER, scanItemDto.getProfitcenter());
				values.put(DBOHC.COLUMN_COMPANY_CODE, scanItemDto.getCompanycode());
				values.put(DBOHC.COLUMN_ASSETS_NO, scanItemDto.getAssetsno());
				values.put(DBOHC.COLUMN_SUB_NO, scanItemDto.getSubno());
				values.put(DBOHC.COLUMN_SCAN_DATE_TIME, scanItemDto.getScandatetime());
				values.put(DBOHC.COLUMN_GPS_COORDINATE, scanItemDto.getGpscoordinate());
				values.put(DBOHC.COLUMN_STATUS, "Pending");
				values.put(DBOHC.COLUMN_ADDITIONAL_1, "");
				values.put(DBOHC.COLUMN_ADDITIONAL_2, "");
				values.put(DBOHC.COLUMN_ADDITIONAL_3, "");
			SQDB.insert(DBOHC.TABLE_SCAN_DETAIL, null, values);
			}
			else{
				SQDB.update(DBOHC.TABLE_SCAN_DETAIL, values, DBOHC.COLUMN_UNIQUE_ID  + " = '" + scanItemDto.getUniqueId() + "'",null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		SQDB.setTransactionSuccessful();
		SQDB.endTransaction();

	}

	public ArrayList<ScanItemDto> getScanData() {
		{
			DBOpenHelperClass DBOHC = DBOpenHelperClass.getSharedObject(mContext);
			SQLiteDatabase SQDB = DBOHC.getWritableDatabase();
			ArrayList<ScanItemDto> scanList = new ArrayList<ScanItemDto>();
			String myQuery = "SELECT  * FROM " + DBOHC.TABLE_SCAN_DETAIL;
			// + " ORDER BY NOTIFICATION_ID DESC";
			try {
				Cursor cursor = SQDB.rawQuery(myQuery, null);
				if (cursor != null) {
					if (cursor.moveToFirst()) {
						do {
							try {
								ScanItemDto scanItemDto = new ScanItemDto();
								scanItemDto.setUniqueId(cursor.getInt(0));
								scanItemDto.setProfitcenter(cursor.getString(1));
								scanItemDto.setCompanycode(cursor.getString(2));
								scanItemDto.setAssetsno(cursor.getString(3));
								scanItemDto.setSubno(cursor.getString(4));
								scanItemDto.setScandatetime(cursor.getString(5));
								scanItemDto.setGpscoordinate(cursor.getString(6));
								scanItemDto.setComments(cursor.getString(7));
								scanItemDto.setImageOne(cursor.getBlob(8));
								scanItemDto.setImageTwo(cursor.getBlob(9));
								scanItemDto.setStatus(cursor.getString(10));

								scanList.add(scanItemDto);
							} catch (Exception e) {
								Log.d("DB_EXCEPTION" + "OBJ_NOT : ", e.getMessage());
							}
						} while (cursor.moveToNext());
					}
				}
				cursor.close();
				// SQDB.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return scanList;
		}

	}

	public boolean isRecordExist() {
		boolean isexit = false;
		String selectQuery = "SELECT * FROM SCAN_DETAILS ";
		DBOpenHelperClass DBOHC = DBOpenHelperClass.getSharedObject(mContext);
		SQLiteDatabase db = DBOHC.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		isexit = cursor.getCount() > 0 ? true : false;
		if (null != cursor)
			cursor.close();

		return isexit;

	}
	
	public boolean isRecordExist(int unique_id){
		boolean isexit = false;
		DBOpenHelperClass DBOHC = DBOpenHelperClass.getSharedObject(mContext);
		SQLiteDatabase db = DBOHC.getWritableDatabase();
		
		Cursor cursor = null;
		 String sql ="SELECT * FROM SCAN_DETAILS WHERE UNIQUE_ID="+unique_id; 
		 cursor= db.rawQuery(sql,null);

		 isexit = cursor.getCount() > 0 ? true : false;
			if (null != cursor)
				cursor.close();

			return isexit;
		
	}

	public boolean deleteRecord(int name) {
		DBOpenHelperClass DBOHC = DBOpenHelperClass.getSharedObject(mContext);
		SQLiteDatabase db = DBOHC.getWritableDatabase();
		return db.delete(DBOHC.TABLE_SCAN_DETAIL, DBOHC.COLUMN_UNIQUE_ID + "=" + name, null) > 0;
	}
	
	
}
