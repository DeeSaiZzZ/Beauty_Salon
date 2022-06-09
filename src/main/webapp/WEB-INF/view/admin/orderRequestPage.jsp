<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${cookie.locale.value}"/>
<fmt:setBundle basename="localization"/>

<html>
<head>
    <title>Requests</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <jsp:include page="../message.jsp"/>
</head>
<body>
<jsp:include page="../header.jsp"/>

<div class="container col-md-4">
    <div class="card">
        <div class="card-header">
            <h1><fmt:message key="orderRequestPage.request"/></h1>
        </div>
        <div class="card-body">
            <c:choose>

            <c:when test="${orderRequest==null}">
                <h1><fmt:message key="orderRequestPage.noRequest"/></h1>
            </c:when>

            <c:otherwise>
            <form id="form1" action="${pageContext.request.contextPath}/main-servlet/main_page" method="post">
                <h5><fmt:message key="orderRequestPage.client"/></h5>
                <input disabled value="${orderRequest.orderUser.firstName} ${orderRequest.orderUser.lastName}">
                <h5><fmt:message key="orderRequestPage.master"/></h5>
                <input disabled
                       value="${orderRequest.orderMaster.firstName} ${orderRequest.orderMaster.lastName}">
                <h5><fmt:message key="orderRequestPage.service"/></h5>
                <input disabled value="${orderRequest.orderService.name}">
                <h5><fmt:message key="orderRequestPage.DateAndTime"/></h5>
                <input id="timeSlot" readonly type="time" name="order_time" value="${orderRequest.orderTime}">
                <input readonly type="date" name="order_date" value="${orderRequest.orderDate}">

                <input hidden name="user_id" value="${orderRequest.orderUser.id}">
                <input hidden name="master_id" value="${orderRequest.orderMaster.id}">
                <input hidden name="service_id" value="${orderRequest.orderService.id}">

            </form>

            <form id="form2" action="${pageContext.request.contextPath}/main-servlet/main_page" method="post"></form>


        </div>
        <div class="card-footer">
            <button form="form1" type="submit" name="action" value="accept_request"
                    class="btn btn-outline-success">
                <fmt:message key="orderRequestPage.accept"/>
            </button>

            <button form="form2" type="submit" name="action" value="cancel_request"
                    class="btn btn-outline-danger">
                <fmt:message key="orderRequestPage.reject"/>
            </button>

            <button type="button" class="btn btn-outline-primary" data-bs-toggle="modal"
                    data-bs-target="#exampleModal">
                <fmt:message key="common.edit"/>
            </button>
        </div>
        </c:otherwise>

        </c:choose>
    </div>
</div>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="orderRequestPage.editTimeSlot"/></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <c:if test="${orderList!=null}">
                    <div class="form-group">
                        <select onchange="document.getElementById('timeSlot').setAttribute('value',this.options[this.selectedIndex].getAttribute('value'))"
                                class="custom-select">
                            <option selected="selected" disabled>
                                <fmt:message key="orderRequestPage.Time"/>
                            </option>
                            <c:forEach items="${orderList}" var="order">
                                <option value="${order.orderTime}">${order.orderTime}</option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">
                    <fmt:message key="common.close"/>
                </button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
