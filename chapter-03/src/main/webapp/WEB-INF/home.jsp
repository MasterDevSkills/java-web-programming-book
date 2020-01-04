<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Products</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/webjars/bootstrap/4.4.1/css/bootstrap.min.css"/>
</head>
<body>
<div class="container">
    <table class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
        </tr>
        </thead>

        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <c:out value="${product.name}"/>
                </td>
                <td>
                    <c:out value="${product.description}"/>
                </td>
                <td>
                    <c:out value="${product.price}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
