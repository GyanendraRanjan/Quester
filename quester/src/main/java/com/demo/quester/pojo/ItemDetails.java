package com.demo.quester.pojo;

public class ItemDetails {
	private String name;
	private int qty;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	@Override
	public String toString() {
		return "ItemDetails [name=" + name + ", qty=" + qty + "]";
	}

	
}
