<%--
  Created by IntelliJ IDEA.
  User: phaolo
  Date: 2020-03-05
  Time: 05:14
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="common/header.jsp"%>
<%@ include file="common/navigation.jsp"%>
<div class="container">
  <div class="row">
    <div class="col-md-6 col-md-offset-3 ">
      <div class="panel panel-primary">
        <div class="panel-heading">Add TODO</div>
        <div class="panel-body">
          <form:form method="post" modelAttribute="todo">
            <form:hidden path="id" />
            <fieldset class="form-group">
              <form:label path="description">Description</form:label>
              <form:input path="description" type="text" class="form-control"
                          required="required" />
              <form:errors path="description" cssClass="text-warning" />
            </fieldset>

            <fieldset class="form-group">
              <form:label path="targetDate">Target Date</form:label>
              <form:input path="targetDate" type="text" class="form-control"
                          required="required" />
              <form:errors path="targetDate" cssClass="text-warning" />
            </fieldset>

            <button type="submit" class="btn btn-success">Save</button>
          </form:form>
        </div>
      </div>
    </div>
  </div>
</div>
<%@ include file="common/footer.jsp"%>
