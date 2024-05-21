<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 4/24/2024
  Time: 4:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<div class="modal fade" style="display: none" id="submitFormHistoryModal">
    <div class="modal-dialog">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 1000px;right: 50%">
                <div class="modal-header">
                    <h4 class="modal-title mt-0">
                        <spring:message code="label.user.info"/>
                        <span id="usernameView2"></span>
                    </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true" onclick="destroyTable()">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="submitHistoryForm">

                    </form>
                    <div class="table-responsive" style="display: none;" id="table2">
                        <table id="example2" class="display dataTable cell-border table table-bordered"
                               style="border-collapse: collapse; border-spacing: 0; width: 100%;"></table>
                    </div>
                    <div id="loadingEvent2">
                        <div class="spinner-border text-success" role="status">
                            <span class="sr-only">Loading...</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
