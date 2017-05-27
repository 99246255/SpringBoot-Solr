function lookPlan(id){
    layer.open({
        type: 2,
        title: '查看绩效评估',
        shadeClose: true,
        shade: false,
        area: ['100%', '100%'],
        content: 'lookplan/' + id,
        end: function(){
            refreshTable();
        }
    });
}
function lookOnly(id){
    layer.open({
        type: 2,
        title: '查看绩效详情',
        shadeClose: true,
        shade: false,
        area: ['100%', '100%'],
        content: 'look/' + id,
        end: function(){
            refreshTable();
        }
    });
}
function refreshTable(){
    $('#table_list').bootstrapTable("refresh");
}
function lookEvaluate(id){
    layer.open({
        type: 2,
        title: '查看绩效评估',
        shadeClose: true,
        shade: false,
        area: ['100%', '100%'],
        content: 'lookEvaluate/' + id,
        end: function(){
            refreshTable();
        }
    });
}
function flow(id){
    layer.open({
        type: 2,
        title: '查看流程',
        shade: 0.6, //遮罩透明度
        maxmin: true, //允许全屏最小化
        anim: 1, //0-6的动画形式，-1不开启
        area: ['900px', '550px'],
        content: 'generateDiagram/'  + id,
    });
}
function auditreason(id){
    layer.open({
        type: 2,
        title: '查看流转意见',
        shade: 0.6, //遮罩透明度
        maxmin: true, //允许全屏最小化
        anim: 1, //0-6的动画形式，-1不开启
        area: ['600px', '500px'],
        content: 'showaudit/'  + id,
    });
}