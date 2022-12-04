<#include "header.ftlh"/>

<ul>
	<#list index as item>
		<li><a href="/web/${item}">${item}</a></li>
	</#list>
</ul>

<#include "footer.ftlh"/>
