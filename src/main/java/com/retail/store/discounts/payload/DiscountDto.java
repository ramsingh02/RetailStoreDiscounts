package com.retail.store.discounts.payload;

import com.retail.store.discounts.entity.Bill;
import com.retail.store.discounts.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {

	private User user;
    private Bill bill;
    
}
