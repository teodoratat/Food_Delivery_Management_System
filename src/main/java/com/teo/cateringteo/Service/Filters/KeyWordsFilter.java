package com.teo.cateringteo.Service.Filters;

import com.teo.cateringteo.BussinessLogic.MenuItem;

import java.util.Locale;
import java.util.function.Predicate;

/**
 * When sent to DeliveryService::filterMenu(Predicate[])
 * as one of the elements of the array of Predicates, this filter
 * will select only the MenuItems which have at least one of the keywords in their title.
 */
public class KeyWordsFilter implements Predicate<MenuItem> {
    private String[] keyWords;

    public KeyWordsFilter(String[] keyWords){
        this.keyWords = keyWords;
    }

    @Override
    public boolean test(MenuItem menuItem) {
        String lowerTitle = menuItem.getTitle().toLowerCase(Locale.ROOT);
        for(String word: keyWords){
            String lowerWord = word.toLowerCase(Locale.ROOT);
            if(lowerTitle.contains(lowerWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("KeyWordsFilter(");
        for(String s:keyWords){
            stringBuilder.append(s);
            stringBuilder.append(", ");
        }
        if(keyWords.length > 0){
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append(") ");
        return stringBuilder.toString();
    }
}
