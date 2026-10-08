package za.ac.tut.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;

@Stateful
public class ShoppingCartBean implements ShoppingCartService {

    private List<Item> items = new ArrayList<>();

    @Override
    @Interceptors(ShoppingInterceptor.class)
    public void addToCart(Item item) {
        items.add(item);
    }

    @Override
    public List<Item> checkout() {
        // return the bought items and empty the cart
        List<Item> boughtItems = new ArrayList<>(items);
        items.clear();
        return boughtItems;
    }

    @Override
    @Interceptors(ReplaceItemInterceptor.class)
    public void replaceItem(int itemID, Item item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemID() == itemID) {
                items.set(i, item);
                return;
            }
        }
    }

    @Override
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

}
