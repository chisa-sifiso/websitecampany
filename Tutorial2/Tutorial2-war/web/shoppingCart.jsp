<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${empty sessionScope.customer}">
    <c:redirect url="login.html"/>
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ABC Wholesalers - Shopping Cart</title>
    </head>
    <body>
        <h1>Welcome ${sessionScope.customer.email}</h1>

        <h2>Products</h2>
        <table border="1">
            <tr><th>ID</th><th>Name</th><th>Type</th><th>Qty</th><th>Price</th><th></th></tr>
            <c:forEach var="item" items="${sessionScope.items}">
                <tr>
                    <td>${item.itemID}</td>
                    <td>${item.name}</td>
                    <td>${item.itemType}</td>
                    <td>${item.qty}</td>
                    <td>R<fmt:formatNumber value="${item.price}" minFractionDigits="2" maxFractionDigits="2"/></td>
                    <td>
                        <form action="ShoppingServlet" method="POST">
                            <input type="hidden" name="command" value="add">
                            <input type="hidden" name="itemID" value="${item.itemID}">
                            <input type="submit" value="Add to Cart">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <h2>My Cart</h2>
        <c:choose>
            <c:when test="${empty sessionScope.cartItems}">
                <p>Your cart is empty.</p>
            </c:when>
            <c:otherwise>
                <table border="1">
                    <tr><th>ID</th><th>Name</th><th>Price (incl. levy)</th></tr>
                    <c:forEach var="item" items="${sessionScope.cartItems}">
                        <tr>
                            <td>${item.itemID}</td>
                            <td>${item.name}</td>
                            <td>R<fmt:formatNumber value="${item.price}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        </tr>
                    </c:forEach>
                </table>

                <h3>Replace an item</h3>
                <form action="ShoppingServlet" method="POST">
                    <input type="hidden" name="command" value="replace">
                    Replace item ID: <input type="number" name="oldItemID" required>
                    with item ID: <input type="number" name="newItemID" required>
                    <input type="submit" value="Replace">
                </form>

                <form action="ShoppingServlet" method="POST">
                    <input type="hidden" name="command" value="check out">
                    <input type="submit" value="Out">
                </form>
            </c:otherwise>
        </c:choose>

        <c:if test="${not empty sessionScope.boughtItems}">
            <h2>Recently bought</h2>
            <ul>
                <c:forEach var="item" items="${sessionScope.boughtItems}">
                    <li>${item.name} - R<fmt:formatNumber value="${item.price}" minFractionDigits="2" maxFractionDigits="2"/></li>
                </c:forEach>
            </ul>
        </c:if>
    </body>
</html>
