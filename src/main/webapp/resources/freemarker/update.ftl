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
          Update Course
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
      <@spring.bind "updateForm.*" />
      <#if spring.status.error>
      <div class="alert alert-error">
        <div>
          <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
            <@spring.bind "updateForm.name" />
            <#if spring.status.error>
                <li>Name field is required.</li>
            </#if>
            <@spring.bind "updateForm.category" />
            <#if spring.status.error>
                <li>Category field is required.</li>
            </#if>
            <@spring.bind "updateForm.description" />
            <#if spring.status.error>
                <li>Description field is required.</li>
            </#if>
            <@spring.bind "updateForm.links" />
            <#if spring.status.error>
                <li>Links field is required.</li>
            </#if>
			<@spring.bind "updateForm.qSubs" />
			<#list spring.status.errorMessages as error> 
				<li>Minimal Subscribers ${error}</li> 
            </#list>
			<@spring.bind "updateForm.qAtts" />
			<#list spring.status.errorMessages as error> 
				<li>Minimal Attendees ${error}</li> 
            </#list>
        </ul>
      </div>
      </#if>
	  
      <form class="form-horizontal" action="<@spring.url '/courses/${course.id}/update'/>" method="post">
        <fieldset>
          <div class="control-group">
            <label class="control-label">Name</label>
            <div class="controls">
			    <@spring.formTextarea "updateForm.name", 'id="titleField" class="span5"' />
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Category</label>
            <div class="controls">
				<@spring.formSingleSelect "updateForm.category", mapCategories, ""/>			  
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Description</label>
            <div class="controls">
			  <@spring.formTextarea "updateForm.description", 'id="descriptionField" class="span5" rows="3"' /> 
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Links</label>
            <div class="controls">
			 <@spring.formTextarea "updateForm.links", 'id="linksField" class="span5" rows="3"' />
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Minimal Subscribers</label>
            <div class="controls">
			 <@spring.formTextarea "updateForm.qSubs", 'id="qSubsField" class="span2"' />
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Minimal Attendees</label>
            <div class="controls">
			 <@spring.formTextarea "updateForm.qAtts", 'id="qAttsField" class="span2"' />
            </div>
          </div>		  
          <div class="form-actions">
            <button id="createButton" class="btn btn-primary" type="submit" name="badge" value="">Update</button>
			<#if course.state.badge=="Draft">
				<button id="reviewButton" class="btn btn-warning" type="submit" name="badge" value="Proposal">Review</button>
			</#if>
            <a class="btn" href="${request.contextPath}/courses">Cancel</a>
          </div>
        </fieldset>
      </form>
    </div>
  </body>
</html>
