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
          Notify
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
      <form class="form-horizontal" action="<@spring.url '/courses/${course.id}/notify'/>" method="post">
        <fieldset>
		  <div class="control-group">
			    <div class="control-label">Name</div>
			    <div id="titleField" class="controls text">${course.name}</div>
		  </div>
		<#if course.attends?size!=0>
			<div class="control-group">
			  <div class="control-label">Attendee not evaluated</div>
			  <div id="attendeeField" class="controls text">
				<#list emailsAttendeesNotEvaluated as emailAttendee>
					<ul class="unstyled">
					  <li>${emailAttendee}</li>
					</ul>
				</#list>
			  </div>
			</div>
		</#if>
          <div class="form-actions">
            <button id="notifyButton" class="btn btn-success" type="submit">Notify</button>
            <a class="btn" href="${request.contextPath}/courses">Cancel</a>
          </div>
        </fieldset>
      </form>
    </div>
  </body>
</html>
