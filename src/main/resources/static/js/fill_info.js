function info(data) {
	$('.tables input, .tables textarea, .tables select, .tables button').unbind();
	$('.textarea').unbind();
	$('.append').remove();
	var kpis = data.kpis;
	var worth = data.worth;
	var improves = data.improves;
	var plans = data.plans;
	//周期
	var cycle = (
		"<div class='time append'><p>周期:</p>" +
		"<input type='text' class='form-data' value=' "+ getSmpFormatDateByLong(data.startTime,false) +" '>" +
		"<span>——</span>" +
		"<input type='text' class='form-data' value=' "+ getSmpFormatDateByLong(data.endTime,false) +" '></div>"
	);
	//员工基本信息
	var info = (
		"<tr class='append'><td><p>名字:</p><input type='text' value=' "+ data.staffName +" '></td>" +
		"<td><p>工号:</p><input type='text' value=' "+ data.staffCode +" '></td>" +
		"<td><p>入职时间:</p><input type='text' value=' "+ getSmpFormatDateByLong(data.joinTime,false) +" ' class='form-data short-input'></td></tr>" +
		"<tr class='append'><td><p>职位:</p><input type='text' value=' "+ data.job +" '></td>" +
		"<td><p>部门:</p><input type='text' value=' "+ data.dept.name +" '></td>" +
		"<td><p>直接上级:</p><input type='text' class='short-input' value=' "+ data.leader +" '></td></tr>"
	);
	//业绩考核
	var check = '';
	for(var i=0; i< kpis.length;i++){
		check +=  "<tr class='item append'><td><pre><span>"+ kpis[i].target +"</span></pre><textarea class='textarea'>"+ kpis[i].target +"</textarea></td>" +
							"<td><pre><span>"+ kpis[i].goal +"</span></pre><textarea class='textarea'>"+ kpis[i].goal +"</textarea></td>" +
							"<td><pre><span>"+ kpis[i].weight +"</span></pre><textarea class='textarea'>"+ kpis[i].weight +"</textarea></td>" +
							"<td><pre><span>"+ kpis[i].standard +"</span></pre><textarea class='textarea'>"+ kpis[i].standard +"</textarea></td>" +
							"<td><pre><span>"+ kpis[i].cycle +"</span></pre><textarea class='textarea'>"+ kpis[i].cycle +"</textarea></td>" +
							"<td><pre><span>"+ kpis[i].execution +"</span></pre><textarea class='textarea'>"+ kpis[i].execution +"</textarea></td>" +
							"<td><pre><span>"+ kpis[i].score +"</span></pre><textarea class='textarea'>"+ kpis[i].score +"</textarea></td></tr>";
	}
	//价值观评价
	var worthBigName = ['持\n志','','虚\n心','','立\n根','','抱\n节',''];
	var values = '';
	for(var i=0; i<worth.length; i++){
		values += "<tr class='append'>";
		if( i%2 == 0){
			values += "<td rowspan='2'><p>" +worthBigName[i]+"</p></td>";
		}
		values += "<td><p class='worth-name'>" +worth[i].name+ "</p></td>" +
							"<td><p class='worth-weight'>" +worth[i].weight+ "</p></td>" +
							"<td><pre><span>" +worth[i].sample+ "</span></pre>"+
							"<textarea class='textarea worth-sample'>" +worth[i].sample+ "</textarea></td>" +
							"<td><input type='text' class='worth-score' value='"+ worth[i].score +"'></td></tr>";
	}
	//胜任能力分析
	var abilityAnalysis = (
		"<tr class='append'><td><p>弱项:</p></td>" +
		"<td><pre><span>"+ data.weaknesses +"</span></pre><textarea class='textarea'>"+ data.weaknesses +"</textarea></td></tr>" +
		"<tr class='append'><td><p>强项:</p></td>" +
		"<td><pre><span>"+ data.strengths +"</span></pre><textarea class='textarea'>"+ data.strengths +"</textarea></td></tr>" +
		"<tr class='append'><td><p>长期职业目标:</p></td>" +
		"<td><pre><span>"+ data.longGoals +"</span></pre><textarea class='textarea'>"+ data.longGoals +"</textarea></td></tr>" +
		"<tr class='append'><td><p>建议下阶段轮换岗位:</p></td>" +
		"<td><pre><span>"+ data.switchtWork +"</span></pre><textarea class='textarea'>"+ data.switchtWork +"</textarea></td></tr>" +
		"<tr class='append'><td><p>个人轮岗意愿:</p></td>" +
		"<td><pre><span>"+ data.switchWish +"</span></pre><textarea class='textarea'>"+ data.switchWish +"</textarea></td></tr>"
	);
	//下阶段绩效改进
	var nextImproves = '';
	if(improves.length == 0){// 加空行
        nextImproves ="<tr class='append'><td><pre><span></span></pre>" +
            "<textarea class='imp-target'></textarea></td>" +
            "<td><pre><span></span></pre>" +
            "<textarea class='imp-goal'></textarea></td>" +
            "<td><pre><span></span></pre>" +
            "<textarea class='imp-plan'></textarea></td><td>" +
            "<input type='text' class='imp-time form-date-top' value=''>" +
            "</td></tr>";
	}
	for(var i=0; i<improves.length; i++){
		nextImproves += "<tr class='append'><td><pre><span>"+ improves[i].target +"</span></pre>" +
										"<textarea class='imp-target'>"+ improves[i].target +"</textarea></td>" +
										"<td><pre><span>"+ improves[i].goal +"</span></pre>" +
										"<textarea class='imp-goal'>"+ improves[i].goal +"</textarea></td>" +
										"<td><pre><span>"+ improves[i].plan +"</span></pre>" +
										"<textarea class='imp-plan'>"+ improves[i].plan +"</textarea></td><td>" +
										"<input type='text' class='imp-time form-date-top' value='"+ getSmpFormatDateByLong(improves[i].time,false) +"'>" +
										"</td></tr>";
	}
	//签名
	var signature = (
		"<tr class='append'><td><p>直接上级:</p><input type='text' value=' "+ data.leader1Sign +" '></td>" +
		"<td><p>间接上级:</p><input type='text' value=' "+ data.leader2Sign +" '></td>" +
		"<td colspan='2'></td></tr>" +
		"<tr class='append'><td><p>日期:</p><input type='text' class='date' value=' "+ getSmpFormatDateByLong(data.leader1SignDate,false) +" '></td>" +
		"<td><p>日期:</p><input type='text' class='date' value=' "+ getSmpFormatDateByLong(data. leader2SignDate,false)+" '></td>" +
		"<td><p>员工本人:</p><input type='text' value=' "+ data.mySign +" '></td>" +
		"<td><p>日期:</p><input type='text' class='date' value=' "+ getSmpFormatDateByLong(data.mySignDate,false) +" '></td></tr>"
	);
	//次年度工作计划
	var nextPlans = '';
	if(plans.length == 0){
        nextPlans += "<tr class='append'><td><pre><span></span></pre>" +
            "<textarea class='plan-name textarea'></textarea></td>" +
            "<td><pre><span></span></pre>" +
            "<textarea class='plan-goal textarea'></textarea></td>" +
            "<td><pre><span></span></pre>" +
            "<textarea class='plan-goal textarea'></textarea></td>" +
            "<td><pre><span></span></pre>" +
            "<textarea class='plan-goal textarea'></textarea></td><td>" +
            "<input type='text' class='plan-finishTime form-date-top' value=''>" +
            "</td></tr>";
	}
	for(var i=0; i<plans.length; i++){
		nextPlans += "<tr class='append'><td><pre><span>"+ plans[i].name +"</span></pre>" +
								"<textarea class='plan-name textarea'>"+ plans[i].name +"</textarea></td>" +
								"<td><pre><span>"+ plans[i].goal +"</span></pre>" +
								"<textarea class='plan-goal textarea'>"+ plans[i].goal +"</textarea></td>" +
								"<td><pre><span>"+ plans[i].plan +"</span></pre>" +
								"<textarea class='plan-goal textarea'>"+ plans[i].plan +"</textarea></td>" +
								"<td><pre><span>"+ plans[i].budget +"</span></pre>" +
								"<textarea class='plan-goal textarea'>"+ plans[i].budget +"</textarea></td><td>" +
								"<input type='text' class='plan-finishTime form-date-top' value='"+ getSmpFormatDateByLong(plans[i].finishTime,false) +"'>" +
								"</td></tr>";
	}

	//周期填写
	$('.tables .title').append(cycle);

	//信息填写
	$('.tables #info tbody').append(info);

	//组织战略
	$('#companyGoal').parent().find('span').text(data.companyGoal);
	$('#companyGoal').val(data.companyGoal);

	//KPI填写
	$('#total').before(check);
	$('#kpiScore').text("总分:" + data.kpiScore);
	$('#kpiComment').parent().find('span').text(data.kpiComment);
	$('#kpiComment').val(data.kpiComment);

	//价值观填写
	$('#values-sum').parent().parent().before(values);
	$('#values-sum').text('总分:' + data.worthScore);
	$('#worthComment').parent().find('span').text(data.worthComment);
	$('#worthComment').val(data.worthComment);

	//总分
	$('#totalScore').find('p').text(data.totalScore);
	var pmlevel = data.pmlevel;

	//胜任能力分析填写
	$('.ability-analysis').append(abilityAnalysis);

	//下阶段绩效改进填写
	$('#improves tbody').append(nextImproves);
	$('#' + pmlevel).prop("checked",true);
	//签名填写
	$('.tables .signature').append(signature);

	//次年度绩效
	$('#nextyearGoals').parent().find('span').text(data.nextyearGoals);
	$('#nextyearGoals').val(data.nextyearGoals);
	$('.next-year-plan tbody').append(nextPlans);
	
	//查看表格禁止输入
	$('.tables input, .tables textarea, .tables select, .tables button').attr("disabled","disabled");
	//kpi输入高度自适应
	$('.textarea').on('input',function () {
		$(this).parent().find('pre').find('span').text($(this).val());
	});

}
