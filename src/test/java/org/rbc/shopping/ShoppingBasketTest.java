package org.rbc.shopping;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.rbc.shopping.exceptions.InvalidItemInShoppingBasketException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;
import static java.util.Collections.emptyMap;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ShoppingBasketTest {
    private ShoppingBasket shoppingBasket = new ShoppingBasket();
    private Map<String, Integer> shoppingItems = new HashMap<>();
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void evaluateBasket_returnsZeroAmount_givenEmptyBasket() throws Exception {
        BigDecimal actualAmount = shoppingBasket.evaluateBasket(emptyMap());
        assertThat(actualAmount, is(value(0.00)));
    }

    @Test
    public void evaluateBasket_throwsInvalidItemException_givenBasketHasInvalidItem() throws Exception {
        exception.expect(InvalidItemInShoppingBasketException.class);
        exception.expectMessage("Invalid item in the basket");
        shoppingItems.put("Blueberry", 1);
        shoppingBasket.evaluateBasket(shoppingItems);
    }

    @Test
    public void evaluateBasket_returnsBasketCost_givenOneItemEach() throws Exception {
        shoppingItems.put("Apple", 1);
        shoppingItems.put("Banana", 1);
        shoppingItems.put("Peach", 1);
        shoppingItems.put("Lemon", 1);
        shoppingItems.put("Orange", 1);
        BigDecimal actualCost = shoppingBasket.evaluateBasket(shoppingItems);
        assertThat(actualCost, is(value(1.40)));
    }

    @Test
    public void evaluateBasket_returnsBasketCost_givenOnlyOneItemWithQuantiyOne() throws Exception {
        shoppingItems.put("Banana", 1);
        BigDecimal actualCost = shoppingBasket.evaluateBasket(shoppingItems);
        assertThat(actualCost, is(value(0.25)));
    }

    @Test
    public void evaluateBasket_returnsBasketCost_givenOnlyOneItemWithMultipleQty() throws Exception {
        shoppingItems.put("Apple", 3);
        BigDecimal actualCost = shoppingBasket.evaluateBasket(shoppingItems);
        assertThat(actualCost, is(value(1.50)));
    }

    @Test
    public void evaluateBasket_returnsBasketCost_givenMultipleItemsWithMultipleQtys() throws Exception {
        shoppingItems.put("Apple", 2);
        shoppingItems.put("Banana", 6);
        shoppingItems.put("Peach", 4);
        shoppingItems.put("Orange", 5);
        BigDecimal actualCost = shoppingBasket.evaluateBasket(shoppingItems);
        assertThat(actualCost, is(value(4.55)));
    }

    private BigDecimal value(double value) {
        return valueOf(value).setScale(2, ROUND_HALF_UP);
    }
}
