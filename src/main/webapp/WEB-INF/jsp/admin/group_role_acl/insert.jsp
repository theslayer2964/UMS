<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 01/13/2021
  Time: 8:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<form:form id="addForm" modelAttribute="item" action="${formURL}" method="post">
    <div class="modal fade" id="myModal" aria-labelledby="myModalLabel" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 600px;">
                <div class="modal-header">
                    <h4 class="modal-title mt-0"><spring:message code="label.user_role_url_acl.remaining"/></h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">x</span>
                    </button>
                </div>
                <div class="modal-body">
                    <c:if test="${not empty messageValidator }">
                        <div class="row">
                            <div class="form-group">
                                <span id="error_select" style="color: #F00;">${messageValidator }</span>
                            </div>
                        </div>
                    </c:if>
                    <div class="row">
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                <tr class="headings">
                                    <th width="30px"><input type="checkbox" id="check-all"></th>
                                    <th class="column-title" width="200px"><spring:message code="userrole.code"/></th>
                                    <th class="column-title"><spring:message code="userrole.name"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="userRole" items="${listOfUserRole}" varStatus="loop">
                                    <tr
                                            <c:choose>
                                            <c:when test="${(loop.count%2) == 1 }">class="even pointer" </c:when>
                                                <c:otherwise>class="odd pointer"</c:otherwise>
                                    </c:choose> >
                                        <td class="a-center" style="width: 50px;">
                                            <form:checkbox path="checkList" value="${userRole.userRoleId }" cssClass="checkChilds"/>
                                        </td>
                                        <td style="width: 100px;">${userRole.code }</td>
                                        <td>${userRole.name }</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary waves-effect" data-dismiss="modal"><spring:message code="button.close"/></button>
                    <button type="submit" class="btn btn-info waves-effect waves-light"><spring:message code="button.save"/></button>
                </div>
            </div>
        </div>
    </div>
    <!-- hidden field -->
    <input type="hidden" name="crudaction" value="insert-update"/>
    <form:hidden path="pojo.userGroup.userGroupId"/>
</form:form>
<script>
    $("#check-all").click(function () {
        if ($(this).is(":checked")) {
            $(".checkChilds").prop("checked", true);
        } else {
            $(".checkChilds").prop("checked", false);
        }
    });
</script>