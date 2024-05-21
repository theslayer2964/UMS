<%--
  Created by IntelliJ IDEA.
  User: phaolo
  Date: 2020-03-19
  Time: 03:08
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp"%>
<form:form id="addForm" modelAttribute="item" action="${formURL}" method="post">
    <div class="modal fade" id="myModal"  aria-labelledby="myModalLabel" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 600px;">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel"><spring:message code="label.user_role_url_acl.remaining"/></h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">x</span>
                    </button>
                </div>
                <div class="modal-body">
                    <!-- form info -->
                    <div class="x_content">
                        <c:if test="${not empty messageValidator }">
                            <div class="form-group">
                                <span id="error_select" style="color: #F00;">${messageValidator }</span>
                            </div>
                        </c:if>
                        <div class="row">
                            <div class="col-md-3" style="padding-top: 7px">
                                <label class="control-label"><spring:message code="label.url.group"/></label>
                            </div>
                            <div class="col-md-9">
                                <div class="form-group">
                                    <form:select path="urlGroupId" class="form-control" id="urlGroupId">
                                        <option value="-1"><spring:message code="common.select"/></option>
                                        <form:options items="${listOfUrlGroup}" itemLabel="name" itemValue="urlGroupId"/>
                                    </form:select>
                                </div>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr colspan="3" class="headings">
                                        <th><input type="checkbox" id="check-all"></th>
                                        <th class="column-title"><spring:message code="label.user_role_url_acl.code"/></th>
                                        <th class="column-title"><spring:message code="label.user_role_url_acl.name"/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="url" items="${listOfUrl }" varStatus="loop">
                                    <tr <c:choose>
                                          <c:when test="${(loop.count%2) == 1 }">class="even pointer displayStatus"</c:when>
                                          <c:otherwise>class="odd pointer displayStatus"</c:otherwise>
                                        </c:choose> >
                                        <td class="a-center" style="width: 5%">
                                            <input type="checkbox" class="checkParent" name="checkList" value="${url.urlId }">
                                        </td>
                                        <td style="width: 40%">${url.code }</td>
                                        <td>${url.name }</td>
                                        <input type="hidden" name="urlGroup" value="${url.urlGroup.urlGroupId}"/>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary waves-effect" data-dismiss="modal"><spring:message code="button.cancel"/></button>
                    <button type="submit" class="btn btn-primary" style="width: 100px;" id="btnSave"><spring:message code="button.save"/></button>
                </div>
            </div>
        </div>
    </div>
    <!-- hidden field -->
    <input type="hidden" name="crudaction" value="insert-update" />
    <form:hidden path="pojo.userRole.userRoleId"/>
</form:form>
<!-- form info: end -->
<script>
    $(document).ready(function() {
       $('#urlGroupId').change(function () {
           var urlGroupId = $('#urlGroupId').val();
           var trTag = $(".displayStatus");
           var urlGroupArr = $("input[name*='urlGroup']");

           for (idx = 0; idx < trTag.length; idx++){
                if (urlGroupArr[idx].value == urlGroupId) {
                    trTag[idx].hidden = false;
                } else {
                    trTag[idx].hidden = true;
                }
           }
       });
    });
</script>