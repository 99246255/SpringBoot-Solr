$(function () {
	var kpisum = 0;  //kpi总分
	var kpiweightSum = 0; //kpi权重总分
    var worthSum = 0; //kpi权重总分
	var maxAreaLength = 1000;
    var maxInputLength = 255;
    var formdatetop ={
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        format: 'yyyy-mm-dd',
        pickerPosition: 'top-right'
    }
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
	$('.form-date-top').datetimepicker(formdatetop);
    computeKpiScore();
    computeWorthScore();
	//添加KPI
	$('#add').click(function () {
		if($('.check').find('.item').length >= 9){
			return false;
		}
		$('.num').unbind();
		$('.weight').unbind();
		$('.textarea').unbind();
		var items = (
			"<tr class='item'><td><pre><span></span></pre><textarea class='textarea target'></textarea></td>" +
			"<td><pre><span></span></pre><textarea class='textarea goal'></textarea></td>" +
			"<td><input type='text' class='weight'><p class='unit'>%</p></td>" +
			"<td><pre><span></span></pre><textarea class='textarea standard'></textarea></td>" +
			"<td><label class='cycle'>年度</label></td>" +
			"<td><pre><span></span></pre><textarea class='textarea execution' readonly='readonly'></textarea></td>" +
			"<td><input type='text' class='num' readonly='readonly'></td></tr>"
		);

		$('#add').parent().parent().before(items);
		$('.num').on("input",KpiNumInputLimit);
		$('.weight').on('input',WeightInputLimit);
		$('.textarea').on('input',function () {
			$(this).parent().find('pre').find('span').text($(this).val());
		})
		if($('.check').find('.item').length == 9){
			$('#add').parent().parent().hide();
		}
        TextLimit();
	});
	//添加改进
    $('#add-improve').click(function () {
        $('.textarea').unbind();
        var items = (
            "<tr><td><pre><span></span></pre><textarea class='textarea imp-target'></textarea></td>" +
            "<td><pre><span></span></pre><textarea class='textarea imp-goal'></textarea></td>" +
       		"<td><pre><span></span></pre><textarea class='textarea imp-plan'></textarea></td>" +
        	"<td><input type='text' class='imp-time form-date-top' readonly='readonly'></input></td></tr>"
        );
        $('#add-improve').parent().parent().before(items);
        $('.textarea').on('input',function () {
            $(this).parent().find('pre').find('span').text($(this).val());
        });
        $('.form-date-top').datetimepicker(formdatetop);
        TextLimit();
    });
	$('.num').on("input",KpiNumInputLimit);
	$('.weight').on('input',WeightInputLimit);

	//kpi绩效评分输入限制
	function KpiNumInputLimit() {
        var kpinum = $(this).val();
        if(kpinum != '' && kpinum != null ){
            if(parseInt(kpinum) >=1 && parseInt(kpinum) <=5){
                $(this).val(parseInt(kpinum));
            }else{
                $(this).val('');
            }
        }
        computeKpiScore();
	}
	// 计算pki分数
	function computeKpiScore() {
        kpisum = 0;
		//kpi总分/权重总计 计算
        kpiweightSum = 0;
        $('.weight').each(function(index, item) {
            var num = $(this).parent().parent().find('.num').val()?$(this).parent().parent().find('.num').val():0;
            kpisum += parseInt($(item).val()) * parseInt(num) || 0;
            kpiweightSum += parseInt($(item).val()) || 0;
            kpiweightSum < 100 ? $('#weight-sum').addClass('red') : $('#weight-sum').removeClass('red');
        });
        $('#weight-sum').text(kpiweightSum + '%');
        $('#sum').text('总分: ' + kpisum/100);
        computTotleScore();
    }
    $('.worth-score').on('input',WorthNumInputLimit);
    //worth绩效评分输入限制
    function WorthNumInputLimit() {
        var worthnum = $(this).val();
        if(worthnum != '' && worthnum != null ){
            if(parseInt(worthnum) >=1 && parseInt(worthnum) <=5){
                $(this).val(parseInt(worthnum));
            }else{
                $(this).val('');
            }
        }
        computeWorthScore();
    }
	// 计算价值观总分
    function computeWorthScore() {
		worthSum = 0;
        $('.worth-score').each(function(index, item) {
            var worthnum = $(this).parent().parent().find('.worth-score').val()?$(this).parent().parent().find('.worth-score').val():0;
            worthSum += (12.5 * parseInt(worthnum) )|| 0;
        });
        $('#values-sum').text('总分: ' + worthSum/100);
        computTotleScore();
    }
    // 计算总分和设置评级
    function computTotleScore() {
        var totlescore = (kpisum * 5 + worthSum * 5)/1000;
        $('#totle-score').text(totlescore);
        if(totlescore < 1){
            $('#E').parent().siblings().find('input[type="checkbox"]').prop('checked',false);
        }else if(totlescore<2){
            $('#E').parent().siblings().find('input[type="checkbox"]').prop('checked',false);
            $('#E').prop("checked",true);
        }else if(totlescore<2.75){
            $('#D').parent().siblings().find('input[type="checkbox"]').prop('checked',false);
            $('#D').prop("checked",true);
        }else if(totlescore<3.75){
            $('#C').parent().siblings().find('input[type="checkbox"]').prop('checked',false);
            $('#C').prop("checked",true);
        }else if(totlescore<4.5){
            $('#B').parent().siblings().find('input[type="checkbox"]').prop('checked',false);
            $('#B').prop("checked",true);
        }else{
            $('#A').parent().siblings().find('input[type="checkbox"]').prop('checked',false);
            $('#A').prop("checked",true);
        }
    }
	//kpi权重输入限制
	function WeightInputLimit() {
		var weight = $(this).val();
		if(!isNaN(weight)){
			if(parseInt(weight) >=1 && parseInt(weight) < 100){
				//判断是否超过100%;
				kpiweightSum = 0;
				$('.weight').each(function(index, item) {
					kpiweightSum += parseInt($(item).val()) || 0;
				});
				kpiweightSum > 100 ? $(this).val(weight.substring(0, weight.length-1)) : $(this).val(parseInt(weight));
			}else{
				$(this).val(weight.substring(0, weight.length-1));
			}
		}else{
			if(weight.length > 1){
				$(this).val(weight.substring(0, weight.length-1));
			}else{
				$(this).val('');
			}
		}
        computeKpiScore();
	}
    //提交
	$('#submit').click(function () {
		if(kpiweightSum < 100){
			alert('kpi权重总计不满100%');
			return false;
		}
        var kpis = [];
        $('.weight').each(function(index, item) {
            var kpinum = $(this).parent().parent().find('.num').val()?$(this).parent().parent().find('.num').val():0;
            var weight = parseInt($(item).val())?parseInt($(item).val()):0;
            if(kpinum == 0 || weight == 0){return true;}
            var kpi = {"target":$(this).parent().parent().find('.target').val(),"goal":$(this).parent().parent().find('.goal').val(),"weight":$(this).parent().parent().find('.weight').val(),"standard":$(this).parent().parent().find('.standard').val(),"cycle":$(this).parent().parent().find('.cycle').val(),"execution":$(this).parent().parent().find('.execution').val(),"score":kpinum};
            kpis.push(kpi);
        });
        var worth= [];
        $('.worth-name').each(function(index, item) {
            var worthnum = $(this).parent().parent().find('.worth-score').val()?$(this).parent().parent().find('.worth-score').val():0;
            if(parseInt(worthnum) == 0){return true;}
            var worth1 = {"name":$(this).parent().parent().find('.worth-name').text(),"weight":12.5,"sample":$(this).parent().parent().find('.worth-sample').val(),"score":worthnum};
            worth.push(worth1);
        });
        var plans = [];
        $('.plan-name').each(function(index, item) {
            var plan = {"name":$(this).parent().parent().find('.plan-name').val(),"goal":$(this).parent().parent().find('.plan-goal').val(),"plan":$(this).parent().parent().find('.plan-plan').val(),"budget":$(this).parent().parent().find('.plan-budget').val(), "finishTime":$(this).parent().parent().find('.plan-finishTime').val()};
            plans.push(plan);
        });
        var improves = [];
        $('.imp-target').each(function(index, item) {
            var improve = {"target":$(this).parent().parent().find('.imp-target').val(),"goal":$(this).parent().parent().find('.imp-goal').val(),"plan":$(this).parent().parent().find('.imp-plan').val(), "time":$(this).parent().parent().find('.imp-time').val()};
            improves.push(improve);
        });
		// var kpis = JSON.stringify([{"id":1,"target":"安抚","goal":"目标","weight":50,"standard":"标准","cycle":"计划","execution":"d","score":3,"performanceId":1},{"id":2,"target":"adfa","goal":"adfa","weight":50,"standard":"adsf","cycle":"dad","execution":"dddd","score":4,"performanceId":1}]);
        var dataOne = {"staffName": $("#staffName").val(),"staffCode":$("#staffCode").val(),"joinTime":$("#joinTime").val(),"job":$("#staffCode").val(),"deptId":$('#deptName option:selected').val(),"leader":$("#leader").val(),"startTime":$("#startTime").val(),"endTime":$("#endTime").val(),"companyGoal":$("#companyGoal").val(),"kpiWeight":50,"kpiComment":$("#kpiComment").val(),"worthWeight":50,"worthScore":$("#worthScore").val(),"worthComment":$("#worthComment").val(),"totalScore":$("#totalScore").val(),"strengths":$("#strengths").val(),"weaknesses":$("#weaknesses").val(),"longGoals":$("#longGoals").val(),"switchtWork":$("#switchtWork").val(),"switchWish":$("#switchWish").val(),"nextyearGoals":$("#nextyearGoals").val(),"leader1Sign":$("#leader1Sign").val(),"leader2Sign":$("#leader2Sign").val(),"mySign":$("#mySign").val(),"leader1SignDate":$("#leader1SignDate").val(),"leader2SignDate":$("#leader2SignDate").val(),"mySignDate":$("#mySignDate").val(),"kpis":JSON.stringify(kpis),"worth":JSON.stringify(worth),"improves":JSON.stringify(improves),"plans":JSON.stringify(plans)};
			$.post("/submit",dataOne,function(r){
				if(!r.success){
                    alert(r.message);
                    return false;
				}else{
                    window.location.href = '/myperformance';
				}

        });
	});
    TextLimit();
    // 文本长度限制
	function TextLimit() {
        //kpi输入高度自适应以及长度限制
        $("textarea").on('input',function () {
            $(this).parent().find('pre').find('span').text($(this).val());
            var str = $(this).val();
            if(str.length >= maxAreaLength){
                $(this).val(str.substring(0, maxAreaLength));
            }
        });
        $("input").attr({"maxLength":maxInputLength});
    }
});