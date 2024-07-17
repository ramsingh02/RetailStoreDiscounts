package com.retail.store.discounts.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
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
			
	@Test
	public void createDiscount() throws Exception 
	{
		User user = new User();
		user.setType(UserType.EMPLOYEE);		
		user.setRegisterDate(LocalDate.parse("2014-02-14"));
		
		Bill bill = new Bill();
		List<Item> items = new ArrayList<>();
		items.add(new Item(ItemType.GROCERY, new BigDecimal(3.7)));
		items.add(new Item(ItemType.GROCERY, new BigDecimal(450)));
		items.add(new Item(ItemType.GROCERY, new BigDecimal(7.8)));				
		bill.setItems(items);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(discountService.discountCalculation(user, bill)).thenReturn(new BigDecimal(441.5));
        
        ResponseEntity<BigDecimal> responseEntity = discountController.createDiscount(new DiscountDto(user, bill));

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

}
