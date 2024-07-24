package com.retail.store.discounts.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

import com.retail.store.discounts.entity.Bill;
import com.retail.store.discounts.entity.Item;
import com.retail.store.discounts.entity.ItemType;
import com.retail.store.discounts.entity.User;
import com.retail.store.discounts.entity.UserType;
import com.retail.store.discounts.payload.DiscountDto;
import com.retail.store.discounts.service.DiscountService;

@ExtendWith(MockitoExtension.class)
public class TestDiscountController {

	@InjectMocks
	DiscountController discountController;

	@Mock
	DiscountService discountService;
	
	MockMvc mockMvc;

	private List<Item> listOfItems;
	private Item item;
	private User user;
	private Bill bill;
	private DiscountDto discountDto;
			
	@BeforeEach
	void constructObjects() {
		
		listOfItems = new ArrayList<Item>();
		
		item = new Item();
		item.setType(ItemType.GROCERY);
		item.setPrice(new BigDecimal(3.7));
		listOfItems.add(item);

		item = new Item();
		item.setType(ItemType.GROCERY);
		item.setPrice(new BigDecimal(450));
		listOfItems.add(item);

		item = new Item();
		item.setType(ItemType.GROCERY);
		item.setPrice(new BigDecimal(7.8));
		listOfItems.add(item);

		user = new User();
		user.setType(UserType.EMPLOYEE);
		user.setRegisterDate(LocalDate.parse("2014-02-14"));
		
		bill = new Bill();
		bill.setItems(listOfItems);

		discountDto = new DiscountDto();
		discountDto.setUser(user);
		discountDto.setBill(bill);		
	}
		
	@Test
	public void createDiscount() throws Exception {
	
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(discountService.discountCalculation(user, bill)).thenReturn(new BigDecimal(441.5));

		ResponseEntity<BigDecimal> responseEntity = discountController.createDiscount(discountDto);

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

}
