<#include "header.ftlh"/>

<h1>question ${remaining}</h1>

<#if question.img?has_content>
	<img src="/web/images/${question.course}/${question.img}"></img>
</#if>

${question.query}<br/>


<form>
	<input id="answer" name="answer" type="input" autocomplete="off" size="50">
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
