<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/admin/department.html"/>
<form id="addForm" modelAttribute="item">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
         style="display: none;" id="myModal2">
        <div class="modal-dialog" >
            <div class="modal-content" style="width: 900px;right: 50%">
                <div class="modal-header">
                    <h4 class="modal-title mt-0">
                        <spring:message code="label.department.user"/>
                        <span id="depart"></span>
                        <input type="hidden" id="depart_id">
                    </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="header-title sub-header">
                        <button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#demo1" id="addManger">
                            <spring:message code="button.add"/>
                        </button>
                        <%@include file="insert_user_list.jsp" %>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
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
    </div>
</form>