<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>eShoppers </title>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/webjars/bootstrap/4.4.1/css/bootstrap.min.css"/>"/>
</head>
<body style="padding-top: 70px">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/"/>">e-Shoppers</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive"
                aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="<c:url value="/"/>">Home
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="jumbotron">
        <h1>Welcome to e-shoppers! </h1>
        <img src="<c:url value="/image/cart.jpg"/>" style="height: 200px"
             alt=""/>
    </div>

    <div class="row">
        <c:forEach var="product" items="${products}">
            <div class="col-sm-4">
                <div class="card h-100 mb-4">
                    <div class="card-body">
                        <h5 class="card-title"><c:out value="${product.name}"/></h5>
                        <p class="card-text"><c:out value="${product.description}"/></p>
                        <p class="card-text">Price: $ <c:out value="${product.price}"/></p>

                        <a href="#" class="card-link btn btn-outline-info">Add toCart </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<footer class="footer mt-auto py-3">
    <div class="container">
        <span class="text-muted">Copyright &copy; eShoppers.com 2020</span>
    </div>
</footer>

</body>
</html>
