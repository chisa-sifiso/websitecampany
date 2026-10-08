package za.ac.tut;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.ac.tut.session.Customer;
import za.ac.tut.session.CustomerService;
import za.ac.tut.session.Item;

@WebServlet(name = "CustomerServlet", urlPatterns = {"/CustomerServlet"})
public class CustomerServlet extends HttpServlet {

    @EJB
    private CustomerService customerBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Customer customer = customerBean.validateLogon(email, password);

        if (customer != null) {
            List<Item> items = customerBean.getAllItems(null);

            HttpSession session = request.getSession(true);
            session.setAttribute("customer", customer);
            session.setAttribute("items", items);
            response.sendRedirect("shoppingCart.jsp");
        } else {
            response.sendRedirect("login.html");
        }
    }

}
