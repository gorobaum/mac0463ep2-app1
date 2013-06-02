package br.usp.ime.bandejaousp;

public class Menu {
	
	private int id;
	private int restaurant_id;
	private int meal_id;
	private String options;
	private String kcal;
	
	public Menu() {
		this(0, 0, 0, null, null);
	}

	public Menu(int id, int restaurant_id, int meal_id, String options,
			String kcal) {
		super();
		this.id = id;
		this.restaurant_id = restaurant_id;
		this.meal_id = meal_id;
		this.options = options;
		this.kcal = kcal;
	}

}
