<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 1/19/2024
  Time: 4:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<form:form id="addForm" modelAttribute="item" action="${formURL}" method="post">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
         style="display: none;" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 600px;">
                <div class="modal-header">
                    <h4 class="modal-title mt-0"><spring:message code="label.user.info"/></h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="programName" class="control-label"><spring:message
                                        code="label.program.name"/></label>
                                <form:input id="programName" path="pojo.programName" cssClass="form-control"
                                            required="required"/>
                                <form:errors id="error_programName" path="pojo.programName" cssStyle="color: #F00;"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="url" class="control-label"><spring:message
                                        code="label.user_role_url_acl.path"/></label>
                                <form:input id="url" path="pojo.url" data-validate-length="6,8" cssClass="form-control"
                                            required="required"/>
                                <form:errors id="error_url" path="pojo.url" cssStyle="color: #F00;"/>
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
    <!-- /.modal -->
    <!-- hidden field -->
    <input type="hidden" name="crudaction" value="insert-update"/>
    <form:hidden path="pojo.programId" id="programId"/>
</form:form>