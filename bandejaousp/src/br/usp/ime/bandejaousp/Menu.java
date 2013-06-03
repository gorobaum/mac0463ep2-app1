package br.usp.ime.bandejaousp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Menu {
	
	private int id;
	private int restaurant_id;
	private int meal_id;
	private String options;
	private String kcal;
	private String day;
	
	private Date date = null;
	
	public Menu() {
		this(0, 0, 0, null, null, null);
	}

	public Menu(int id, int restaurant_id, int meal_id, String options,
			String kcal, String day) {
		super();
		this.id = id;
		this.restaurant_id = restaurant_id;
		this.meal_id = meal_id;
		this.options = options;
		this.kcal = kcal;
		this.day = day;
	}

	public int getId() {
		return id;
	}

	public int getRestaurant_id() {
		return restaurant_id;
	}

	public int getMeal_id() {
		return meal_id;
	}

	public String getOptions() {
		return options;
	}

	public String getKcal() {
		return kcal;
	}

	public Date getDate() {
		if (date == null)
			date = dateFromString(day);
		return date;
	}

	private Date dateFromString(String str) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		try {
			date = format.parse(str);
			return date;
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDateAsString() {
		if (date == null) {
			date = getDate();
		}
		DateFormat format = new SimpleDateFormat("EEEE, dd/MM/yyyy", Locale.getDefault());
		return format.format(date);
	}

}
