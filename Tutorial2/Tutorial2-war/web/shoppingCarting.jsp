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
        <h1>ABC Wholesalers - Products</h1>
        <%
            List<Item> items = (List<Item>) session.getAttribute("items");
            if (items == null) {
                response.sendRedirect("login.html");
                return;
            }
        %>
        <table border="1">
            <tr>
                <th>Product</th>
                <th>Type</th>
                <th>Qty</th>
                <th>Price</th>
                <th></th>
            </tr>
            <% for (Item item : items) { %>
            <tr>
                <td><%= item.getName() %></td>
                <td><%= item.getItemType() %></td>
                <td><%= item.getQty() %></td>
                <td>R<%= String.format("%.2f", item.getPrice()) %></td>
                <td>
                    <form action="ShoppingServlet" method="POST">
                        <input type="hidden" name="itemID" value="<%= item.getItemID() %>">
                        <input type="submit" value="Add to Cart">
                    </form>
                </td>
            </tr>
            <% } %>
        </table>

        <h2>Cart</h2>
        <%
            List<Item> cartItems = (List<Item>) session.getAttribute("cartItems");
            if (cartItems != null && !cartItems.isEmpty()) {
        %>
        <ul>
            <% for (Item item : cartItems) { %>
            <li><%= item.getName() %> - R<%= String.format("%.2f", item.getPrice()) %></li>
            <% } %>
        </ul>
        <% } else { %>
        <p>Your cart is empty.</p>
        <% } %>

        <form action="ShoppingServlet" method="POST">
            <input type="hidden" name="command" value="check out">
            <input type="submit" value="Check Out">
        </form>

        <%
            List<Item> boughtItems = (List<Item>) session.getAttribute("boughtItems");
            if (boughtItems != null) {
        %>
        <p><%= boughtItems.size() %> item(s) checked out and published to Jms/recentBoughtItems.</p>
        <% } %>
    </body>
</html>
