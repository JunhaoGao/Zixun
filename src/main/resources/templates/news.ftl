<html>
<head>
    <title>freemarker Test</title>
</head>
<body>
<h1>Hello,${value1}</h1>

<#list map?keys as key>
    ${key} : ${map[key]}
</#list>

User:${user.name}<br>
User:${user.getAge()}<br>

<#assign title = "asdas">
<#--import:-->
<#--<#import "header.ftl" as h> <br>-->
<#--include:-->
<#include "header.ftl"><br>

<#macro color k m>
    Color By ${k} ${m[k]} <br>
</#macro>

<#list map?keys as key>
    <@color key map/>
</#list>


<#assign hw = "hello world">
<#assign hw1 = "${hw}  !!!">
<#assign hw2 = '${hw1} `12`1'>

hw: ${hw}<br>
hw1: ${hw1}<br>
hw2: ${hw2}<br>


</body>
</html>