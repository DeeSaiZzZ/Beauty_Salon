<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie.locale.value}"/>
<fmt:setBundle basename="localization"/>

<html>
<head>
    <title>Title</title>
    <style>
        <%@include file="/WEB-INF/view/src/css/header.css"%>
    </style>
</head>
<body>
<div class="header">
    <nav class="navbar navbar-expand-lg">
        <a class="navbar-brand">Beauty Factory</a>
        <ul class="menu">
            <li>
                <a href="${pageContext.request.contextPath}/main-servlet/main_page?action=open_master_list">
                    <fmt:message key="header.masterList"/>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/main-servlet/main_page?action=open_service_list">
                    <fmt:message key="header.serviceLis"/>
                </a>
            </li>
            <c:if test="${sessionScope.currentUser.roleId == 3}">
                <li>
                    <a href="${pageContext.request.contextPath}/main-servlet/main_page?action=open_order_reg_form">
                        <fmt:message key="header.register"/>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/main-servlet/main_page?action=open_my_order_list">
                        <fmt:message key="header.myService"/>
                    </a>
                </li>
            </c:if>
            <c:if test="${sessionScope.currentUser.roleId == 2}">
                <li>
                    <a href="${pageContext.request.contextPath}/main-servlet/main_page?action=show_timetable">
                        <fmt:message key="header.myTimeTable"/>
                    </a>
                </li>
            </c:if>
            <c:if test="${sessionScope.currentUser.roleId == 1}">
                <li>
                    <a href="${pageContext.request.contextPath}/main-servlet/main_page?action=check_request">
                        <fmt:message key="header.checkRequest"/>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/main-servlet/main_page?action=check_all_order">
                        <fmt:message key="header.allOrder"/>
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>

    <div id="language_selector" class="language_selector">
        <form action="${pageContext.request.contextPath}/main-servlet/main_page?action=change_lan" method="post">
            <button class="lang_button" name="lang" value="uk" type="submit">
                <img src="${pageContext.request.contextPath}/img/ukr_flag.png">
            </button>
            <button class="lang_button" style="border: none" name="lang" value="en" type="submit">
                <img src="${pageContext.request.contextPath}/img/eng_flag.png"/>
            </button>
        </form>
    </div>

    <div class="userInfo">
        <c:choose>
            <c:when test="${sessionScope.currentUser!=null}">
                <span>${sessionScope.currentUser.firstName}</span>
                <span>${sessionScope.currentUser.lastName}</span>
                <form style="display: inline-block" action="${pageContext.request.contextPath}/main-servlet/main_page"
                      method="post">
                    <input type="hidden" name="action" value="logout_user"/>
                    <button style="padding: 2px" class="btn btn-outline-danger">Log out</button>
                </form>
            </c:when>
            <c:otherwise>
                <button onclick="location.href='${pageContext.request.contextPath}/index.jsp'"
                        style="padding: 2px; margin-bottom: 5px" class="btn btn-outline-success">Sing in
                </button>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
