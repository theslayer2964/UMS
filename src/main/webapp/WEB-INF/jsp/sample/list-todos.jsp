<%--
  Created by IntelliJ IDEA.
  User: phaolo
  Date: 2020-03-05
  Time: 05:12
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="common/header.jsp"%>
<%@ include file="common/navigation.jsp"%>

<div class="container">
  <div>
    <a type="button" class="btn btn-primary btn-md" href="/sample/add-todo">Add Todo</a>
  </div>
  <br>
  <div class="panel panel-primary">
    <div class="panel-heading">
      <h3>List of TODO's</h3>
    </div>
    <div class="panel-body">
      <table class="table table-striped">
        <thead>
        <tr>
          <th width="40%">Description</th>
          <th width="40%">Target Date</th>
          <th width="20%"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${todos}" var="todo">
          <tr>
            <td>${todo.description}</td>
            <td><fmt:formatDate value="${todo.targetDate}"
                                pattern="dd/MM/yyyy" /></td>
            <td><a type="button" class="btn btn-success"
                   href="/sample/update-todo?id=${todo.id}">Update</a>
              <a type="button" class="btn btn-warning"
                 href="/sample/delete-todo?id=${todo.id}">Delete</a></td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>

</div>
<%@ include file="common/footer.jsp"%>
