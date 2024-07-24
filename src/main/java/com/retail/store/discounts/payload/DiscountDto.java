package com.retail.store.discounts.payload;

import com.retail.store.discounts.entity.Bill;
import com.retail.store.discounts.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDto {

	private User user;
    private Bill bill;
    
}
