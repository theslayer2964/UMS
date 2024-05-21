<%@include file="/common/taglibs.jsp"%>
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 1000px;right: 50%">
                <div class="modal-header">
                    <h4 class="modal-title mt-0"><spring:message code="menu.system.statisticalUser"/> </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form:form id="listFormDelete" modelAttribute="item" action="${formURL}" method="post">
                        <div class="table-responsive">
                            <table id="datatable-buttons" class="table table-bordered"
                                   style="border-collapse: collapse; border-spacing: 0; width: 100%;">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th><spring:message code="label.username"/></th>
                                    <th><spring:message code="label.user.name"/></th>
                                    <th><spring:message code="label.description"/></th>
                                    <th><spring:message code="label.pc"/></th>
                                </tr>
                                </thead>
                                <tbody style="pointer-events: none">
                                <c:forEach items="${item}" var="r" varStatus="loop">
                                    <tr>
                                        <td class="table-number" style="max-width: 5px" >${loop.index+1}</td>
                                        <td style="min-width: 100px">${r.username}</td>
                                        <td style="min-width: 100px">${r.fullName}</td>
                                        <td style="min-width: 300px">${r.description}</td>
                                        <td>${r.granted_ip}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <input type="hidden" name="crudaction" value="unlock"/>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
