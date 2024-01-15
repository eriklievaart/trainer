<#include "header.ftlh"/>

<h1>whitebox</h1>
<div id="whitebox">
	<ul>
		<#list courses as course>
			<li><a href="?c=${course}">${course}</a></li>
		</#list>
	</ul>

	<hr/>

	<#if whitebox?has_content>
		<table>
			<thead><tr><th>wait</th><th>query</th></tr></thead>
			<tbody>
				<#list whitebox as vo>
					<tr><td>${vo.remainingTime}</td><td>${vo.query}</td></tr>
				</#list>
			</tobody>
		</table>
		<p>total: ${whitebox?size}</p>
	</#if>
</div>

<#include "footer.ftlh"/>
