package za.ac.tut.session;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class ReplaceItemInterceptor {

    private static final double DISCOUNT = 1.14;

    @AroundInvoke
    public Object replaceItemInterception(InvocationContext context) throws Exception {
        for (Object parameter : context.getParameters()) {
            if (parameter instanceof Item) {
                Item item = (Item) parameter;
                item.setPrice(item.getPrice() - DISCOUNT);
                System.out.println("ReplaceItemInterceptor: R" + DISCOUNT + " subtracted from "
                        + item.getName() + ", new price R" + item.getPrice());
            }
        }
        return context.proceed();
    }
}
