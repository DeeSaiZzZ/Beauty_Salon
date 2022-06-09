<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404</title>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400italic,400,700&subset=latin,cyrillic'
          rel='stylesheet' type='text/css'>

    <style>
        <%@include file="/WEB-INF/view/src/css/error.css"%>
    </style>

</head>

<body style="background: url(${pageContext.request.contextPath}/img/404.png) center no-repeat #fff">

<div class="error">
    <h1>Error 404 - NOT FOUND</h1>
</div>

<div class="content">
    <a href="${pageContext.request.contextPath}/main-servlet/main_page?action=open_master_list">Go to home page</a>
</div>

</body>
</html>




