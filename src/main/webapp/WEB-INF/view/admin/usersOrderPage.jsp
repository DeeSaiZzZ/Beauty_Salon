<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie.locale.value}"/>
<fmt:setBundle basename="localization"/>

<html>
<head>
    <title>MyOrderList</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <style>
        <%@include file="/WEB-INF/view/src/css/list.css"%>
    </style>
    <jsp:include page="../message.jsp"/>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div class="card">
    <div class="card-body">
        <form style="display: inline-block" action="${pageContext.request.contextPath}/main-servlet/main_page"
              method="get">
            <input id="date" type="date" name="date">
            <button name="action" value="check_all_order" class="btn btn-outline-secondary">
                <fmt:message key="common.submit"/>
            </button>
        </form>
        <button type="button" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
            <fmt:message key="common.edit"/>
        </button>
    </div>
</div>
<table class="table table-bordered table-hover">
    <thead>
    <tr>
        <th scope="col">№</th>
        <th scope="col"><fmt:message key="userOrderPage.master"/></th>
        <th scope="col"><fmt:message key="userOrderPage.service"/></th>
        <th scope="col"><fmt:message key="userOrderPage.price"/></th>
        <th scope="col"><fmt:message key="userOrderPage.date"/></th>
        <th scope="col"><fmt:message key="userOrderPage.time"/></th>
        <th scope="col"><fmt:message key="userOrderPage.client"/></th>
        <th scope="col"><fmt:message key="userOrderPage.status"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orders}" var="order">
        <tr>
            <td>${order.id}</td>
            <td>${order.orderMaster.firstName} ${order.orderMaster.lastName}</td>
            <td>${order.orderService.name}</td>
            <td>${order.orderService.price} UAH</td>
            <td>${order.orderDate}</td>
            <td>${order.orderTime}</td>
            <td>${order.orderUser.firstName} ${order.orderUser.lastName}</td>
            <td>${order.orderStatus}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="userOrderPage.moderateOrder"/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <form id="form1" action="${pageContext.request.contextPath}/main-servlet/main_page" method="post">
                        <label>
                            <fmt:message key="userOrderPage.infoEnterNum"/><input name="order_number" type="number">
                        </label>

                        <div>
                            <h5><fmt:message key="userOrderPage.selectAction"/></h5>
                            <label>
                                <input name="moderate_type" value="cancel_order" type="radio"><fmt:message
                                    key="userOrderPage.cancelOrder"/>
                            </label>
                            <br>
                            <label>
                                <input name="moderate_type" value="submit_pay" type="radio"><fmt:message
                                    key="userOrderPage.submitPay"/>
                            </label>
                        </div>

                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button form="form1" type="submit" name="action" value="moderate_order" class="btn btn-outline-success">
                    <fmt:message key="common.submit"/>
                </button>

                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <fmt:message key="common.close"/>
                </button>
            </div>
        </div>
    </div>
</div>

<script>
    document.getElementById("date").setAttribute("value", "${currentDate}")
</script>

</body>