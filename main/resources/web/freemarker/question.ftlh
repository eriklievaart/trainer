<#include "header.ftlh"/>

<h1>question (${remaining})</h1>
${question.query}<br/>

<form>
	<input id="answer" name="answer" type="input" autocomplete="off">
	<input type="submit" value="submit">
</form>

<hr>

<#if answer??>
	<h1>wrong answer</h1>
	${previous.query}<br/>
	${previous.answer} (expected)<br/>
	${answer} (your answer)
</#if>


<script>
document.getElementById('answer').focus();
</script>

<#include "footer.ftlh"/>
