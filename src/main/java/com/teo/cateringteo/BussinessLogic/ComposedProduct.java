package com.teo.cateringteo.BussinessLogic;

import java.util.ArrayList;
import java.util.List;
/**
 * Extends the MenuItem class, represents an item composed of 2 or more base products
 */
public class ComposedProduct extends MenuItem{
    private List<BaseProduct> baseProducts;

    public ComposedProduct(String title) {
        super(title);
        baseProducts = new ArrayList<>();
    }

    public void add(BaseProduct baseProduct){
        baseProducts.add(baseProduct);
    }

    public void remove(BaseProduct baseProduct){
        baseProducts.remove(baseProduct);
    }

    public List<BaseProduct> getBaseProducts() {
        return baseProducts;
    }

    @Override
    public float getRating() {
        double sum = baseProducts.stream().mapToDouble(BaseProduct::getRating).sum();
        return (float) sum / baseProducts.size();
    }

    @Override
    public int getCalories() {
        return baseProducts.stream().mapToInt(BaseProduct::getCalories).sum();
    }

    @Override
    public int getProtein() {
        return baseProducts.stream().mapToInt(BaseProduct::getProtein).sum();
    }

    @Override
    public int getFat() {
        return baseProducts.stream().mapToInt(BaseProduct::getFat).sum();
    }

    @Override
    public int getSodium() {
        return baseProducts.stream().mapToInt(BaseProduct::getSodium).sum();
    }

    @Override
    public int getPrice() {
        return baseProducts.stream().mapToInt(BaseProduct::getPrice).sum();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Composed["+ getTitle()+ ": [");
        for(BaseProduct baseProduct: baseProducts){
            stringBuilder.append(baseProduct.getTitle());
            stringBuilder.append(", ");
        }
        return stringBuilder.append("]]").toString();
    }
}
