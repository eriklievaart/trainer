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

<#include "answer.ftlh"/>

<script>
document.getElementById('answer').focus();
</script>

<#include "footer.ftlh"/>
