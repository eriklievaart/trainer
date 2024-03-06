<#include "header.ftlh"/>

<h1>whitebox</h1>
<div id="whitebox">
	<ul>
		<#list courses as course>
			<li><a href="?c=${course}">${course}</a></li>
		</#list>
	</ul>

	<hr/>

	<#if redundant?? && redundant gt 0>
		<form action="whitebox/redundant" method="POST">
			<input type="hidden" name="course" value="${parameter.get('c')}" />
			<input type="submit" value="remove redundant (${redundant})" />
		</form>
	</#if>

	<#if whitebox?has_content>
		<table>
			<thead><tr><th>wait</th><th>query</th><th>hash</th></tr></thead>
			<tbody>
				<#list whitebox as vo>
					<tr><td>${vo.remainingTime}</td><td>${vo.query}</td><td>${vo.hash}</td></tr>
				</#list>
			</tobody>
		</table>
		<p>total: ${whitebox?size}</p>
	</#if>
</div>

<#include "footer.ftlh"/>
