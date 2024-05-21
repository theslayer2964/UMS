<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/list/department.html"/>
<form:form id="changeIp_mSale" modelAttribute="item" action="${formURL}" method="post" name="ip_granted_form">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
         style="display: none;" id="mSaleModal">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 600px;">
                <div class="modal-header">
                    <h4 class="modal-title mt-0">
                        <spring:message code="button.update_msale"/>
                    </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group no-margin">
                                <label for="m_sale_ip" class="control-label"><spring:message
                                        code="button.msalte"/></label>
                                <form:textarea id="m_sale_ip" name="m_sale_ip"
                                            path="granted_ip_mSale" onblur="validateIP()"
                                               cssClass="form-control autogrow" style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 150px;"/>
                                <small class="text-danger" id="ip-validation"></small>
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
        <input type="hidden" name="crudaction" value="change-mSale"/>
    </div>
</form:form>
