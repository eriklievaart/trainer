<#include "header.ftlh"/>

<form>
	<table>
		<#list questioneers as q>
			<tr><td><input type="checkbox" name="q" value="${q}" ${selected?seq_contains(q)?string("checked", "")}></td><td>${q}</td></tr>
		</#list>
	</table>

	<input name="delay" type="text" value="60" size="4" autocomplete="off"> seconds delay<br/>
	<input type="submit" value="start">
</form>

<hr>

<a href="${lightning.getRemotePath('web', 'whitebox')}">whitebox</a>

<#include "footer.ftlh"/>
