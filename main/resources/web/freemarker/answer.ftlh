<#if answer??>
	<h1>wrong answer</h1>
	${previous.query}<br/>
	<ul>
		<#list previous.answers as expect>
			<li>${expect}</li>
		</#list>
	</ul>
	<#if answer?has_content>
		${answer} (your answer)<br/>
	</#if>
</#if>
