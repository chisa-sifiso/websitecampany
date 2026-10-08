package za.ac.tut.session;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class ShoppingInterceptor {

    @AroundInvoke
    public Object addToCartInterceptor(InvocationContext context) throws Exception {
        Object[] parameters = context.getParameters();

        for (Object parameter : parameters) {
            if (parameter instanceof Item) {
                Item item = (Item) parameter;
                double price = item.getPrice();

                if (price > 800) {
                    item.setPrice(price + (price * 0.08));
                } else {
                    item.setPrice(price + (price * 0.03));
                }
            }
        }

        context.setParameters(parameters);
        return context.proceed();
    }

}
