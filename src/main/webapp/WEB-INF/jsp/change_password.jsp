<%@include file="/common/taglibs.jsp"%>
<c:url var="formURL" value="/change_password.html"/>
<form:form id="changepass_form" modelAttribute="item" action="${formURL}" method="post">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;" id="change_pass_modal">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 600px;">
                <div class="modal-header">
                    <h4 class="modal-title mt-0"><spring:message code="menu.system.change_password"/> </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label for="old_password" class="control-label"><spring:message
                                    code="label.password"/> </label>
                            <input type="password" id="old_password" name="old_pass" cssClass="form-control"
                                        onblur="validatePassword()" />
<%--                            <small class="text-danger" id="password-validation"></small>--%>
                        </div>
                    </div>
                    <div class="form-group col-md-12">
                        <label for="new_password" class="control-label"><spring:message
                                code="label.password"/> </label>
                        <input type="password" id="new_password"  cssClass="form-control"
                                    onblur="validatePassword()" name="new_password"/>
                    </div>
                    <div class="form-group col-md-12">
                        <label for="repeat_password" class="control-label"><spring:message
                                code="label.password"/> </label>
                        <input type="password" id="repeat_password"  cssClass="form-control"
                                    onblur="validatePassword()" name="repeat_password"/>
<%--                        <small class="text-danger" id="password-validation"></small>--%>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary waves-effect" data-dismiss="modal"><spring:message code="button.close"/> </button>
                    <button type="submit" class="btn btn-info waves-effect waves-light"><spring:message code="button.save"/></button>
                </div>
            </div>
        </div>
    </div>
</form:form>