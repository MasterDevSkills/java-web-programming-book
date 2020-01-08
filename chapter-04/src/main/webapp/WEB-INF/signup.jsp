<%@include file="includes/header.jsp" %>
<%@include file="includes/navigation.jsp" %>

<div class="container">
    <h2 class="h2">Sign Up</h2>
    <hr class="mb-4">

    <form class="form-horizontal" role="form" action="<c:url value="/signup"/>" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" class="form-control" id="username"
                   name="username"
                   required
                   minlength="4"
                   maxlength="32"/>
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email"
                   name="email"
                   required
                   minlength="4"
                   maxlength="64"
                   placeholder="you@example.com"/>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password"
                   name="password"
                   required
                   minlength="6"
                   maxlength="16"
            >
        </div>

        <div class="form-group">
            <label for="passwordConfirmed">Password Confirmed</label>
            <input type="password" class="form-control" id="passwordConfirmed"
                   name="passwordConfirmed"
                   required
                   minlength="6"
                   maxlength="16">
        </div>

        <div class="form-group">
            <label for="email">First Name</label>
            <input type="text" class="form-control" id="firstName"
                   name="firstName"
                   required
                   minlength="1"
                   maxlength="30"/>
        </div>

        <div class="form-group">
            <label for="email">Last Name</label>
            <input type="text" class="form-control" id="lastName"
                   name="lastName"
                   required
                   minlength="1"
                   maxlength="30"/>
        </div>

        <hr class="mb-4">
        <div class="form-group">
            <button class="btn btn-primary btn-lg" type="submit"
                    onclick="return validatePassword()">Signup
            </button>
        </div>
    </form>
</div>

<script type="text/javascript">
    function validatePassword() {
        var password = document.getElementsByName("password").value;
        var confirmPassword = document.getElementById("passwordConfirmed").value;
        if (password !== confirmPassword) {
            alert("Passwords do not match.");
            return false;
        }
        return true;
    }
</script>

<%@include file="includes/footer.jsp" %>