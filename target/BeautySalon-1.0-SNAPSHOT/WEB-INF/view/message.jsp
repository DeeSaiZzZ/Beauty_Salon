<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<c:if test="${not empty message}">
    <script>
        alert("${message}")
    </script>
</c:if>

<c:if test="${not empty message}">
    ${sessionScope.remove('message')}
</c:if>
</body>
</html>
