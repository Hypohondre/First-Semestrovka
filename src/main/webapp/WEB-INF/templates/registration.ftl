<#ftl encoding="utf-8">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>registration</title>
</head>
<body>

    <#if errorMessage?has_content>
        <ul>
            <#list errorMessage as item >
                <li>${item}</li>
            </#list>
        </ul>
    </#if>

    <form method="post" action="/registration">
        <label>логин</label>
        <input type="text" name="username">
        <label>пароль</label>
        <input type="password" name="password">
        <label>мыло</label>
        <input type="text" name="email">
        <label>др</label>
        <input type="date" name="birth">
        <label>запомнить меня</label>
        <input type="checkbox" name="remember">
        <input type="submit">Подтвердить</input>
    </form>
</body>
</html>