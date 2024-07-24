package com.retail.store.discounts.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Bill {

	private List<Item> items;
	
}
