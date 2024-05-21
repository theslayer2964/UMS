<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 1/5/2024
  Time: 9:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp"%>
<form:form id="addForm" modelAttribute="item" action="${formURL}" method="post" name="addForm">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 600px;">
                <div class="modal-header">
                    <h4 class="modal-title mt-0">
                        <spring:message code="label.user.info"/>
                        <span id="usernamesearch">XXX</span>
                    </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group no-margin">
                                <label for="status2" class="control-label"><spring:message code="label.user.status"/> </label>
                                <form:select id="status2" path="pojo.status" cssClass="form-control" required="required">
                                    <option value="0"><spring:message code="label.c2user.cancel"/></option>
                                    <option value="2"><spring:message code="label.c2user.logTemp"/></option>
                                    <option value="1"><spring:message code="label.c2user.using"/></option>
                                    <option value="4"><spring:message code="label.c2user.log"/></option>
                                </form:select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <label for="granted_ip" class="control-label"><spring:message code="label.c2user.granted-CHT"/> </label>
                            <form:input path="email" cssClass="form-control" onblur="validateEmailForGrantedPermission()"
                                        cssStyle="text-transform: uppercase" name="email"/>
                            <small class="text-danger" id="email-validation"></small>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group no-margin">
                                <label for="granted_ip" class="control-label"><spring:message code="label.c2user.ip"/> </label>
                                <form:textarea id="granted_ip" path="pojo.granted_ip"  cssClass="form-control" required="required"
                                cssStyle="height: 100px"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary waves-effect" data-dismiss="modal"><spring:message code="button.close"/> </button>
                    <button type="submit" class="btn btn-info waves-effect waves-light"><spring:message code="button.save"/></button>
                </div>
            </div>
        </div>
    </div><!-- /.modal -->
    <!-- hidden field -->
    <input type="hidden" name="crudaction" value="insert-update" />
    <form:hidden path="pojo.userId" id="userId"/>
</form:form>