package com.retail.store.discounts.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.store.discounts.payload.DiscountDto;
import com.retail.store.discounts.service.DiscountService;


@RestController
@RequestMapping("api/discounts")
public class DiscountController {
	
	@Autowired
    private DiscountService discountService;

	@PostMapping
	public ResponseEntity<BigDecimal> createDiscount(@RequestBody DiscountDto discount) {
		
		return new ResponseEntity<>(
				discountService.discountCalculation(discount.getUser(), discount.getBill()),
				HttpStatus.OK
			);
	}
	
}
