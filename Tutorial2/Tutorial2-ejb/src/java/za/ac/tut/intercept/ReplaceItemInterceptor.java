package za.ac.tut.intercept;

import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import za.ac.tut.Item;

/**
 *
 * @author samuk
 */
@Interceptor
public class ReplaceItemInterceptor
{
    @AroundInvoke
    public Object replaceItemInterception(InvocationContext cnt) throws Exception
    {
        Logger.getLogger(ReplaceItemInterceptor.class.getName()).info("Intercepted method is " + cnt.getMethod().getName());
        Object[] parameters = cnt.getParameters();
        if (parameters != null)
        {
            for(Object parameter: parameters )
             {
                 if (parameter instanceof Item)
                 {
                     Item item = (Item) parameter;
                     item.setPrice(item.getPrice() - 1.14);
                 }
             }
        }
        Logger.getLogger(ReplaceItemInterceptor.class.getName()).info("R1.14 is subtracted, then we release process to the method " + cnt.getMethod().getName());
        return cnt.proceed();
    }
}
