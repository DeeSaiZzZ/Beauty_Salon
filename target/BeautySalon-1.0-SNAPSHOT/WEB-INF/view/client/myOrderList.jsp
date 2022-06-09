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
    <style>
        <%@include file="/WEB-INF/view/src/css/list.css"%>
    </style>
</head>
<body>
<jsp:include page="../header.jsp"/>
<table class="table table-bordered table-hover">
    <thead>
    <tr>
        <th scope="col"><fmt:message key="myOrderList.master"/></th>
        <th scope="col"><fmt:message key="myOrderList.service"/></th>
        <th scope="col"><fmt:message key="myOrderList.price"/></th>
        <th scope="col"><fmt:message key="myOrderList.date"/></th>
        <th scope="col"><fmt:message key="myOrderList.time"/></th>
        <th scope="col"><fmt:message key="myOrderList.status"/></th>
    </tr>
    </thead>
    <tbody id="table"></tbody>
</table>
<ul id="pagination"></ul>

<%--PAGGINATION--%>
<script>
    let orders = [];
    <jsp:useBean id="myOrder" scope="request" type="java.util.List"/>
    <c:forEach items="${myOrder}" var="order">
    orders.push({
        master: '${order.orderMaster.firstName} ${order.orderMaster.lastName}',
        name: '${order.orderService.name}',
        price: '${order.orderService.price} UAH',
        orderDate: '${order.orderDate}',
        orderTime: '${order.orderTime}',
        orderStatus: '${order.orderStatus}'
    })
    </c:forEach>

    let table = document.querySelector('#table');
    let pagination = document.querySelector('#pagination');

    let notesOnPage = 5;
    let countOfItems = Math.ceil(orders.length / notesOnPage);

    let showPage = (function () {
        let active;

        return function (item) {
            if (active) {
                active.classList.remove('active');
            }
            active = item;

            item.classList.add('active');

            let pageNum = +item.innerHTML;

            let start = (pageNum - 1) * notesOnPage;
            let end = start + notesOnPage;

            let notes = orders.slice(start, end);

            table.innerHTML = '';
            for (let note of notes) {
                let tr = document.createElement('tr');
                table.appendChild(tr);

                createCell(note.master, tr);
                createCell(note.name, tr);
                createCell(note.price, tr);
                createCell(note.orderDate, tr);
                createCell(note.orderTime, tr);
                createCell(note.orderStatus, tr);
            }
        };
    }());

    let items = [];
    for (let i = 1; i <= countOfItems; i++) {
        let li = document.createElement('li');
        li.innerHTML = i;
        pagination.appendChild(li);
        items.push(li);
    }

    showPage(items[0]);

    for (let item of items) {
        item.addEventListener('click', function () {
            showPage(this);
        });
    }

    function createCell(text, tr) {
        let td = document.createElement('td');
        td.innerHTML = text;
        tr.appendChild(td);
    }
</script>

</body>

</html>
