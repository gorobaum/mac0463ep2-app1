package br.usp.ime.bandejaousp;

public class Restaurant {

	private String name;
	private String address;
	private String tel;
	private int id;
	
	public Restaurant() {
		this(null, null, null, 0);
	}
	
	public Restaurant(String name, String address, String tel, int id) {
		super();
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getTel() {
		return tel;
	}

	public int getId() {
		return id;
	}
}
