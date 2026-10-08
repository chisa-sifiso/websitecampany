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
        if (parameters != null)
        {
            for(Object parameter: parameters )
             {
                 if (parameter instanceof Item)
                 {
                     Item item = (Item) parameter;
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
        Logger.getLogger(ShoppingInterceptor.class.getName()).info("A levy is added, then we release process to the method " + cnt.getMethod().getName());
        return cnt.proceed();
    }
}
