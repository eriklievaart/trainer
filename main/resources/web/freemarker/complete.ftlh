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
var next = ${timestamp};
document.getElementById('refresh').href = window.location.href.replace(/[?].*/, '');

function timestamp() {

	var now = Date.now();
	var diff = next - now;
	var seconds = Math.ceil(diff / 1000);
	var minutes = Math.ceil(diff / 1000 / 60);
	var hours = Math.ceil(diff / 1000 / 3600);

	var label = seconds + 's';
	if(seconds > 99) {
		label = minutes + 'm';
	}
	if(minutes > 99) {
		label = hours + 'h';
	}

	if(diff < 0) {
		document.getElementById("js").innerHTML = "new question available";
		document.getElementById("refresh").focus();
	} else {
		document.getElementById("js").innerHTML = "next question in " + label;
	}
}

setInterval(timestamp, 1000);
</script>




<#include "footer.ftlh"/>
