package com.retail.store.discounts.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.retail.store.discounts.entity.Bill;
import com.retail.store.discounts.entity.Item;
import com.retail.store.discounts.entity.ItemType;
import com.retail.store.discounts.entity.User;
import com.retail.store.discounts.entity.UserType;
import com.retail.store.discounts.service.DiscountService;
import com.retail.store.discounts.service.DiscountServiceImpl;

@SpringBootTest
public class AppUtilsTest {

	@Test
    public void testCalculateTotal_GroceriesOnly() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));

        AppUtils util = new AppUtils();
        BigDecimal total = util.calculateTotalPerType(items, ItemType.GROCERY);
        assertEquals(300.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateTotalNonGroceriesOnly() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(ItemType.CLOTHES, new BigDecimal(100.0)));
        items.add(new Item(ItemType.OTHER, new BigDecimal(100.0)));
        items.add(new Item(ItemType.CLOTHES, new BigDecimal(100.0)));

        AppUtils util = new AppUtils();
        BigDecimal total = util.calculateTotal(items);
        assertEquals(300.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateTotalMix() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(ItemType.CLOTHES, new BigDecimal(100.0)));
        items.add(new Item(ItemType.OTHER, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));

        AppUtils util = new AppUtils();
        BigDecimal total = util.calculateTotalPerType(items, ItemType.GROCERY);
        assertEquals(300.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_10pct() {
    	AppUtils util = new AppUtils();
        BigDecimal total = util.calculateDiscount(new BigDecimal(1000), new BigDecimal(0.1));
        assertEquals(900.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_50pct() {
    	AppUtils util = new AppUtils();
        BigDecimal total = util.calculateDiscount(new BigDecimal(1000), new BigDecimal(0.5));
        assertEquals(500.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_0pct() {
    	AppUtils util = new AppUtils();
        BigDecimal total = util.calculateDiscount(new BigDecimal(1000),  new BigDecimal(0.0));
        assertEquals(1000.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_100pct() {
    	AppUtils util = new AppUtils();
        BigDecimal total = util.calculateDiscount(new BigDecimal(1000),  new BigDecimal(1.0));
        assertEquals(0.0, total.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_affiliate() {
        User user = new User(UserType.AFFILIATE, LocalDate.parse("2014-02-14"));
        AppUtils util = new AppUtils();
        BigDecimal discount = util.getUserDiscount(user);
        assertEquals(0.1, discount.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_employee() {
        User user = new User(UserType.EMPLOYEE, LocalDate.now());
        AppUtils util = new AppUtils();
        BigDecimal discount = util.getUserDiscount(user);
        assertEquals(0.3, discount.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_customer_old() {
        LocalDate joinDate = LocalDate.of(2016, 2, 23);
        User user = new User(UserType.CUSTOMER, joinDate);
        AppUtils util = new AppUtils();
        BigDecimal discount = util.getUserDiscount(user);
        assertEquals(0.05, discount.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_customer_new() {
        User user = new User(UserType.CUSTOMER, LocalDate.now());
        AppUtils util = new AppUtils();
        BigDecimal discount = util.getUserDiscount(user);
        assertEquals(0.0, discount.doubleValue(), 0);
    }

    @Test
    public void testIsCustomerSince() {
    	AppUtils util = new AppUtils();
        LocalDate joinDate = LocalDate.now();
        boolean isTwoYearsJoined = util.isCustomerSince(joinDate, 2);
        assertFalse(isTwoYearsJoined);
    }

    @Test
    public void testIsCustomerSince_1year() {
    	AppUtils util = new AppUtils();
        LocalDate joinDate = LocalDate.now().minusYears(1);
        boolean isTwoYearsJoined = util.isCustomerSince(joinDate, 2);
        assertFalse(isTwoYearsJoined);
    }

    @Test
    public void testIsCustomerSince_2years() {
    	AppUtils util = new AppUtils();
        LocalDate joinDate = LocalDate.now().minusYears(2);
        boolean isTwoYearsJoined = util.isCustomerSince(joinDate, 2);
        assertTrue(isTwoYearsJoined);
    }

    @Test
    public void testIsCustomerSince_3years() {
    	AppUtils util = new AppUtils();
        LocalDate joinDate = LocalDate.now().minusYears(3);
        boolean isTwoYearsJoined = util.isCustomerSince(joinDate, 2);
        assertTrue(isTwoYearsJoined);
    }

    @Test
    public void testCalculateBillsDiscount() {
    	AppUtils util = new AppUtils();
        BigDecimal amount = util.calculateBillsDiscount(new BigDecimal(1000),  new BigDecimal(100),  new BigDecimal(5));
        assertEquals(50, amount.doubleValue(), 0);
    }

    @Test
    public void testCalculateBillsDiscount_2() {
    	AppUtils util = new AppUtils();
        BigDecimal amount = util.calculateBillsDiscount(new BigDecimal(1000),  new BigDecimal(50),  new BigDecimal(5));
        assertEquals(100, amount.doubleValue(), 0);
    }

    @Test
    public void testCalculateBillsDiscount_3() {
    	AppUtils util = new AppUtils();
        BigDecimal amount = util.calculateBillsDiscount( new BigDecimal(5632), new BigDecimal(100), new BigDecimal(5));
        assertEquals(280, amount.doubleValue(), 0);
    }

    @Test
	public void testDiscountServiceCalculate() {
		List<Item> items = new ArrayList<Item>();
		items.add(new Item(ItemType.GROCERY, new BigDecimal(50.0)));
		items.add(new Item(ItemType.GROCERY, new BigDecimal(200.0)));
		items.add(new Item(ItemType.GROCERY, new BigDecimal(10.0)));

		Bill bill = new Bill();
		bill.setItems(items);

		DiscountService discountService = new DiscountServiceImpl();

		discountService.discountCalculation(new User(UserType.CUSTOMER, LocalDate.now()), bill);
		AppUtils util = new AppUtils();
		BigDecimal amount = util.calculateBillsDiscount(new BigDecimal(5632), new BigDecimal(100), new BigDecimal(5));
		assertEquals(280, amount.doubleValue(), 0);
	}
}
