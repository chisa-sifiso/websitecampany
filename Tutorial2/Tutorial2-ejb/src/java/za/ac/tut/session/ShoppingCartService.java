
package za.ac.tut.session;

import java.util.List;
import javax.ejb.Local;

@Local
public interface ShoppingCartService {

    public void addToCart(Item item);
    public List<Item> checkout();
    public void replaceItem(int itemID, Item item);
    public List<Item> getItems();
}
