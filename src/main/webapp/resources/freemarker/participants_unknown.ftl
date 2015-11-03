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
          Course Participants
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
      <div class="alert alert-error">
        <div>
          <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
          <li>Unknown course</li>
        </ul>
      </div>
      <div class="form-horizontal">
        <div class="form-actions">
          <a class="btn" href="${request.contextPath}/courses">Back</a>
        </div>
      </div>
    </div>
  </body>
</html>
