<%-- 
    Document   : shoppingCarting
    Author     : samuk
--%>

<%@page import="java.util.List"%>
<%@page import="za.ac.tut.Item"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Cart</title>
    </head>
    <body>
        <h1>Products</h1>
        <%
            List<Item> items = (List<Item>) session.getAttribute("items");
            if (items != null)
            {
                for (Item item : items)
                {
        %>
        <form action="ShoppingServlet" method="post">
            <%= item.getName() %> R<%= item.getPrice() %>
            <input type="hidden" name="itemID" value="<%= item.getItemID() %>" />
            <input type="submit" value="Add to Cart" name="select" />
        </form>
        <%
                }
            }
        %>

        <h1>Cart</h1>
        <%
            List<Item> cartItems = (List<Item>) session.getAttribute("cartItems");
            if (cartItems != null)
            {
                for (Item item : cartItems)
                {
        %>
        <%= item.getName() %> R<%= item.getPrice() %></br>
        <%
                }
            }
        %>

        <form action="ShoppingServlet" method="post">
            <input type="submit" value="check out" name="select" />
        </form>
    </body>
</html>
