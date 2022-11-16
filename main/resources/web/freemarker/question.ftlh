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
	<ul>
		<#list previous.answers as expect>
			<li>${expect}</li>
		</#list>
	</ul>
	<#if answer?has_content>
		${answer} (your answer)
	</#if>
</#if>


<script>
document.getElementById('answer').focus();
</script>

<#include "footer.ftlh"/>
