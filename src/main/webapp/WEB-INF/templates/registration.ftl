<#ftl encoding="utf-8">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>registration</title>
</head>
<body>
    <#if errorMessage?has_content>
        <h1>${errorMessage}</h1>
    </#if>
    <form method="post" action="/registration">
        <input type="text" name="username">
        <input type="password" name="password">
        <input type="submit">Подтвердить</input>
    </form>
</body>
</html>