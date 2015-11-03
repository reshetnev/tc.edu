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
      <div class="form-horizontal">
        <div class="control-group">
          <div class="control-label">Name</div>
          <div id="titleField" class="controls text">${course.name}</div>
        </div>
        <div class="control-group">
          <div class="control-label">Lecturer</div>
          <div id="ownerField" class="controls text">${detail.lecturer}</div>
        </div>
		<#if course.subscribes?size!=0>
        <div class="control-group">
          <div class="control-label">Subscribers</div>
          <div id="subscribersField" class="controls text">
			<#list emailsSubscribers as emailSubscriber>
            <ul class="unstyled">
              <li>${emailSubscriber}</li>
            </ul>
			</#list>
          </div>
        </div>
		</#if>
		<#if course.attends?size!=0>
        <div class="control-group">
          <div class="control-label">Attendee</div>
          <div id="attendeeField" class="controls text">
			<#list emailsAttendees as emailAttendee>
            <ul class="unstyled">
              <li>${emailAttendee}</li>
            </ul>
			</#list>
          </div>
        </div>
		</#if>
        <div class="form-actions">
          <a class="btn" href="${request.contextPath}/courses">Back</a>
        </div>
      </div>
    </div>
  </body>
</html>
