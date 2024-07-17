package com.retail.store.discounts.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retail.store.discounts.entity.Bill;
import com.retail.store.discounts.entity.Item;
import com.retail.store.discounts.entity.ItemType;
import com.retail.store.discounts.entity.User;
import com.retail.store.discounts.entity.UserType;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class DiscountServiceTest {
	
	@Mock
	DiscountService discountService;
	
	@DisplayName("JUnit test for discount calculation method")
    @Test
    public void discountCalculation() {
		
		User user = new User();
		user.setType(UserType.EMPLOYEE);		
		user.setRegisterDate(LocalDate.parse("2014-02-14"));
		
		Bill bill = new Bill();
		List<Item> items = new ArrayList<>();
		items.add(new Item(ItemType.GROCERY, new BigDecimal(3.7)));
		items.add(new Item(ItemType.GROCERY, new BigDecimal(450)));
		items.add(new Item(ItemType.GROCERY, new BigDecimal(7.8)));				
		bill.setItems(items);
		
	}
}
