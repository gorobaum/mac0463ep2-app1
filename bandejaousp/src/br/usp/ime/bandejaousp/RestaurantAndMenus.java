package br.usp.ime.bandejaousp;

public class RestaurantAndMenus {
	
	/* A estrutura da classe é totalmente definida pelo JSON do serviço do bandejão */
	
	/* Um "layer" em volta do restaurante e do menu, devido à estrutura do JSON do serviço */
	public class RestHolder {
		private Restaurant restaurant;
	}
	
	public class MenuHolder{
		private Menu menu;
	}
	
	private RestHolder restaurant;
	private MenuHolder menus[];
	private Menu actualMenus[] = null;
	
	RestaurantAndMenus() {
		this(null, null);
	}
	
	RestaurantAndMenus(RestHolder restaurant, MenuHolder menus[]) {
		super();
		this.restaurant = restaurant;
		this.menus = menus;
	}

	public Restaurant getRestaurant() {
		return restaurant.restaurant;
	}

	public Menu[] getMenus() {
		if (actualMenus == null)
			menusToActualMenus();
		return actualMenus;
	}
	
	private void menusToActualMenus() {
		actualMenus = new Menu[menus.length];
		for (int i = 0; i < menus.length; i++) {
			actualMenus[i] = menus[i].menu;
		}
	}
	
}
