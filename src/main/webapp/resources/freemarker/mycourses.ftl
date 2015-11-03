<#import "spring.ftl" as spring />

<html lang="en">
  <head>
    <meta charset="utf-8"/>
    
	<link rel="stylesheet" type="text/css" href="<@spring.url '/resources/css/style.css'/>"/>
	
  </head>
  <body>
    <div class="container">
      <header>
        <h1>
          My Courses
          <div class="logout">
            <span id="currentUserLogin">
			<#if email??>
			   ${email}
			</#if>
            </span>
            <a href="${request.contextPath}/logout">
              <i class="icon-off"></i>
            </a>
          </div>
        </h1>
      </header>
      <div class="courses-top-control">
        <a class="btn" href="${request.contextPath}/courses">Courses</a>
      <@spring.bind "searchForm.category" />		
        <div class="search-box">
          <form class="form-search" action="<@spring.url '/mycourses'/>" method="post">
            <div class="controls">
				<@spring.formSingleSelect "searchForm.category", mapCategories, ""/>
				<button class="btn" type="submit">Apply</button>				
            </div>
          </form>			
        </div>
      </div>
      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th class="span1">Id</th>
            <th>Course</th>
            <th class="span3">Category</th>
            <th class="span1">S/A</th>
            <th class="span1">Grade</th>
            <th class="span2">Action</th>
          </tr>
        </thead>
        <tbody>
			<#list myCourses as course>
				<#assign flagS=0>
				<#list course.subscribes as subscribe>
					<#if subscribe.pk.user.email==email>
						<#assign flagS=1>
					</#if>
				</#list>
				<#assign flagA=0>
				<#assign flagG=0>
				<#assign sumGrade=0>
				<#assign qG=0>
				<#list course.attends as attend>
					<#if attend.pk.user.email==email>
						<#assign flagA=1>
						<#if attend.grade!=0>
							<#assign flagG=1>
						</#if>
					</#if>
					<#if attend.grade!=0>
						<#assign sumGrade=sumGrade+attend.grade>
						<#assign qG=qG+1>
					</#if>					
				</#list>
				<#if qG==0>
					<#assign mark=0>
				<#else>
					<#assign mark=sumGrade/(qG)>
				</#if>
				<tr>
					<td>${course.id}</td>
					<td>
						<a href="${request.contextPath}/courses/${course.id}">${course.name}
						<#if course.state.badge=="Proposal">
							<span class="label label-warning">${course.state.badge}</span>
						<#elseif course.state.badge=="Rejected">
							<span class="label label-important">${course.state.badge}</span>
						<#elseif course.state.badge=="New">
							<span class="label label-success">${course.state.badge}</span>
						<#elseif course.state.badge=="Open">
							<span class="label label-info">${course.state.badge}</span>
						<#elseif course.state.badge=="Ready">
							<span class="label label-inverse">${course.state.badge}</span>
						<#else>
							<span class="label">${course.state.badge}</span>
						</#if>
					</td>
					<td>
					  ${course.category.category}
					</td>
					<td>
					<#if course.attends?size!=0>				
						<a href="${request.contextPath}/courses/${course.id}/participants">${course.subscribes?size}/${course.attends?size}</a>
					<#elseif course.subscribes?size!=0>
						<a href="${request.contextPath}/courses/${course.id}/participants">${course.subscribes?size}</a>					
					</#if>
					</td>
					<td>
					<#if mark!=0>
						${mark}
					</#if>
					</td>
					<td>
					<#if course.detail.lecturer==email>
						<#if course.state.badge=="Draft" || course.state.badge=="Rejected">		
						  <div class="btn-group">
							<a class="btn btn-mini" href="${request.contextPath}/courses/${course.id}/update">Update</a>
							<a class="btn dropdown-toggle btn-mini" data-toggle="dropdown" href="#"><span class="caret"></span></a>
							<ul class="dropdown-menu">
							  <li>
								<a href="${request.contextPath}/courses/${course.id}/update">Send to Review</a>
								<a href="${request.contextPath}/courses/${course.id}/delete">Delete</a>
							  </li>
							</ul>
						  </div>
						</#if>
						<#if course.state.badge=="Ready">
							<a class="btn btn-mini" href="${request.contextPath}/courses/${course.id}/start">Start</a>
						</#if>
						<#if course.state.badge=="In Progress">
							<a class="btn btn-mini" href="${request.contextPath}/courses/${course.id}/finish">Finish</a>
						</#if>
						<#if course.state.badge=="Finished">
							<a class="btn btn-mini" href="${request.contextPath}/courses/${course.id}/notify">Notify</a>
						</#if>						
					</#if>
					<#if flagU==1>				
						<#if flagS==0>
							<#if flagA==0>
								<#if course.state.badge=="New" || course.state.badge=="Open" || course.state.badge=="Ready" >
									<a class="btn btn-mini" href="${request.contextPath}/courses/${course.id}/subscribe">Subscribe</a>
								</#if>	
							</#if>
						</#if>
					</#if>
					<#if flagS==1>
						<#if flagA==0>
							<#if course.state.badge=="Open" || course.state.badge=="Ready" >
								<a class="btn btn-mini" href="${request.contextPath}/courses/${course.id}/attend">Attend</a>
							</#if>	
						</#if>
					</#if>	
					<#if flagA==1>
						<#if flagG==0>
							<#if course.state.badge=="Finished">							
								<a class="btn btn-mini" href="${request.contextPath}/courses/${course.id}/evaluate">Evaluate</a>
							</#if>
						</#if>
					</#if>				
					</td>
				</tr>
			</#list>
        </tbody>
      </table>
    </div>
    <script src="<@spring.url '/resources/script/jquery-1.8.1.min.js'/>"></script>
    <script src="<@spring.url '/resources/script/bootstrap-2.2.2.min.js'/>"></script>
  </body>
</html>
