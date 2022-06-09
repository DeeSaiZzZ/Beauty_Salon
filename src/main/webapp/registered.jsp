<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie.get(locale)}"/>
<fmt:setBundle basename="localization"/>

<html>
<head>
    <title>Registration</title>
    <style>
        <%@include file="/WEB-INF/view/src/css/style.css"%>
    </style>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <script>
        function validEnter() {
            if (document.regForm.email.value === "" || document.regForm.pass.value === "" ||
                document.regForm.name.value === "" || document.regForm.surname.value === "") {
                alert("Enter all fields!");
                document.regForm.email.focus();
                return false;
            }
        }
    </script>
</head>
<body>
<c:if test="${not empty currentUser}">
    <jsp:forward page="main-servlet/main_page?action=open_master_list"/>
</c:if>
<br>
<div class="container col-md-4">
    <div class="card">
        <div class="card-header">
            <h1 class="card-title"><fmt:message key="registration_jsp.info"/></h1>
        </div>
        <div class="card-body">
            <c:if test="${message!=null}"><h3 class="attention">${message}</h3></c:if>
            <form action="${pageContext.request.contextPath}/main-servlet/main_page" method="post"
                  name="regForm"
                  onsubmit="return validEnter();">
                <div class="form-group">
                    <label><fmt:message key="common.email"/></label>
                    <input type="email" class="form-control" name="email" placeholder="Enter email">
                </div>
                <div class="form-group">
                    <label><fmt:message key="common.password"/></label>
                    <input type="password" class="form-control"
                           name="pass" placeholder="Enter password">
                </div>
                <div class="form-group">
                    <label><fmt:message key="registration_jsp.info.name"/></label>
                    <input type="text" class="form-control"
                           name="name" placeholder="Enter name">
                </div>
                <div class="form-group">
                    <label><fmt:message key="registration_jsp.info.surname"/></label>
                    <input type="text" class="form-control"
                           name="surname" placeholder="Enter surname">
                </div>
                <button type="submit" name="action" value="registered_user" class="btn btn-outline-success"><fmt:message
                        key="common.submit"/>
                </button>
                <a class="withoutAuth"
                   href="${pageContext.request.contextPath}/main-servlet/main_page?action=open_master_list">
                    <fmt:message key="common.reg.log.withoutAuth"/></a>
                <div class="navigate">
                    <a href="${pageContext.request.contextPath}/index.jsp">
                        <fmt:message key="registration_jsp.info.goToLog"/></a>
                </div>
            </form>
        </div>
    </div>
</div>

<c:if test="${not empty message}">
    ${sessionScope.remove('message')}
</c:if>

</body>
</html>
