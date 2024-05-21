<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2/2/2024
  Time: 11:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<form:form id="changeshopForm" modelAttribute="item" action="${formURL}" method="post" name="changeshopForm">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
         style="display: none;" id="myModalChange">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 800px;right: 50%">
                <div class="modal-header">
                    <h4 class="modal-title mt-0">
                        <spring:message code="label.user.changeShop"/>
                        <span id="userChangeshop"></span>
                    </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <h4>
                                    <label for="shopcode_old" class="control-label"><spring:message
                                            code="label.c2user.shopcode_old"/>:</label>
                                    <br>
                                    <span id="shopcode_old"/>
                                </h4>
                            </div>
                        </div>
                        <div class="col-md-6 form-group" id="parentShop">
                            <label for="shop_code2" class="control-label"><spring:message
                                    code="label.c2user.shopcode_new"/></label>
                            <form:select id="shop_code2" name="shop_code2" path="pojo.shopCode"
                                         class="form-control js-select2">
                                <option value="-1"><fmt:message key="commonet.shop_code"/></option>
                                <form:options items="${listShop2}" item="shop" itemLabel="name"
                                              itemValue="shop_code"/>
                            </form:select>
                            <small class="text-danger" id="shop-validation"></small>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="email" class="control-label">
                                    <spring:message code="label.cskk.email"/></label>
                                <form:input type="text" id="email" name="email"
                                            path="email"
                                            cssClass="form-control"/>
                                <small class="text-danger" id="email-changeShop-validation"></small>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="granted_ip_new" class="control-label">
                                    <spring:message code="label.IP"/></label>
                                <form:input type="text" id="granted_ip_new" name="granted_ip_new"
                                            path="pojo.granted_ip"
                                            cssClass="form-control"/>
                                <small class="text-danger" id="ip-validation"></small>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <table id="datatable-buttons" class="table table-bordered" name="table"
                                       style="border-collapse: collapse; border-spacing: 0;width: 100%">
                                    <thead>
                                    <tr>
                                        <th><spring:message code="label.action"/></th>
                                        <th><spring:message code="label.program.name"/></th>
                                        <th><spring:message code="label.user_role_url_acl.path"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${listProgram}" var="r" varStatus="loop">
                                        <tr>
                                            <td class="table-number">
                                                <input type="checkbox" name="program" value="${r.programId}">
                                            </td>
                                            <td width="40%">${r.programName}</td>
                                            <td width="40%">${r.url}</td>
                                        </tr>
                                    </c:forEach>
                                    <small class="text-danger" id="programlist-validation"></small>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary waves-effect" data-dismiss="modal"><spring:message
                            code="button.close"/></button>
                    <button type="submit" class="btn btn-info waves-effect waves-light"><spring:message
                            code="button.saveInfo"/></button>
                </div>
            </div>
        </div>
    </div>
    <form:input type="hidden" path="program" id="program2"/>
    <form:input type="hidden" path="shopcode_old" id="shop_old2"/>
    <form:input type="hidden" path="pojo.username" id="username2"/>
    <form:input type="hidden" path="pojo.fullName" id="fullname2"/>
    <input type="hidden" name="crudaction" value="change-shop"/>
</form:form>