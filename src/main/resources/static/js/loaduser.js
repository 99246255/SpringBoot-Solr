function loaduser(){
    $.get("/getUser",function(r){
        if (!r.success) {
            // 错误提示
            return;
        }
        $(".username").text(r.name);
        if(!r.hr){
            $("#search").hide();
            $("#report").hide();
        }else{
            $("#search").show();
            $("#report").show();
        }
        showLeftTime();
    });
}
function showLeftTime(){
    var now=new Date();
    var year=now.getYear() + 1900;
    var month=now.getMonth() + 1;
    var day=now.getDate();
    document.all.show.innerHTML=""+year+"年"+month+"月"+day+"日"
    var timeID=setTimeout(showLeftTime,60*1000);
}