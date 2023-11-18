<#include "header.ftlh"/>

<#if ! answer??>
<h1>Succes!</h1>
Gefeliciflapstaart
</#if>

<#include "answer.ftlh"/>

<br/><a id="refresh" href="/">refresh!</a>
<script>document.getElementById('refresh').href = window.location.href.replace(/[?].*/, '');</script>
<br/><br/><a href="/">home</a>

<#include "footer.ftlh"/>
