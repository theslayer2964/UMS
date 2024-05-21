<%--
  Created by IntelliJ IDEA.
  User: phaolo
  Date: 2020-03-19
  Time: 03:08
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp"%>
<form:form id="addForm" modelAttribute="item" action="${formURL}" method="post">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 600px;">
                <div class="modal-header">
                    <h4 class="modal-title mt-0"><spring:message code="label.user.info"/> </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="userName" class="control-label"><spring:message code="label.username"/></label>
                                <form:input id="userName" path="pojo.userName" cssClass="form-control"/>
                                <form:errors id="error_userName" path="pojo.userName" cssStyle="color: #F00;" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="password" class="control-label"><spring:message code="label.password"/> </label>
                                <form:password id="password" path="pojo.password" data-validate-length="6,8" cssClass="form-control"/>
                                <form:errors id="error_password" path="pojo.password" cssStyle="color: #F00;" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group no-margin">
                                <label for="displayName" class="control-label"><spring:message code="label.user.name"/> </label>
                                <form:input id="displayName" path="pojo.displayName"  cssClass="form-control"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group no-margin">
                                <label for="email" class="control-label"><spring:message code="label.user.email"/> </label>
                                <form:input type="email" id="email" path="pojo.email" cssClass="form-control"/>
                                <form:errors id="error_email" path="pojo.email" cssStyle="color: #F00;" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group no-margin">
                                <label for="accountType" class="control-label"><spring:message code="label.user.type"/> </label>
                                <form:select id="accountType" path="pojo.accountType" cssClass="form-control" required="required">
                                    <option value="-1"><spring:message code="label.user.default"/></option>
                                    <option value="1"><spring:message code="label.user.system_type"/></option>
                                    <option value="2"><spring:message code="label.user.LDAP_type"/></option>
                                    <option value="3"><spring:message code="label.user.connect_type"/></option>
                                </form:select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group no-margin">
                                <label for="userGroupId" class="control-label"><spring:message code="label.url.group"/> </label>
                                <form:select id="userGroupId" path="pojo.userGroup.userGroupId" class="form-control">
                                    <option value="-1"><spring:message code="label.url.default"/></option>
                                    <form:options items="${listOfUserGroup}" itemLabel="name" itemValue="userGroupId"/>
                                </form:select>
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
