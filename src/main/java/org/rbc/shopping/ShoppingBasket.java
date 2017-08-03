package org.rbc.shopping;

import org.rbc.shopping.exceptions.InvalidItemInBasketException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;

public class ShoppingBasket {
    public BigDecimal evaluateBasket(List<String> fruitBasket) {
        isValidFruit(fruitBasket);
        return fruitBasket.stream()
                .map(fruit -> basketWithPrice().get(fruit))
                .reduce(value(0.00), BigDecimal::add);
    }

    private BigDecimal value(double value) {
        return valueOf(value).setScale(2, ROUND_HALF_UP);
    }

    private Map<String, BigDecimal> basketWithPrice() {
        Map<String, BigDecimal> basketPrice = new HashMap<>();
        basketPrice.put("Banana", value(0.25));
        basketPrice.put("Orange", value(0.25));
        basketPrice.put("Apple", value(0.50));
        basketPrice.put("Lemon", value(0.20));
        basketPrice.put("Peache", value(0.20));
        return basketPrice;
    }

    private void isValidFruit(List<String> fruitBasket) {
        List<String> validFruits = asList("Banana", "Orange", "Apple", "Lemon", "Peache");
        if (!validFruits.containsAll(fruitBasket)) {
            throw new InvalidItemInBasketException("Invalid item in the basket");
        }
    }
}
