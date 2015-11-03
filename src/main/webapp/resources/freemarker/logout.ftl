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
          Logout
        </h1>
      </header>
      <form class="form-horizontal" action="${request.contextPath}/j_spring_security_logout">
        <fieldset>
          <div class="control-group">
            <div class="controls text">
              You currently logged as '<span id="currentUserLogin">${email}</span>'.
            </div>
            <div class="controls text">
              Are you sure you want to logout?
            </div>
          </div>
          <div class="form-actions">
            <button id="logoutButton" class="btn btn-danger" type="submit">Logout</button>
            <a class="btn" href="${request.contextPath}/courses">Back</a>
          </div>
        </fieldset>
      </form>
    </div>
  </body>
</html>
