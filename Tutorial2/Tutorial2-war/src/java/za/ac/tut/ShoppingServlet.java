package za.ac.tut;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.ac.tut.session.CustomerService;
import za.ac.tut.session.Item;
import za.ac.tut.session.ShoppingCartService;

@WebServlet(name = "ShoppingServlet", urlPatterns = {"/ShoppingServlet"})
public class ShoppingServlet extends HttpServlet {

    @EJB
    private CustomerService customerBean;

    @Resource(mappedName = "Jms/recentBoughtItemsFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "Jms/recentBoughtItems")
    private Topic topic;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customer") == null) {
            response.sendRedirect("login.html");
            return;
        }

        ShoppingCartService cart = getShoppingCart(session);
        String command = request.getParameter("command");

        if ("add".equals(command)) {
            int itemID = Integer.parseInt(request.getParameter("itemID"));
            Item item = customerBean.findItem(itemID);
            if (item != null) {
                cart.addToCart(item);
            }
        } else if ("replace".equals(command)) {
            int oldItemID = Integer.parseInt(request.getParameter("oldItemID"));
            int newItemID = Integer.parseInt(request.getParameter("newItemID"));
            Item newItem = customerBean.findItem(newItemID);
            if (newItem != null) {
                cart.replaceItem(oldItemID, newItem);
            }
        } else if ("check out".equals(command)) {
            List<Item> boughtItems = cart.checkout();
            for (Item item : boughtItems) {
                publishRecentItem(item);
            }
            session.setAttribute("boughtItems", boughtItems);
        }

        session.setAttribute("cartItems", cart.getItems());
        response.sendRedirect("shoppingCart.jsp");
    }

    public void publishRecentItem(Item item) {
        try (JMSContext context = connectionFactory.createContext()) {
            context.createProducer().send(topic, item);
        }
    }

    // each logged on customer keeps their own stateful cart in the http session
    private ShoppingCartService getShoppingCart(HttpSession session) throws ServletException {
        ShoppingCartService cart = (ShoppingCartService) session.getAttribute("cart");
        if (cart == null) {
            try {
                cart = (ShoppingCartService) new InitialContext().lookup(
                        "java:app/Tutorial2-ejb/ShoppingCartBean!za.ac.tut.session.ShoppingCartService");
            } catch (NamingException e) {
                throw new ServletException("Unable to create shopping cart", e);
            }
            session.setAttribute("cart", cart);
        }
        return cart;
    }

}
