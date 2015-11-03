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
          Evaluation
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
      <@spring.bind "evaluateForm.*" />

      <div class="alert alert-error">
        <div>
          <strong>Okay, Houston, we've had a problem here</strong>
        </div>
        <ul>
 
          <li>Grade must be integer.</li> 

        </ul>
      </div>

	  
      <form class="form-horizontal" action="<@spring.url '/courses/${course.id}/evaluate'/>" method="post">
        <fieldset>
		  <div class="control-group">
			    <div class="control-label">Name</div>
			    <div id="titleField" class="controls text">${course.name}</div>
		  </div>
		  <div class="control-group">
			    <div class="control-label">Lecturer</div>
			    <div id="ownerField" class="controls text">${detail.lecturer}</div>
		  </div>
          <div class="control-group">
            <label class="control-label">Grade</label>
            <div class="controls">
			 <@spring.formTextarea "evaluateForm.grade", 'id="gradeField" class="span2"' />
			</textarea>
            </div>
          </div>
          <div class="form-actions">
            <button id="evaluateButton" class="btn btn-success" type="submit">Evaluate</button>
            <a class="btn" href="${request.contextPath}/courses">Cancel</a>
          </div>
        </fieldset>
      </form>
    </div>
  </body>
</html>
