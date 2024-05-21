<%@include file="/common/taglibs.jsp"%>
<form:form id="history_Form" modelAttribute="item" action="${formURL}" method="post">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
         style="display: none;" id="history_modal">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 1000px;right: 50%">
                <div class="modal-header">
                    <h4 class="modal-title mt-0">
                        <spring:message code="label.user.info"/>
                        <span id="history_user">XXX</span>
                    </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="table-responsive">
                                <table id="history_table" class="display dataTable cell-border table table-bordered"
                                       style="border-collapse: collapse; border-spacing: 0; width: 100%;">
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary waves-effect" data-dismiss="modal"><spring:message code="button.close"/> </button>
                </div>
            </div>
        </div>
    </div><!-- /.modal -->
</form:form>