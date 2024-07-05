<#if answer??>
	<h1>wrong answer</h1>
	${previous.query?no_esc}<br/>
	<#if previous.img?has_content>
		<img src="/web/images/${previous.course}/${previous.img}"></img>
	</#if>
	<ul>
		<#list previous.answers as expect>
			<li>${expect?no_esc}</li>
		</#list>
	</ul>
	<#if answer?has_content>
		${answer} (your answer)<br/>
	</#if>
</#if>
