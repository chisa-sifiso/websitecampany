package za.ac.tut.intercept;

import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import za.ac.tut.Item;

@Interceptor
public class ShoppingInterceptor
{
    @AroundInvoke
    public Object addToCartInterceptor(InvocationContext cnt) throws Exception
    {
        Logger.getLogger(ShoppingInterceptor.class.getName()).info("Intercepted method is " + cnt.getMethod().getName());

        Object[] parameters = cnt.getParameters();
        //LOOP THROUGH THE ARRAY OF PARAMETERS
        if (parameters != null)
        {
            for (Object parameter : parameters)
            {
                if (parameter instanceof Item)
                {
                    Item item = (Item) parameter;
                    //Add levy of 0.08 above R800, otherwise 0.03
                    if (item.getPrice() > 800)
                    {
                        item.setPrice(item.getPrice() + item.getPrice() * 0.08);
                    }
                    else
                    {
                        item.setPrice(item.getPrice() + item.getPrice() * 0.03);
                    }
                }
            }
        }
        return cnt.proceed();
    }
}
