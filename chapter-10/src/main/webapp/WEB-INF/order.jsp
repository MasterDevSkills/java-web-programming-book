<%@include file="includes/header.jsp" %>
<%@include file="includes/navigation.jsp" %>

<div class="container">
    <br/>

    <div>
        <form class="form-horizontal" role="form" action="<c:url value="/order"/>" method="post">

            <div class="row">
                <div class="col-md-4 order-md-2 mb-4">
                    <h4 class="d-flex justify-content-between align-items-center mb-3">
                        <span class="text-muted">Your cart</span>
                        <span class="badge badge-secondary badge-pill">
                            <c:out value="${cart.totalItem}"/></span>
                    </h4>
                    <ul class="list-group mb-3">

                        <c:forEach var="cartItem" items="${cart.cartItems}">
                            <li class="list-group-item d-flex justify-content-between lh-condensed">
                                <div>
                                    <h6 class="my-0">
                                        <c:out value="${cartItem.product.name}"/>
                                        <small class="badge badge-info">
                                            <c:out value="${cartItem.quantity}"/>
                                        </small>
                                    </h6>
                                    <small class="text-muted">
                                        <c:out value="${cartItem.product.description}"/>
                                    </small>
                                </div>
                                <span class="text-muted">$<c:out value="${cartItem.price}"/> </span>
                            </li>
                        </c:forEach>

                        <li class="list-group-item d-flex justify-content-between">
                            <span>Total (USD)</span>
                            <strong>$<c:out value="${cart.totalPrice}"/></strong>
                        </li>
                    </ul>
                </div>

                <div class="col-md-8 order-md-1">
                    <h4 class="mb-3">Shipping address</h4>

                    <div class="mb-3">
                        <label for="address">Address</label>
                        <input type="text" class="form-control" id="address"
                               name="address" placeholder="1234 Main St"
                               value="${shippingAddress.address}"
                               required>
                        <c:if test="${errors.address != null}">
                            <small class="text-danger"> ${errors.address}</small>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label for="address2">Address 2 <span class="text-muted">(Optional)</span></label>
                        <input type="text" class="form-control" id="address2" name="address2"
                               value="${shippingAddress.address2}"
                               placeholder="Apartment or suite">
                    </div>

                    <div class="row">
                        <div class="col-md-5 mb-3">
                            <label for="country">Country</label>
                            <select class="custom-select d-block w-100"
                                    id="country"
                                    required name="country">
                                <option value="">Choose...</option>
                                <c:forEach var="country" items="${countries}">
                                    <option value="${country}"
                                        ${country == shippingAddress.country ? 'selected="selected"' : ''}>
                                            ${country}
                                    </option>
                                </c:forEach>
                            </select>
                            <c:if test="${errors.country != null}">
                                <small class="text-danger"> ${errors.country}</small>
                            </c:if>
                        </div>
                        <div class="col-md-4 mb-3">
                            <label for="state">State</label>
                            <input type="text" class="form-control" id="state" name="state"
                                   value="${shippingAddress.state}"
                                   placeholder="State">
                            <c:if test="${errors.state != null}">
                                <small class="text-danger"> ${errors.state}</small>
                            </c:if>
                        </div>

                        <div class="col-md-3 mb-3">
                            <label for="zip">Zip</label>
                            <input type="text" class="form-control" id="zip" name="zip" placeholder=""
                                   value="${shippingAddress.zip}" required>
                            <c:if test="${errors.zip != null}">
                                <small class="text-danger"> ${errors.zip}</small>
                            </c:if>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="address2">Phone Number
                            <span class="text-muted">(Optional)</span>
                        </label>
                        <input type="text" class="form-control"
                               id="mobileNumber" name="mobileNumber"
                               value="${shippingAddress.mobileNumber}"
                               placeholder="+88016xxxxxxxx">
                        <c:if test="${errors.mobileNumber != null}">
                            <small class="text-danger"> ${errors.mobileNumber}</small>
                        </c:if>
                    </div>

                    <hr class="mb-4">

                    <hr class="mb-4">
                    <button class="btn btn-primary btn-lg btn-block"
                            type="submit">
                        Continue to checkout
                    </button>
                </div>
            </div>
        </form>
    </div>
    <br/>
    <br/>
</div>

<%@include file="includes/footer.jsp" %>
