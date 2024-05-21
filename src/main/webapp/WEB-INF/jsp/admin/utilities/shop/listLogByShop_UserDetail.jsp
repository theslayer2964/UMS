<%@include file="/common/taglibs.jsp" %>
<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
     style="display: none;" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 600px;">
            <div class="modal-header">
                <h4 class="modal-title mt-0">
                    <spring:message code="label.user.shop_user_detail"/>
                    <span id="shopcode_search"></span>
                </h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
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
<!-- /.modal -->
