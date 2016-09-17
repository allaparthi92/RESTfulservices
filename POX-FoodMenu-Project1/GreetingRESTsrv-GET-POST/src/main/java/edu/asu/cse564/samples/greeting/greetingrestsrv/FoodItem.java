package edu.asu.cse564.samples.greeting.greetingrestsrv;

public class FoodItem {
	
	private String country;
	
	private Integer id;
	
	private String name;
	
	public FoodItem() {
		super();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	private String description;
	
	private String category;
	
	private double price;

	public FoodItem(String country, Integer id, String name,
			String description, String category, double price) {
		super();
		this.country = country;
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
	}
	
}
