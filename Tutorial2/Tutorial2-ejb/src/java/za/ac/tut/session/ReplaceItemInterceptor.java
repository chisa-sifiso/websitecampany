package za.ac.tut.session;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class ReplaceItemInterceptor {

    @AroundInvoke
    public Object replaceItemInterception(InvocationContext context) throws Exception {
        Object[] parameters = context.getParameters();

        for (Object parameter : parameters) {
            if (parameter instanceof Item) {
                Item item = (Item) parameter;
                item.setPrice(item.getPrice() - 1.14);
            }
        }

        context.setParameters(parameters);
        return context.proceed();
    }

}
