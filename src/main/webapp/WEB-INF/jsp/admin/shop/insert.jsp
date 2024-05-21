<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/list/department.html"/>
<form:form id="updateIP_Shop" modelAttribute="item" action="${formURL}" method="post" name="update_ip_shopcode">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
         style="display: none;" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 600px;">
                <div class="modal-header">
                    <h4 class="modal-title mt-0">
                        <spring:message code="label.department.info"/>
                        <span id="ten"></span>
                    </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group no-margin">
                                <label for="name" class="control-label"><spring:message
                                        code="label.department.name"/> </label>
                                <form:input type="text" id="name" path="pojo.name"
                                            required="required" disabled="true"
                                            cssClass="form-control"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group no-margin">
                                <label for="grantedIp_shop" class="control-label"><spring:message
                                        code="label.c2user.ip"/> </label>
                                <form:textarea type="text" id="grantedIp_shop" path="grantedIp_shop"
                                            required="required" onblur="validateIp_UpdateForm()" name="grantedIp_shop"
                                            cssClass="form-control" style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 150px;"/>
                                <small class="text-danger" id="ip-granted-validation"></small>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group no-margin">
                                <label for="shop_type" class="control-label"><spring:message
                                        code="label.department.shop-type"/></label>
                                <form:select id="shop_type" path="shop_type" class="form-control"
                                             name="shop_type">
                                    <option value="1"><spring:message code="label.department.shop-type.shop"/></option>
                                    <option value="0"><spring:message code="label.department.shop-type.branch"/></option>
                                </form:select>
                                <small class="text-danger" id="groupUser-validation"></small>
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
    <input type="hidden" name="crudaction" value="insert-update"/>
    <form:hidden path="pojo.shop_code" id="shop_code"/>
</form:form>