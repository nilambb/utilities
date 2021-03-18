package com.nilam.camel.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartDto {
	private String item;
	private Integer id;
	private List<CartDto> data = new ArrayList<>();

	public CartDto() {

	}

	public CartDto(String item, Integer id) {
		this.item = item;
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<CartDto> getData() {
		return data;
	}

	public void setData(List<CartDto> data) {
		this.data = data;
	}

	public List<CartDto> getItems() {
		return data;
	}

	public CartDto addItem(CartDto cartDto) {
		data.add(cartDto);
		return cartDto;
	}

	public String removeItem(int itemId) {
		data = data.stream().filter(p -> p.getId() != itemId).collect(Collectors.toList());
		System.out.println("After deleting the item");
		for (CartDto cartDto : data) {
			System.out.println(cartDto.toString());
		}
		return "Done";
	}
	
	@Override
	public String toString() {
		return "id = " + this.id + " item = " + this.item;
	}
	
	public CartDto getItemById(int itemId) {
		List<CartDto> filteredData = data.stream().filter(p -> p.getId() == itemId).collect(Collectors.toList());
		if(null != filteredData && !filteredData.isEmpty()) {
			System.out.println(filteredData.get(0));
			return filteredData.get(0);
		} else {
			System.out.println("returning the null..................");
			return null;
		}
	}

}
