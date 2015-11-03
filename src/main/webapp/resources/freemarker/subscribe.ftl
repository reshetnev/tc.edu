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
          Subscribe
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
      <form class="form-horizontal" action="<@spring.url '/courses/${detail.id}/subscribe'/>" method="post">
        <fieldset>
			<div class="control-group">
			    <div class="control-label">Name</div>
			    <div id="titleField" class="controls text">${course.name}</div>
			</div>
			<div class="control-group">
			    <div class="control-label">Lecturer</div>
			    <div id="ownerField" class="controls text">${detail.lecturer}</div>
			</div>
		    <div class="form-actions">
				<button id="subscribeButton" class="btn btn-success" type="submit">Subscribe</button>
				<a class="btn" href="${request.contextPath}/courses">Cancel</a>
		    </div>
        </fieldset>
      </form>
    </div>
  </body>
</html>
