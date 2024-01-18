<#include "header.ftlh"/>

<form>
	<table>
		<#list questioneers as q>
			<tr><td><input type="checkbox" name="q" value="${q}" ${selected?seq_contains(q)?string("checked", "")}></td><td>${q}</td></tr>
		</#list>
	<table>
	<input type="submit" value="start">
</form>

<hr>

<a href="${lightning.getRemotePath('web', 'whitebox')}">whitebox</a>

<#include "footer.ftlh"/>
