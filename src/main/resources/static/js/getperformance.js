var pagesize=10;
function searchpm(page) {
    var starttime = $('#starttime').val();
    var endtime = $('#endtime').val();
    var searchtxt = $('#searchtxt').val();
    $.get("/api/search",{name: searchtxt,starttime:starttime,endtime:endtime,page:page,size:pagesize },function(r){
        showresult(page,r,"searchpm");
    });
}
function findbyoa(page) {
    $.get("/api/oa",{page:page,size:pagesize },function(r){
        showresult(page,r,"findbyoa");
    });
}
function showresult(page,r,fname) {
    var count = r.pages;
    $('#table-div').find('tbody').empty();
    $('.table-center tbody tr').unbind();
    if(count >0){
        $('.error').hide();
        var data = r.data;
        var s = '';
        $.each(data,function (index,item) {
            s += (
                "<tr><td data-id=" + item.id + " class='permancedata'>" + item.staffName +
                "</td><td>" + item.staffCode +
                "</td><td>" + item.totalScore +
                "</td><td>" + (getSmpFormatDateByLong(item.startTime,false) + "至" + getSmpFormatDateByLong(item.endTime,false)) +
                "</td><td>" + item.job +
                "</td><td>" + item.dept.name +
                "</td><td>" + item.leader +
                "</td><td class='details'><a href='#'>查看详情</a></td></tr>" +
                "</td></tr>"
            );
        });
        $('#table-div').find('tbody').append(s);
        $('#table-div').show();
        //表格点击跳转详情
        $('.table-center tbody tr').dblclick(function () {
            $this = $(this).find('.permancedata');
            var id = $this.attr('data-id');
            $.get("/api/id",{id: id },function(r){
                info(r);
                $('.search-table').hide();
                $('.tables').show();
            });
        });
        $('.details').click(function () {
            $this = $(this).parent().find('.permancedata');
            var id = $this.attr('data-id');
            $.get("/api/id",{id: id },function(r){
                info(r);
                $('.search-table').hide();
                $('.tables').show();
            });
        });
        pagination(page+1,count,fname);
    }else{
        $('.error').show();
        $('#table-div').hide();
    }
}
function pagination(curpage,totlepage,fname) {
    $('#pagination').empty();
    if(totlepage == 1){
        return;
    }
    var i = 1;
    var s = '';
    if(curpage>1){
        s += ('<li><a href="#" aria-label="Previous" onclick="javascript:'+ fname + '(' + (curpage-2) + ')"><span>&laquo;</span></a></li>'
        );
    }
    var start = 1;
    var end=10;
    if(totlepage > 10) {
        if(curpage > 6) {
            s += ('<li><a href="#" onclick="javascript:'+ fname + '(0)">1</a></li><li><a href="#">...</a></li>');
            if(parseInt(totlepage)>parseInt(curpage+6)) {
                start = curpage-4;
                end = curpage+4;

            }else {
                end = totlepage;
                start = totlepage - 9;
            }
        }
    }
    if(end > totlepage) end = totlepage;
    for(var i=start;parseInt(end-i+1)>0; i++) {
        if(i != curpage) {
            s += ('<li><a href="#" onclick="javascript:'+ fname + '('+ (i-1) +')">'+ i +'</a></li>');
        } else {
            s += ('<li class="active"><a href="#" >'+ i +'</a></li>');
        }
    }

    if(parseInt(totlepage)>parseInt(end)) {
        s += ('<li><a href="#">...</a></li><li><a href="#" onclick="javascript:'+ fname + '('+ (totlepage-1) + ')">' + totlepage + '</a></li>');
    }
    if(parseInt(totlepage)>parseInt(curpage)) {
        s += ('<li><a href="#" aria-label="Next" onclick="'+ fname + '(' + (curpage) + ')"><span>&raquo;</span></a></li>'
        );
    }
    $('#pagination').append(s);
    $('#pagination').show();
}
$(function () {
    $('.form-date').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        format: 'yyyy-mm-dd'
    });

    //查看表格禁止输入
    $('.tables input, .tables textarea, .tables select, .tables button').attr("disabled","disabled");

    //表格点击跳转详情
    $('.table-center tbody tr').click(function () {
        $('.search-table').hide();
        $('.tables').show();
    });

    //返回搜索界面
    $('.back-search').click(function () {
        $('.search-table').show();
        $('.tables').hide();
    });
});