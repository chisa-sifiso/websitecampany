/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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

/**
 *
 * @author samuk
 */
@WebServlet(name = "ShoppingServlet", urlPatterns = {"/ShoppingServlet"})
public class ShoppingServlet extends HttpServlet {

    @EJB
    private CustomerService customerService;

    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "Jms/recentBoughtItems")
    private Topic topic;

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        ShoppingCartService cart = getShoppingCart(session);

        String command = request.getParameter("command");

        if ("check out".equalsIgnoreCase(command)) {
            List<Item> boughtItems = cart.checkout();

            for (Item item : boughtItems) {
                publishRecentItem(item);
            }

            session.setAttribute("boughtItems", boughtItems);
        } else {
            int itemID = Integer.parseInt(request.getParameter("itemID"));
            Item item = customerService.findItem(itemID);

            if (item != null) {
                cart.addToCart(item);
            }
            session.setAttribute("cartItems", cart.checkout());
        }

        response.sendRedirect("shoppingCarting.jsp");
    }

    public void publishRecentItem(Item item) {
        try (JMSContext context = connectionFactory.createContext()) {
            context.createProducer().send(topic, item);
        }
    }

    // A stateful bean must not be shared between users, so each HTTP session gets its own cart
    private ShoppingCartService getShoppingCart(HttpSession session) throws ServletException {
        ShoppingCartService cart = (ShoppingCartService) session.getAttribute("cart");

        if (cart == null) {
            try {
                InitialContext ctx = new InitialContext();
                cart = (ShoppingCartService) ctx.lookup("java:app/Tutorial2-ejb/ShoppingCartBean!za.ac.tut.session.ShoppingCartService");
                session.setAttribute("cart", cart);
            } catch (NamingException e) {
                throw new ServletException(e);
            }
        }
        return cart;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Adds items to the shopping cart and checks out";
    }

}
