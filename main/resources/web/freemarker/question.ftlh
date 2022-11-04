
<h1>question</h1>
${question.query}<br/>

<form>
	<input id="answer" name="answer" type="input" autocomplete="off">
	<input type="submit" value="submit">
</form>

<hr>

<#if answer??>
	<h1>wrong answer</h1>
	${previous.query}<br/>
	${previous.answer}<br/>
	your answer: ${answer}
</#if>


<script>
document.getElementById('answer').focus();
</script>