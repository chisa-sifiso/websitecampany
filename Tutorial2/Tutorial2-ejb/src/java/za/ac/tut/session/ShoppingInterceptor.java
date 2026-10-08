package za.ac.tut.session;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class ShoppingInterceptor {

    private static final double LIMIT = 800.0;
    private static final double HIGH_LEVY = 0.08;
    private static final double LOW_LEVY = 0.03;

    @AroundInvoke
    public Object addToCartInterceptor(InvocationContext context) throws Exception {
        for (Object parameter : context.getParameters()) {
            if (parameter instanceof Item) {
                Item item = (Item) parameter;
                double price = item.getPrice();
                double levy = price > LIMIT ? HIGH_LEVY : LOW_LEVY;
                item.setPrice(price + (price * levy));
                System.out.println("ShoppingInterceptor: levy of " + levy + " added to "
                        + item.getName() + ", new price R" + item.getPrice());
            }
        }
        return context.proceed();
    }
}
