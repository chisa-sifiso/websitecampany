package za.ac.tut.session;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import za.ac.tut.Item;
import za.ac.tut.intercept.ReplaceItemInterceptor;
import za.ac.tut.intercept.ShoppingInterceptor;

@Stateful
public class ShoppingCartBean implements ShoppingCartService
{
    private List<Item> items;

    @PostConstruct
    public void initialise()
    {
        items = new ArrayList<>();
    }

    @Override
    @Interceptors(ShoppingInterceptor.class)
    public void addToCart(Item item)
    {
        items.add(item);
    }

    @Override
    public List<Item> checkout()
    {
        return items;
    }

    @Override
    @Interceptors(ReplaceItemInterceptor.class)
    public void replaceItem(int id, Item item)
    {
        for (int i = 0; i < items.size(); i++)
        {
            if (items.get(i).getItemID() == id)
            {
                items.set(i, item);
                break;
            }
        }
    }

}
