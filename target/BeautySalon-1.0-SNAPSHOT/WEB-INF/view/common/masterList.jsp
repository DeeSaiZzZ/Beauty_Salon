<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie.locale.value}"/>
<fmt:setBundle basename="localization"/>

<html>
<head>
    <title>MasterList</title>
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
        <%@include file="/WEB-INF/view/src/css/style.css"%>
    </style>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div class="card">
    <div class="card-body">
        <div>
            <form action="${pageContext.request.contextPath}/main-servlet/main_page" method="get">
                <select style="display: inline-block; width: 250px" class="custom-select" name="sort_type">
                    <option selected="selected" disabled><fmt:message key="masterList.sort"/></option>
                    <option id="standard" value="standard"><fmt:message key="masterList.sortStandard"/></option>
                    <option id="by_name_up" value="by_name_up"><fmt:message key="masterList.sortByName"/> ↑</option>
                    <option id="by_rate_up" value="by_rate_up"><fmt:message key="masterList.sortByRate"/> ↑</option>
                    <option id="by_name_down" value="by_name_down"><fmt:message key="masterList.sortByName"/> ↓</option>
                    <option id="by_rate_down" value="by_rate_down"><fmt:message key="masterList.sortByRate"/> ↓</option>
                </select>
                <div class="btn-group">
                    <button type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown">
                        <fmt:message key="common.filter"/>
                    </button>
                    <ul class="dropdown-menu">
                        <li>
                            <label class="checkbox"><input id="1" name="filer_param" value="1" type="checkbox">
                                <fmt:message key="filter.hairdresser"/>
                            </label>
                        </li>

                        <li>
                            <label class="checkbox"><input id="2" name="filer_param" value="2" type="checkbox">
                                <fmt:message key="filter.spaMaster"/>
                            </label>
                        </li>

                        <li>
                            <label class="checkbox"><input id="3" name="filer_param" value="3" type="checkbox">
                                <fmt:message key="filter.manicurist"/>
                            </label>
                        </li>

                        <li>
                            <label class="checkbox"><input id="4" name="filer_param" value="4" type="checkbox">
                                <fmt:message key="filter.beautician"/>
                            </label>
                        </li>

                        <li>
                            <label class="checkbox"><input id="5" name="filer_param" value="5" type="checkbox">
                                <fmt:message key="filter.makeUpArtist"/>
                            </label>
                        </li>

                    </ul>
                </div>

                <button style="margin-left: 5px;display: inline-block" name="action" value="open_master_list"
                        class="btn btn-outline-secondary">
                    <fmt:message key="common.submit"/>
                </button>

            </form>
        </div>
    </div>
</div>

<table class="table table-bordered table-hover">
    <thead>
    <tr>
        <th scope="col"><fmt:message key="masterList.emil"/></th>
        <th scope="col"><fmt:message key="masterList.name"/></th>
        <th scope="col"><fmt:message key="masterList.surname"/></th>
        <th scope="col"><fmt:message key="masterList.speciality"/></th>
        <th scope="col"><fmt:message key="masterList.rate"/></th>
    </tr>
    </thead>
    <tbody id="table"></tbody>
</table>
<ul id="pagination"></ul>

<%--PAGGINATION--%>
<script>
    let users = [];
    <jsp:useBean id="masterList" scope="request" type="java.util.List"/>
    <c:forEach items="${masterList}" var="master">
    users.push({
        email: '${master.email}',
        firstName: '${master.firstName}',
        lastName: '${master.lastName}',
        speciality: '${master.specialityName}',
        rate: '${master.rate}'
    })
    </c:forEach>

    let table = document.querySelector('#table');
    let pagination = document.querySelector('#pagination');

    let notesOnPage = 5;
    let countOfItems = Math.ceil(users.length / notesOnPage);

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

            let notes = users.slice(start, end);

            table.innerHTML = '';
            for (let note of notes) {
                let tr = document.createElement('tr');
                table.appendChild(tr);

                createCell(note.email, tr);
                createCell(note.firstName, tr);
                createCell(note.lastName, tr);
                createCell(note.speciality, tr);
                createCell(note.rate, tr);
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

    document.getElementById("${selected_sort}").setAttribute("selected", "selected");

    <c:forEach items="${selected_filters}" var="filter">
    document.getElementById(${filter}).checked = true;
    </c:forEach>


</script>
</body>
</html>