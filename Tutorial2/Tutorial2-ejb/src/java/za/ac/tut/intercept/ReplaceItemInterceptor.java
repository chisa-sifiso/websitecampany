package za.ac.tut.intercept;

import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import za.ac.tut.Item;

@Interceptor
public class ReplaceItemInterceptor
{
    @AroundInvoke
    public Object replaceItemInterception(InvocationContext cnt) throws Exception
    {
        Logger.getLogger(ReplaceItemInterceptor.class.getName()).info("Intercepted method is " + cnt.getMethod().getName());

        Object[] parameters = cnt.getParameters();
        //LOOP THROUGH THE ARRAY OF PARAMETERS
        if (parameters != null)
        {
            for (Object parameter : parameters)
            {
                if (parameter instanceof Item)
                {
                    Item item = (Item) parameter;
                    //Subtract R1.14
                    item.setPrice(item.getPrice() - 1.14);
                }
            }
        }
        return cnt.proceed();
    }
}
