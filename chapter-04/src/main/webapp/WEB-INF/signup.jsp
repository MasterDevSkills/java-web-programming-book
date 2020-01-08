<%@include file="includes/header.jsp" %>
<%@include file="includes/navigation.jsp" %>

<div class="container">
    <h2 class="h2">Sign Up</h2>
    <hr class="mb-4">

    <form class="form-horizontal" role="form" action="<c:url value="/signup"/>" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" class="form-control" id="username" name="username" placeholder=""/>
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" name="email"
                   placeholder="you@example.com"/>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" name="password">
        </div>

        <div class="form-group">
            <label for="passwordConfirmed">Password Confirmed</label>
            <input type="password" class="form-control" id="passwordConfirmed" name="passwordConfirmed">
        </div>

        <div class="form-group">
            <label for="email">First Name</label>
            <input type="text" class="form-control" id="firstName" name="firstName"/>
        </div>

        <div class="form-group">
            <label for="email">Last Name</label>
            <input type="text" class="form-control" id="lastName" name="lastName"/>
        </div>

        <hr class="mb-4">
        <div class="form-group">
            <button class="btn btn-primary btn-lg" type="submit">Signup
            </button>
        </div>
    </form>

</div>

<%@include file="includes/footer.jsp" %>