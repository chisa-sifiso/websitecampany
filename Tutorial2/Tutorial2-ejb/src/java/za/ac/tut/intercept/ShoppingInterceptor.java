/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class ShoppingInterceptor
{
    @AroundInvoke
    public Object addToCartInterceptor(InvocationContext cnt) throws Exception
    {
        //Know the name of the method we are intercepting
        Logger.getLogger(ShoppingInterceptor.class.getName()).info("Intercepted method is " + cnt.getMethod().getName());
        Object[] parameters = cnt.getParameters();
        //LOOP THROUGH THE ARRAY OF PARAMETERS
        if (parameters != null)
        {
            for(Object parameter: parameters )
             {
                 if (parameter instanceof Item)
                 {
                     Item item = (Item) parameter;
                     if (item.getPrice() > 800)
                     {
                         //Add levy of 0.08
                         item.setPrice(item.getPrice() + item.getPrice() * 0.08);
                     }
                     else
                     {
                         //Add levy of 0.03
                         item.setPrice(item.getPrice() + item.getPrice() * 0.03);
                     }
                 }
             }
        }
        Logger.getLogger(ShoppingInterceptor.class.getName()).info("A levy is added, then we release process to the method " + cnt.getMethod().getName());
        return cnt.proceed();
    }
}
