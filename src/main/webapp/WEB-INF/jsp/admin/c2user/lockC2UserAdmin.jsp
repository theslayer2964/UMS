<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2/19/2024
  Time: 11:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<form:form id="addForm" modelAttribute="item" action="${formURL}" method="post" enctype="multipart/form-data">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
         style="display: none;" id="lockModal">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 600px;">
                <div class="modal-header">
                    <h4 class="modal-title mt-0">
                        <spring:message code="label.user.lock"/>
                        <span id="usernameFile"></span>
                    </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="file" class="control-label"><spring:message code="label.file"/></label>
                                <form:input type="file" id="file" path="file" cssClass="form-control"
                                            required="required"
                                            accept=".png,.pdf,.img"/>
                                    <%--<form:errors id="error_userName" path="pojo.userName" cssStyle="color: #F00;" />--%>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="updateDate" class="control-label"><spring:message
                                        code="label.uploadDate"/> </label>
                                <form:input type="date" id="updateDate" path="uploadDate" cssClass="form-control"
                                            required="required"/>
                                    <%--<form:errors id="error_password" path="pojo.password" cssStyle="color: #F00;" />--%>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary waves-effect" data-dismiss="modal"><spring:message
                            code="button.close"/></button>
                    <button type="submit" class="btn btn-info waves-effect waves-light"><spring:message
                            code="button.save"/></button>
                </div>
            </div>
        </div>
    </div>
    <input type="hidden" name="crudaction" value="unlock"/>
    <input type='hidden' name='pojo.username' id="nameUpload">
    <input type='hidden' name='pojo.userId' id="idUpload">
</form:form>