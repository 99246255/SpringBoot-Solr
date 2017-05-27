function report() {
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
    $('#table-div').find('tbody').empty();
    var starttime = $('#starttime').val();
    var endtime = $('#endtime').val();
    var searchtxt = $('#searchtxt').val();
    $.get("/api/report",{name: searchtxt,starttime:starttime,endtime:endtime},function(r){
        if(r == null || r.length == 0){
            $('.error').show();
            $('#table-div').hide();
        }else{
            var s = '';
            $.each(r,function (index,item) {
                s += (
                "<tr><td>" + item.deptName +
                "</td><td>" + item.a +
                "</td><td>" + item.b +
                "</td><td>" + item.c +
                "</td><td>" + item.d +
                "</td><td>" + item.e +
                "</td></tr>"
                );
            });
            $('#table-div').find('tbody').append(s);
            $('#table-div').show();
            $('.error').hide();
        }
    });
}
function loadreport() {
    loaduser();
    report();
}