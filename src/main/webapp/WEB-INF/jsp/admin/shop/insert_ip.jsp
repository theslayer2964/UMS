<%@include file="/common/taglibs.jsp" %>
<form:form id="ipForm" modelAttribute="item" action="${formURL}" method="post">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
         style="display: none;" id="ipGrantedModal">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 850px;right: 50%">
                <div class="modal-header">
                    <h4 class="modal-title mt-0"><spring:message code="label.shop_ip.info"/>
                        <span id="shopName_grantedIp">xxx</span>
                    </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row" id="success">
                        <div class="col-md-12">
                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th><spring:message code="label.shop.granted_ip"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td id="ip"></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row" id="error">
                        <h4 id="error-messge"></h4>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form:form>
