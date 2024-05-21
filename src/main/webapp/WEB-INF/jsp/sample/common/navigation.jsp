<%--
  Created by IntelliJ IDEA.
  User: phaolo
  Date: 2020-03-04
  Time: 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<nav role="navigation" class="navbar navbar-default">
    <div class="">
        <a href="http://www.javaguides.net" class="navbar-brand">Java Guides</a>
    </div>
    <div class="navbar-collapse">
        <ul class="nav navbar-nav">
            <li class="active"><a href="/">Home</a></li>
            <security:authorize access="hasAnyRole('ADMIN','SAMPLE')">
            <li><a href="/sample/list-todos.html">Todos</a></li>
            </security:authorize>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="/logout.html">Logout</a></li>
        </ul>
    </div>
</nav>