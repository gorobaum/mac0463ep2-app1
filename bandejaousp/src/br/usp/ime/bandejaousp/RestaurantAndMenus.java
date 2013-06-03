package br.usp.ime.bandejaousp;

public class RestaurantAndMenus {
	
	private Restaurant restaurant;
	private Menu menus[];
	
	RestaurantAndMenus() {
		this(null, null);
	}
	
	RestaurantAndMenus(Restaurant restaurant, Menu menus[]) {
		super();
		this.restaurant = restaurant;
		this.menus = menus;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public Menu[] getMenus() {
		return menus;
	}
	
}
