package za.ac.tut;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.ac.tut.session.CustomerService;
import za.ac.tut.session.ShoppingCartService;

@WebServlet(name = "ShoppingServlet", urlPatterns = {"/ShoppingServlet"})
public class ShoppingServlet extends HttpServlet {
@EJB
ShoppingCartService service;
@EJB
CustomerService customerService;
@Resource(lookup="Jms/recentBoughtItemsFactory")
ConnectionFactory factory;
@Resource(lookup="Jms/recentBoughtItems")
Topic topic;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String decide = request.getParameter("select");

        if (decide.equals("check out"))
        {
            List<Item> items = service.checkout();
            for (Item item : items)
            {
                publishRecentItem(item);
            }
            session.setAttribute("boughtItems", items);
        }
        else
        {
            int itemID = Integer.parseInt(request.getParameter("itemID"));
            Item objItem = customerService.findItem(itemID);
            service.addToCart(objItem);
            session.setAttribute("cartItems", service.checkout());
        }
        response.sendRedirect("shoppingCarting.jsp");
    }

    public void publishRecentItem(Item item)
    {
    try {
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer publish = session.createProducer(topic);
        ObjectMessage objMsg = session.createObjectMessage(item);
        publish.send(objMsg);
        session.close();
        connection.close();
       }
     catch (JMSException ex)
     {
        Logger.getLogger(ShoppingServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
