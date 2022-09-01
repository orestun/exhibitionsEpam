<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ua">
<head>
    <meta charset="UTF-8">
    <title>Exhibitions</title>
    <link rel="stylesheet" href="asserts/css/login-style.css">
    <link href="asserts/icons/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <link rel="stylesheet" href="asserts/css/bootstrap.css">
    <link rel="stylesheet" href="asserts/css/header-style.css">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <header class="p-3 text-bg-dark" style="background: #4F594F">
            <div class="container">
                <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                    <a href="index.jsp" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                        <img src="asserts/img/header.png" class="bi me-2" width="40" role="img" aria-label="icon" >
                    </a>
                    <p class="d-flex align-items-center head" style="font-size: 18px;font-family: 'Comfortaa', cursive;padding: 8px;display: flex;margin: 0 0;color: white">  Exhibitions</p>
                    <ul id="menu" class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                        <li><a href="index.jsp" class="nav-link active px-2 ">Домашня</a></li>
                        <li><a href="aboutMuseum.jsp" class="nav-link px-2 ">Про музей</a></li>
                        <li><a href="holes.jsp" class="nav-link px-2 ">Зали</a></li>
                        <li><a href="exhibitions.jsp" class="nav-link px-2 ">Виставки</a></li>
                        <% if("ADMINISTRATOR".equals(request.getSession().getAttribute("role"))){%>
                        <li><a href="toaddexhibition.jsp" class="nav-link px-2 ">Добавити виставку</a></li>
                        <% }%>
                    </ul>
                    <div id="lang" class="text-end nav ">
                        <li><a href="index.jsp" class="nav-link px-2"><img src="asserts/img/ukraineFlag.png" style="width: 24px;"></a></li>
                        <li><a href="#" class="nav-link px-2"><img src="asserts/img/englishFlag.png" style="width: 24px;"></a></li>
                    </div>
                    <% if(request.getSession().getAttribute("role")==null){%>
                    <div class="text-end">
                        <a href="login.jsp"><button type="button" class="btn btn-outline-light me-2">Ввійти</button></a>
                        <a href="registration.jsp"><button type="button" class="btn btn-warning">Зареєструватись</button></a>
                    </div>
                    <%}else{%>
                    <div class="flex-shrink-0 dropdown">
                        <a href="#" class="d-block link-dark text-decoration-none dropdown-toggle dropbtn" data-bs-toggle="dropdown" aria-expanded="false" onclick="myFunction()">
                            <% if("ADMINISTRATOR".equals(request.getSession().getAttribute("role"))){%>
                            <img src="asserts/img/adminAvatar.png" alt="mdo" width="40" height="40" class="rounded-circle">
                            <%}else if("USER".equals(request.getSession().getAttribute("role"))){%>
                            <img src="asserts/img/userAvatar2.png" alt="mdo" width="40" height="40" class="rounded-circle">
                            <%}%>
                        </a>
                        <ul id="myDropdown" class="dropdown-menu text-small dropdown-content">
                            <li><a class="dropdown-item" href="profil.jsp">Профіль</a></li>
                            <li><a class="dropdown-item" href="basket.jsp">Корзина</a></li>
                            <li><a class="dropdown-item" href="wallet.jsp">Гаманець</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li style="padding: 4px 16px; font-family: 'Comfortaa', cursive;color: #1c7430"><%=request.getSession().getAttribute("wallet")%> $</li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="SignOutServlet">Вийти</a></li>
                        </ul>
                    </div>
                    <%}%>
                </div>
            </div>
        </header>
    </div>
    <div class="row form" style="background:#A6A69F">
        <div class="col-1 col-md-3 col-lg-4 col-xl-4"></div>
            <div class="col-10 col-md-6 col-lg-4 col-xl-4">
                <div class="sign-in-form">
                    <h1>Вхід</h1>
                    <form action="LoginServlet" method="post">
                        <div class="users-pass">
                            <div class="input-box">
                                <% String username = (String)request.getSession().getAttribute("usernameError"); %>
                                <% if (username==null){request.getSession().setAttribute("usernameError", " ");}%>
                                <span class="details">Нікнейм</span>
                                <input name="username" type="text" placeholder="Введіть свій нікнейм" required oninput="usernameError()">
                                <p id="usernameError">
                                    <%=request.getSession().getAttribute("usernameError")%>
                                    <%request.getSession().setAttribute("usernameError"," ");%>
                                </p>
                            </div>
                            <div class="input-box">
                                <% String password = (String)request.getSession().getAttribute("incorrectPassword"); %>
                                <% if (password==null){request.getSession().setAttribute("incorrectPassword", " ");}%>
                                <span class="details">Пароль</span>
                                <input name="password" type="password" placeholder="Введіть свій пароль" required oninput="passwordError()">
                                <p id="incorrectError">
                                    <%=request.getSession().getAttribute("incorrectPassword")%>
                                    <%request.getSession().setAttribute("incorrectPassword"," ");%>
                                </p>
                            </div>
                        </div>
                        <div class="link">
                            <a href="registration.jsp">У мене ще немає аккаунта</a>
                        </div>
                        <div class="submit-button">
                            <input type="submit" value="Ввійти">
                        </div>
                    </form>
                </div>
            </div>
        <div class="col-1 col-md-3 col-lg-4 col-xl-4"></div>
    </div>

</div>

<script>
    function usernameError(){
        var username = document.getElementsByName('username');
        var error = document.getElementById('usernameError');
        if(username.length>=0){
            error.style.display = 'none';
        }
    }
    function passwordError(){
        var password = document.getElementsByName('password');
        var error = document.getElementById('incorrectError');
        if(password.length>=0){
            error.style.display = 'none';
        }
    }
</script>
<script src="asserts/js/bootstrap.bundle.js"></script>
<script src="asserts/js/bootstrap.js"></script>
</body>
</html>