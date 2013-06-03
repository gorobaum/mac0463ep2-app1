package br.usp.ime.bandejaousp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

	public static final String AUTO_ID = "_id";
	public static final String MENU_ID = "given_id";
	public static final String MENU_MEAL_ID = "meal_id";
	public static final String MENU_OPTIONS = "options";
	public static final String MENU_KCAL = "kcal";
	public static final String MENU_DATE = "date";
	public static final String MENU_RESTAURANT_ID = "restaurant_id";
	
	public static final String RESTAURANT_ID = "_id";
	public static final String RESTAURANT_NAME = "name";
	public static final String RESTAURANT_ADDRESS = "address";
	public static final String RESTAURANT_TEL = "telephone";

	private static final String DATABASE_NAME = "bandejaousp";
	private static final String DATABASE_MENU_TABLE = "menus";
	private static final String DATABASE_RESTAURANT_TABLE = "restaurant";
	private static final int DATABASE_VERSION = 1;
	private static final String TAG = "DBAdapter";

	private static final String DATABASE_CREATE_RESTAURANT = "CREATE TABLE "
			+ DATABASE_RESTAURANT_TABLE
			+ " ("
				+ RESTAURANT_ID  + " INTEGER PRIMARY KEY, "
				+ RESTAURANT_NAME + " TEXT NOT NULL, "
				+ RESTAURANT_ADDRESS + " TEXT NOT NULL, "
				+ RESTAURANT_TEL + " TEXT NOT NULL);";
	
	private static final String DATABASE_CREATE_MENU = "CREATE TABLE "
			+ DATABASE_MENU_TABLE
			+ " ("
				+ AUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ MENU_ID + " INTEGER, "
				+ MENU_OPTIONS + " TEXT NOT NULL, "
				+ MENU_KCAL + " TEXT NOT NULL, "
				+ MENU_DATE + " TEXT NOT NULL, "
				+ MENU_MEAL_ID + " INTEGER, "
				+ MENU_RESTAURANT_ID + " INTEGER);";
	

	private final Context context;

	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE_RESTAURANT);
			db.execSQL(DATABASE_CREATE_MENU);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS titles");
			onCreate(db);
		}
	}

	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		DBHelper.close();
	}

	public List<Long> insertMenuOptions(Menu menus[]) {
		List<Long> ids = new ArrayList<Long>();
		for (Menu menu : menus) {
			ids.add(insertMenuOption(menu));
		}
		return ids;
	}

	public long insertMenuOption(Menu menu) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(MENU_ID, menu.getId());
		initialValues.put(MENU_KCAL, menu.getKcal());
		initialValues.put(MENU_MEAL_ID, menu.getMeal_id());
		initialValues.put(MENU_OPTIONS, menu.getOptions());
		initialValues.put(MENU_DATE, menu.getDateAsString());
		initialValues.put(MENU_RESTAURANT_ID, menu.getRestaurant_id());
		return db.insert(DATABASE_MENU_TABLE, null, initialValues);
	}
	
	public long insertRestaurant(Restaurant restaurant) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(RESTAURANT_ID, restaurant.getId());
		initialValues.put(RESTAURANT_ADDRESS, restaurant.getAddress());
		initialValues.put(RESTAURANT_TEL, restaurant.getTel());
		initialValues.put(RESTAURANT_NAME, restaurant.getName());
		return db.insert(DATABASE_RESTAURANT_TABLE, null, initialValues);
	}
	
	public Cursor getAllRestaurants() {
		Cursor cursor = db.query(DATABASE_RESTAURANT_TABLE,
				new String[] { 	RESTAURANT_ID,
								RESTAURANT_ADDRESS,
								RESTAURANT_TEL,
								RESTAURANT_NAME },
				null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	public boolean deleteAllInfo() {
		if (db.delete(DATABASE_RESTAURANT_TABLE, null, null) > 0
			&& db.delete(DATABASE_MENU_TABLE, null, null) > 0) {
			return true;
		}
		return false;
	}

	public boolean isEmpty() {
		Cursor cursor = getAllRestaurants();
		return !cursor.moveToFirst();
	}

}