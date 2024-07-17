package com.retail.store.discounts.service;

import java.math.BigDecimal;

import com.retail.store.discounts.entity.Bill;
import com.retail.store.discounts.entity.User;

public interface DiscountService {

	BigDecimal discountCalculation(User user, Bill bill);
}
