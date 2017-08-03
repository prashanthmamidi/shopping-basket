package org.rbc.shopping;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.rbc.shopping.exceptions.InvalidItemInBasketException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ShoppingBasketTest {

    private ShoppingBasket shoppingBasket = new ShoppingBasket();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void evaluateBasket_returnsZeroAmount_givenEmptyBasket() throws Exception {
        BigDecimal actualAmount = shoppingBasket.evaluateBasket(Collections.<String>emptyList());

        assertThat(actualAmount, is(value(0.00)));
    }

    @Test
    public void evaluateBasket_throwsInvalidItemException_givenBasketHasInvalidItem() throws Exception {
        exception.expect(InvalidItemInBasketException.class);
        exception.expectMessage("Invalid item in the basket");
        shoppingBasket.evaluateBasket(singletonList("Blueberry"));
    }

    @Test
    public void evaluateBasket_returnsBasketCost_givenOneItemEach() throws Exception {
        BigDecimal actualCost = shoppingBasket.evaluateBasket(asList("Banana", "Orange", "Apple", "Lemon", "Peache"));
        assertThat(actualCost, is(value(1.40)));
    }

    @Test
    public void evaluateBasket_returnsBasketCost_givenOnlyOneItemWithQuantiyOne() throws Exception {
        BigDecimal actualCost = shoppingBasket.evaluateBasket(singletonList("Banana"));
        assertThat(actualCost, is(value(0.25)));
    }

    @Test
    public void evaluateBasket_returnsBasketCost_givenOnlyOneItemWithMultipleQty() throws Exception {
        BigDecimal actualCost = shoppingBasket.evaluateBasket(asList("Apple", "Apple", "Apple"));
        assertThat(actualCost, is(value(1.50)));
    }

    @Test
    public void evaluateBasket_returnsBasketCost_givenMultipleItemsWithMultipleQty() throws Exception {
        BigDecimal actualCost = shoppingBasket.evaluateBasket(asList("Apple", "Apple", "Banana", "Banana", "Peache", "Peache", "Orange", "Orange" ));
        assertThat(actualCost, is(value(2.40)));
    }

    private BigDecimal value(double value) {
        return valueOf(value).setScale(2, ROUND_HALF_UP);
    }
}
