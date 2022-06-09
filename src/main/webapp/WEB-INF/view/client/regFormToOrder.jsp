<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie.locale.value}"/>
<fmt:setBundle basename="localization"/>

<html>
<head>
    <title>OrderList</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <style>
        <%@include file="/WEB-INF/view/src/css/list.css"%>
    </style>
    <jsp:include page="../message.jsp"/>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div class="container col-md-4">
    <div class="card">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/main-servlet/main_page" method="post">

                <div class="form-group">
                    <label><fmt:message key="regFormToOrder.selectMaster"/> </label>
                    <select class="custom-select" name="master_id"
                            onchange="location.href='${pageContext.request.contextPath}/main-servlet/main_page?action=find_service&master_id='
                                    + this.options[this.selectedIndex].getAttribute('value')">
                        <jsp:useBean id="masterList" scope="request" type="java.util.List"/>

                        <option selected="selected" disabled><fmt:message key="regFormToOrder.master"/></option>

                        <c:forEach items="${masterList}" var="master">
                            <option id="${master.id}_master"
                                    value="${master.id}">${master.firstName} ${master.lastName}
                            </option>
                        </c:forEach>

                    </select>
                </div>

                <c:if test="${selectedMaster!=null}">
                    <div class="form-group">
                        <label><fmt:message key="regFormToOrder.selectService"/> </label>
                        <select class="custom-select" name="service_id"
                                onchange="document.getElementById('date').removeAttribute('value')">

                            <option selected="selected" disabled><fmt:message key="regFormToOrder.service"/></option>

                            <c:forEach items="${serviceList}" var="service">
                                <option id="${service.id}_service" value="${service.id}">${service.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label><fmt:message key="regFormToOrder.selectDate"/> </label>

                        <input id="date"
                               onchange="location.href='${pageContext.request.contextPath}/main-servlet/main_page?action=find_timeSlots&date='
                                       + this.value + '&selected_service=' + service_id.options[service_id.selectedIndex].getAttribute('value')
                                       + '&master_id='+${selectedMaster}"
                               type="date" name="orderDate"/>

                    </div>
                </c:if>

                <c:if test="${orderList!=null}">
                    <div class="form-group">
                        <select class="custom-select" name="orderTime">
                            <option selected="selected" disabled><fmt:message key="regFormToOrder.time"/></option>
                            <c:forEach items="${orderList}" var="order">
                                <option value="${order.orderTime}">${order.orderTime}</option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>

                <button type="submit" name="action" value="create_request" class="btn btn-outline-success">
                    <fmt:message key="common.submit"/>
                </button>

            </form>

        </div>

    </div>

</div>

<script>
    document.getElementById("${selectedMaster}_master").setAttribute("selected", "selected");
    document.getElementById("${selectedService}_service").setAttribute("selected", "selected");
    document.getElementById("date").setAttribute("value", "${currentDate}")
</script>

</body>

</html>
