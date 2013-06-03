package br.usp.ime.bandejaousp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewMapActivity extends Activity {
	
	private GoogleMap map;
	private String names[];
	private double latitudes[];
	private double longitudes[];

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_map);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();
		
		names = new String[] { "Central", "Física", "Químicas", "Prefeitura" };
		
		latitudes = new double[] {
				 -23.559788,
				 -23.560604,
				 -23.563712,
				 -23.559483
		};
		
		longitudes = new double[] {
				-46.721797,
				-46.73552,
				-46.725606,
				-46.740025
		};
		
		focusOnCampus();
		markRestaurantsOnMap();
	}
	
	private void focusOnCampus() {
		LatLng latLgnUsuario = new LatLng(-23.560052, -46.730926);
		CameraUpdate moveCameraToUsr = CameraUpdateFactory
				.newLatLng(latLgnUsuario);
		CameraUpdate zoomCamera = CameraUpdateFactory.zoomBy(12);
		map.moveCamera(moveCameraToUsr);
		map.moveCamera(zoomCamera);
	}
	
	private void markRestaurantsOnMap() {
		for (int i = 0; i < names.length; i++) {
			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions.title(names[i]);
			markerOptions.position(new LatLng(latitudes[i], longitudes[i]));
			map.addMarker(markerOptions);
		}
	}

}
