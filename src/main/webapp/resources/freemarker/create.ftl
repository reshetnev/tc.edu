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
          Create Course
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
      <@spring.bind "createForm.*" />
      <#if spring.status.error>
      <div class="alert alert-error">
        <div>
          <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
            <@spring.bind "createForm.name" />
            <#if spring.status.error>
                <li>Name field is required.</li>
            </#if>
            <@spring.bind "createForm.category" />
            <#if spring.status.error>
                <li>Category field is required.</li>
            </#if>
            <@spring.bind "createForm.description" />
            <#if spring.status.error>
                <li>Description field is required.</li>
            </#if>
            <@spring.bind "createForm.links" />
            <#if spring.status.error>
                <li>Links field is required.</li>
            </#if>
        </ul>
      </div>
      </#if>
	  
      <form class="form-horizontal" action="<@spring.url '/courses/create'/>" method="post">
        <fieldset>
          <div class="control-group">
            <label class="control-label">Name</label>
            <div class="controls">
			    <@spring.formTextarea "createForm.name", 'id="titleField" class="span5"' />
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Category</label>
            <div class="controls">
				<@spring.formSingleSelect "createForm.category", mapCategories, ""/>			  
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Description</label>
            <div class="controls">
			  <@spring.formTextarea "createForm.description", 'id="descriptionField" class="span5" rows="3"' /> 
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Links</label>
            <div class="controls">
			 <@spring.formTextarea "createForm.links", 'id="linksField" class="span5" rows="3"' />
			</textarea>
            </div>
          </div>
          <div class="form-actions">
            <button id="createButton" class="btn btn-primary" type="submit">Create</button>
            <a class="btn" href="${request.contextPath}/courses">Cancel</a>
          </div>
        </fieldset>
      </form>
    </div>
  </body>
</html>
