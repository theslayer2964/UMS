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
                    <h4 class="modal-title mt-0"><spring:message code="label.url.info"/> </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="code" class="control-label"><spring:message code="label.code"/></label>
                                <form:input id="code" path="pojo.code" cssClass="form-control" required="required"/>
                                <form:errors id="error_code" path="pojo.code" cssStyle="color: #F00;" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="name" class="control-label"><spring:message code="label.name"/> </label>
                                <form:input id="name" path="pojo.name" cssClass="form-control" required="required"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group no-margin">
                                <label for="path" class="control-label"><spring:message code="label.path"/> </label>
                                <form:input id="path" path="pojo.path" cssClass="form-control" required="required"/>
                                <form:errors id="error_path" path="pojo.path" cssStyle="color: #F00;" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group no-margin">
                                <label for="urlGroupId" class="control-label"><spring:message code="label.url.group"/> </label>
                                <form:select id="urlGroupId" path="pojo.urlGroup.urlGroupId" class="form-control">
                                    <option value="-1"><fmt:message key="common.select"/></option>
                                    <form:options items="${listOfUrlGroup}" itemLabel="name" itemValue="urlGroupId"/>
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
    <form:hidden path="pojo.urlId" id="urlId"/>
</form:form>
