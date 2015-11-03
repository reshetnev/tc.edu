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
          Approve Course
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
      <@spring.bind "approvalForm.*" />
      <#if spring.status.error>
      <div class="alert alert-error">
        <div>
          <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
            <@spring.bind "approvalForm.decision" />
            <#if spring.status.error>
                <li>Decision hasn't been selected.</li>
            </#if>
<!--			
            <@spring.bind "approvalForm.reason" />
            <#if spring.status.error>
                <li>Reason is required.</li>
            </#if>
-->			
        </ul>
      </div>
      </#if>
	  
      <div class="form-horizontal">
        <div class="control-group">
          <div class="control-label">Name</div>
          <div id="titleField" class="controls text">${course.name}</div>
        </div>
        <div class="control-group">
          <div class="control-label">Lecturer</div>
          <div id="ownerField" class="controls text">${detail.lecturer}</div>
        </div>
        <div class="control-group">
          <div class="control-label">Category</div>
          <div id="categoryField" class="controls text">${course.category.category}</div>
        </div>
        <div class="control-group">
          <div class="control-label">Description</div>
          <div id="descriptionField" class="controls text">${detail.description}</div>
        </div>
        <div class="control-group">
          <div class="control-label">Links</div>
          <div id="linksField" class="controls text">${detail.links}</div>
        </div>
	  
      <form class="form-horizontal" action="<@spring.url '/courses/${course.id}/approve'/>" method="post">
        <fieldset>
			<#list users as user>
				<#if user.email==email && user.role.role=="Knowledge Manager">
					<#if course.voteKM==1>
						<div class="control-group">
						  <div class="control-label">${user.role.role}</div>
						  <div id="dmField" class="controls text">${user.login}</div>
						</div>		
						  <div class="control-group">
							<label class="control-label">Decision</label>
							<div class="controls">
								<@spring.formSingleSelect "approvalForm.decision", mapDecisionsAll, ""/>			  
							</div>
						  </div>
						  <div class="control-group">
							<label class="control-label">Reason</label>
							<div class="controls">
							  <@spring.formTextarea "approvalForm.reason", 'id="reasonField" class="span5" rows="3"' /> 
							</div>
						  </div>
					<#else>
						<div class="control-group">
						  <div class="control-label">${user.role.role}</div>
						  <div id="dmField" class="controls text">${user.login}</div>
						</div>		
						  <div class="control-group">
							<label class="control-label">Decision</label>
							<div class="controls">
								<@spring.formSingleSelect "approvalForm.decision", mapDecisionsKM, "disabled='disabled'"/>			  
							</div>
						  </div>
						  <div class="control-group">
							<label class="control-label">Reason</label>
							<div class="controls">
							  <@spring.formTextarea "approvalForm.reason", 'id="reasonField" class="span5" rows="3" disabled="disabled"' />
							</div>
						  </div>					
					</#if>
				</#if>
				<#if user.email!=email && user.role.role=="Department Manager">
					<#if course.voteDM==1>
						<div class="control-group">
						  <div class="control-label">${user.role.role}</div>
						  <div id="dmField" class="controls text">${user.login}</div>
						</div>		
						  <div class="control-group">
							<label class="control-label">Decision</label>
							<div class="controls">
								<@spring.formSingleSelect "approvalForm.decision", mapDecisionsAll, "disabled='disabled'"/>			  
							</div>
						  </div>
						  <div class="control-group">
							<label class="control-label">Reason</label>
							<div class="controls">
							  <@spring.formTextarea "approvalForm.reason", 'id="reasonField" class="span5" rows="3" disabled="disabled"' /> 
							</div>
						  </div>
					<#else>
						<div class="control-group">
						  <div class="control-label">${user.role.role}</div>
						  <div id="dmField" class="controls text">${user.login}</div>
						</div>		
						  <div class="control-group">
							<label class="control-label">Decision</label>
							<div class="controls">
								<@spring.formSingleSelect "approvalForm.decision", mapDecisionsDM, "disabled='disabled'"/>			  
							</div>
						  </div>
						  <div class="control-group">
							<label class="control-label">Reason</label>
							<div class="controls">
							  <@spring.formTextarea "approvalForm.reason", 'id="reasonField" class="span5" rows="3" disabled="disabled"' /> 
							</div>
						  </div>					
					</#if>
				</#if>
				<#if user.email==email && user.role.role=="Department Manager">
					<#if course.voteDM==1>
						<div class="control-group">
						  <div class="control-label">${user.role.role}</div>
						  <div id="dmField" class="controls text">${user.login}</div>
						</div>		
						  <div class="control-group">
							<label class="control-label">Decision</label>
							<div class="controls">
								<@spring.formSingleSelect "approvalForm.decision", mapDecisionsAll, ""/>			  
							</div>
						  </div>
						  <div class="control-group">
							<label class="control-label">Reason</label>
							<div class="controls">
							  <@spring.formTextarea "approvalForm.reason", 'id="reasonField" class="span5" rows="3"' /> 
							</div>
						  </div>
					<#else>
						<div class="control-group">
						  <div class="control-label">${user.role.role}</div>
						  <div id="dmField" class="controls text">${user.login}</div>
						</div>		
						  <div class="control-group">
							<label class="control-label">Decision</label>
							<div class="controls">
								<@spring.formSingleSelect "approvalForm.decision", mapDecisionsDM, "disabled='disabled'"/>			  
							</div>
						  </div>
						  <div class="control-group">
							<label class="control-label">Reason</label>
							<div class="controls">
							  <@spring.formTextarea "approvalForm.reason", 'id="reasonField" class="span5" rows="3" disabled="disabled"' /> 
							</div>
						  </div>					
					</#if>
				</#if>
				<#if user.email!=email && user.role.role=="Knowledge Manager">
					<#if course.voteKM==1>
						<div class="control-group">
						  <div class="control-label">${user.role.role}</div>
						  <div id="dmField" class="controls text">${user.login}</div>
						</div>		
						  <div class="control-group">
							<label class="control-label">Decision</label>
							<div class="controls">
								<@spring.formSingleSelect "approvalForm.decision", mapDecisionsAll, "disabled='disabled'"/>			  
							</div>
						  </div>
						  <div class="control-group">
							<label class="control-label">Reason</label>
							<div class="controls">
							  <@spring.formTextarea "approvalForm.reason", 'id="reasonField" class="span5" rows="3" disabled="disabled"' /> 
							</div>
						  </div>
					<#else>
						<div class="control-group">
						  <div class="control-label">${user.role.role}</div>
						  <div id="dmField" class="controls text">${user.login}</div>
						</div>		
						  <div class="control-group">
							<label class="control-label">Decision</label>
							<div class="controls">
								<@spring.formSingleSelect "approvalForm.decision", mapDecisionsKM, "disabled='disabled'"/>			  
							</div>
						  </div>
						  <div class="control-group">
							<label class="control-label">Reason</label>
							<div class="controls">
							  <@spring.formTextarea "approvalForm.reason", 'id="reasonField" class="span5" rows="3" disabled="disabled"' /> 
							</div>
						  </div>					
					</#if>
				</#if>
			</#list>
          <div class="form-actions">
            <button id="createButton" class="btn btn-primary" type="submit">Save</button>
            <a class="btn" href="${request.contextPath}/courses">Back</a>
          </div>
        </fieldset>
      </form>
    </div>
  </body>
</html>
