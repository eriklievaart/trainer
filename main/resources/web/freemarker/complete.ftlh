<#include "header.ftlh"/>

<#if ! answer??>
<h1>Succes!</h1>
</#if>

<#include "answer.ftlh"/>

<a id="refresh" href="/">refresh!</a>

<br/><br/>
<div id="js">gefeliciflapstaart!</div>
<a href="/">home</a>
<br/>





<script>
document.getElementById('refresh').href = window.location.href.replace(/[?].*/, '');

function timestamp() {
	fetch('/web/timestamp').then(response => {
		if(response.ok) {
			return (response.text());
		}
	}).then(text => {
		if(text === "refresh") {
			document.getElementById("refresh").click();
		} else {
			document.getElementById('js').innerHTML = text;
		}
	});
}

setInterval(timestamp, 1000);
</script>




<#include "footer.ftlh"/>
