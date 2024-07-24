package com.retail.store.discounts.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.retail.store.discounts.entity.Bill;
import com.retail.store.discounts.entity.ItemType;
import com.retail.store.discounts.entity.User;
import com.retail.store.discounts.utils.AppUtils;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Override
	public BigDecimal discountCalculation(User user, Bill bill) {
		
		AppUtils utils = new AppUtils();
		
        BigDecimal totalAmount = utils.calculateTotal(bill.getItems());
        BigDecimal groceryAmount = utils.calculateTotalPerType(bill.getItems(), ItemType.GROCERY);
        BigDecimal nonGroceryAmount = totalAmount.subtract(groceryAmount);
        BigDecimal userDiscount = utils.getUserDiscount(user);
        BigDecimal billsDiscount = utils.calculateBillsDiscount(totalAmount, new BigDecimal(100), new BigDecimal(5));
		if (nonGroceryAmount.compareTo(BigDecimal.ZERO) > 0) {
			nonGroceryAmount = utils.calculateDiscount(nonGroceryAmount, userDiscount);
		}

        BigDecimal finalAmount = (groceryAmount.add(nonGroceryAmount).subtract(billsDiscount));
		return finalAmount;
	}

}
