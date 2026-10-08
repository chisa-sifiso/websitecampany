
package za.ac.tut.session;

import java.util.List;
import javax.ejb.Local;
import za.ac.tut.Item;

@Local
public interface ShoppingCartService {

    public void addToCart(Item item);
    public List<Item> checkout();
    public void replaceItem(int id, Item item);

}
