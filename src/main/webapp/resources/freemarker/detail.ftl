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
          Course Details
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
	<#if course.subscribes?size!=0>		
        <div class="control-group">
          <div class="control-label">Subscribers</div>
          <div id="subscribersField" class="controls text">
            <a href="${request.contextPath}/courses/${detail.id}/participants">${course.subscribes?size}</a>
          </div>
        </div>
	</#if>
	<#if course.attends?size!=0>	
        <div class="control-group">
          <div class="control-label">Attendee</div>
          <div id="attendeeField" class="controls text">
            <a href="${request.contextPath}/courses/${detail.id}/participants">${course.attends?size}</a>
          </div>
        </div>
	</#if>
	<#if mark!=0>
        <div class="control-group">
          <div class="control-label">Grade</div>
          <div id="gradeField" class="controls text">${mark}</div>
        </div>
	</#if>
        <div class="form-actions">
          <a class="btn" href="${request.contextPath}/courses">Back</a>
        </div>
      </div>
    </div>
  </body>
</html>
