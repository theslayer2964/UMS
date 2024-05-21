
<%@include file="/common/taglibs.jsp"%>
<form:form id="addForm" modelAttribute="item" action="${formURL}" method="post">
<div class="modal fade" id="myModal"  aria-labelledby="myModalLabel" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" >
        <div class="modal-content" style="width: 600px;">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel"><spring:message code="label.user_role.remaining"/></h4>
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
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr class="headings">
                                    <th><input type="checkbox" id="check-all"></th>
                                    <th class="column-title"><spring:message code="userrole.code"/></th>
                                    <th class="column-title"><spring:message code="userrole.name"/></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="userRole" items="${listOfUserRole }" varStatus="loop">
                                    <tr <c:choose><c:when test="${(loop.count%2) == 1 }">class="even pointer"</c:when><c:otherwise>class="odd pointer"</c:otherwise></c:choose> >
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

    <!-- hidden field -->
    <input type="hidden" name="crudaction" value="insert-update" />
    <form:hidden path="pojo.user.userId"/>
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