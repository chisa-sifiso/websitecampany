/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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

/**
 *
 * @author samuk
 */
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
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String decide = request.getParameter("select");

        if (decide.equals("check out"))
        {
            //PUBLISH EACH ITEM FROM THE SHOPPING CART
            List<Item> items = service.checkout();
            for (Item item : items)
            {
                publishRecentItem(item);
            }
            session.setAttribute("boughtItems", items);
        }
        else
        {
            //ADD AN ITEM TO THE SHOPPING CART
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
        //Session
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
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
