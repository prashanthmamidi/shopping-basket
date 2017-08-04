package org.rbc.shopping;

import org.rbc.shopping.exceptions.InvalidItemInShoppingBasketException;

import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;

public class ShoppingBasket {
    public BigDecimal evaluateBasket(Map<String, Integer> fruitBasket) {
        isValidFruit(fruitBasket.keySet());
       return fruitBasket.keySet().stream()
                .map(fruit -> getPriceOf(fruit).multiply(withQty(fruitBasket, fruit)))
                .reduce(value(0.00), BigDecimal::add);

    }

    private BigDecimal getPriceOf(String fruit) {
        Map<String, BigDecimal> basketPrice = new HashMap<>();
        basketPrice.put("Banana", value(0.25));
        basketPrice.put("Orange", value(0.25));
        basketPrice.put("Apple", value(0.50));
        basketPrice.put("Lemon", value(0.20));
        basketPrice.put("Peache", value(0.20));
        return basketPrice.get(fruit);
    }

    private void isValidFruit(Set<String> fruits) {
        Set<String> validFruits = new HashSet<>(asList("Banana", "Orange", "Apple", "Lemon", "Peache"));
        if (!validFruits.containsAll(fruits)) {
            throw new InvalidItemInShoppingBasketException("Invalid item in the basket");
        }
    }

    private BigDecimal withQty(Map<String, Integer> fruitBasket, String fruit) {
        return valueOf(fruitBasket.get(fruit));
    }

    private BigDecimal value(double value) {
        return valueOf(value).setScale(2, ROUND_HALF_UP);
    }

}
