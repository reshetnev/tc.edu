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
          Start Course
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
      <form class="form-horizontal" action="<@spring.url '/courses/${course.id}/start'/>" method="post">
        <fieldset>
			<div class="control-group">
			    <div class="control-label">Name</div>
			    <div id="titleField" class="controls text">${course.name}</div>
			</div>
		    <div class="form-actions">
				<button id="startButton" class="btn btn-success" type="submit">Start</button>
				<a class="btn" href="${request.contextPath}/courses">Cancel</a>
		    </div>
        </fieldset>
      </form>
    </div>
  </body>
</html>
