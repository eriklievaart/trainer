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

	if(diff < 0) {
		document.getElementById("refresh").click();
	} else {
		document.getElementById("js").innerHTML = "next question in " + seconds + "s";
	}
}

setInterval(timestamp, 1000);
</script>




<#include "footer.ftlh"/>
