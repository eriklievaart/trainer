<#include "header.ftlh"/>

<li>
	<#list index as item>
		<li><a href="/web/${item}">${item}</a></li>
	</#list>
</li>

<#include "footer.ftlh"/>
