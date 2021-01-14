<%@ page import="models.User" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
    <script src="<%=request.getContextPath()%>/script.js"></script>
    <title>Мои объявления</title>
</head>
<body>
<div class="container pt-3">
    <%  User user = (User) request.getSession().getAttribute("user");
        if (user != null) { %>
    <a class="nav-link" href="<%=request.getContextPath()%>/login.do">
        <%=user.getName()%>
        | Выйти </a>
    <a class="nav-link" href="<%=request.getContextPath()%>/myannouncements.do">
        Мои обьявления</a>
    <% }  %>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <form action="<%=request.getContextPath()%>/addAnnouncement.do" method="get">
                <p><button id="filterBtn" class="btn btn-primary" type="submit">Добавить объявление</button></p>
                </form>
            </div>

            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">№</th>
                        <th scope="col">Заголовок</th>
                        <th scope="col">Описание</th>
                        <th scope="col">Дата создания</th>
                        <th scope="col">Цена</th>
                        <th scope="col">Детали</th>
                        <th scope="col">Действие</th>
                    </tr>
                    </thead>
                    <tbody id="myitemsList">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
