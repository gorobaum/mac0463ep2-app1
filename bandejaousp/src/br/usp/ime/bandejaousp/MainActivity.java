package br.usp.ime.bandejaousp;

import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;

public class MainActivity extends Activity {
	
	String urls[] = new String[] {
			"http://uspservices.deusanyjunior.dj/bandejao/1.json",
			"http://uspservices.deusanyjunior.dj/bandejao/2.json",
			"http://uspservices.deusanyjunior.dj/bandejao/3.json",
			"http://uspservices.deusanyjunior.dj/bandejao/4.json"
	};
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		
		DBAdapter dbadapter = new DBAdapter(getApplicationContext());
		dbadapter.open();
		
		if(isOnline()) {
			showToast("Atualizando dados...");
			loadMenus(dbadapter);
			showToast("Dados atualizados com sucesso.");
		} else {
			if (dbadapter.isEmpty()) {
				showToast("Nenhuma conexão encontrada, e não há dados armazenados.");
			} else {
				Toast.makeText(
						getApplicationContext(),
						"Mostrando dados antigos... Conectar a internet para atualizar.",
						Toast.LENGTH_SHORT).show();
			}
		}
		
		Cursor cursor = dbadapter.getAllRestaurants();
		setAdapter(cursor);
	}
	
	private void loadMenus(DBAdapter dbadapter) {
		if (!dbadapter.isEmpty()) {
			dbadapter.deleteAllInfo();
		}
		
		JSONObject jobj;
		Gson gson = new Gson();
		
		for (int i = 0; i < urls.length; i++) {
			jobj = getJsonObject(urls[i]);
			RestaurantAndMenus newMenuObj;
			newMenuObj = gson.fromJson(jobj.toString(), RestaurantAndMenus.class);
			dbadapter.insertRestaurant(newMenuObj.getRestaurant());
			dbadapter.insertMenuOptions(newMenuObj.getMenus());
		}
	}

	private void showToast(String string) {
		Toast.makeText(getApplicationContext(), string,
				Toast.LENGTH_LONG).show();
	}

	private JSONObject getJsonObject(String url) {
		RequestJson requestJson = new RequestJson();
		requestJson.execute(url);
		JSONObject jsonObj = null;
		try {
			jsonObj = requestJson.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
	
	private void setAdapter(Cursor cursor) {
		String[] fromColumns = new String[] { DBAdapter.RESTAURANT_NAME };
		int[] toViews = new int[] { R.id.name };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.activity_main, cursor, fromColumns, toViews, 0);
		ListView listView = (ListView) findViewById(R.id.listViewRestaurantNames);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View view,
					int position, long id) {
				Intent intent = new Intent(view.getContext(),
						RestaurantInfoActivity.class);
				startActivityForResult(intent, 0);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return (netInfo != null) && netInfo.isConnectedOrConnecting();
	}
	
	public void goToMap(View view) {
		Intent intent = new Intent(view.getContext(), ViewMapActivity.class);
		startActivity(intent);
	}

}
