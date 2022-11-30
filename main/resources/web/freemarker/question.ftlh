<#include "header.ftlh"/>

<h1>question ${remaining}</h1>
${question.query}<br/>

<form>
	<input id="answer" name="answer" type="input" autocomplete="off">
	<input id="hash" name="hash" type="hidden" value="${question.hash}">
	<input type="submit" value="submit">
</form>

<hr>

<#if mismatch>
	<h1>hash mismatch</h1>
</#if>

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

<script>
document.getElementById('answer').focus();
</script>

<#include "footer.ftlh"/>
