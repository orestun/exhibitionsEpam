<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.epam.exhibitions.db.UserDAOImpl" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html >
<html lang="ua">
<head>
    <meta charset="UTF-8">
    <title>Exhibitions</title>
    <link rel="stylesheet" href="asserts/css/registration-style.css">
    <link href="asserts/icons/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    <link rel="stylesheet" href="asserts/css/bootstrap.css">
    <link href="asserts">
</head>
<body>
<%  String language;
    session.setAttribute("responsePage","registration.jsp");
    if(session.getAttribute("language")==null){
        language = "uk";
    }   else{
        language = (String) session.getAttribute("language");
    }%>

<fmt:setLocale value="<%=language%>"/>
<fmt:setBundle basename="languages"/>
<div class="container-fluid" >
    <div class="row">
        <header class="p-3 text-bg-dark" style="background: #4F594F">
            <div class="container">
                <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                    <a href="index.jsp" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                        <img src="asserts/img/header.png" class="bi me-2" width="40" role="img" aria-label="icon" >
                    </a>
                    <p class="d-flex align-items-center head" style="font-size: 18px;font-family: 'Comfortaa', cursive;padding: 8px;display: flex;margin: 0 0;color: white">  Exhibitions</p>
                    <ul id="menu" class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                        <li><a href="index.jsp" class="nav-link px-2"><fmt:message key="header.home"/></a></li>
                        <li><a href="aboutMuseum.jsp" class="nav-link px-2 "><fmt:message key="header.aboutMuseum"/> </a></li>
                        <li><a href="holes.jsp" class="nav-link px-2"><fmt:message key="header.halls"/></a></li>
                        <li><a href="exhibitions.jsp" class="nav-link px-2 "><fmt:message key="header.exhibition"/></a></li>
                        <% if("ADMINISTRATOR".equals(request.getSession().getAttribute("role"))){%>
                        <li><a href="toaddexhibition.jsp" class="nav-link px-2"><fmt:message key="header.toAddExhibition"/></a></li>
                        <% }%>
                    </ul>
                    <div id="lang" class="text-end nav ">
                        <li><a href="ChangeLanguage?language=uk" class="nav-link px-2"><img src="asserts/img/UkraineFlag.png" style="width: 24px;"></a></li>
                        <li><a href="ChangeLanguage?language=en" class="nav-link px-2"><img src="asserts/img/EnglishFlag.png" style="width: 24px;"></a></li>
                    </div>
                    <% if(request.getSession().getAttribute("role")==null){%>
                    <div class="text-end">
                        <a href="login.jsp"><button type="button" class="btn btn-outline-light me-2"><fmt:message key="header.signIn"/></button></a>
                        <a href="registration.jsp"><button type="button" class="btn btn-warning"><fmt:message key="header.signUp"/></button></a>
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
                            <li><a class="dropdown-item" href="profil.jsp"><img src="asserts/img/iconProfil.png" width="20px"> <fmt:message key="header.profile"/></a></li>
                            <li><a class="dropdown-item" href="basket.jsp"><img src="asserts/img/iconTicket.png" width="20px"> <fmt:message key="header.basket"/></a></li>
                            <li><a class="dropdown-item" href="wallet.jsp"><img src="asserts/img/iconWallet.png" width="20px"> <fmt:message key="header.wallet"/></a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li style="padding: 4px 16px; font-family: 'Comfortaa', cursive;color: #1c7430"><%=request.getSession().getAttribute("wallet")%> $</li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="SignOutServlet"><img src="asserts/img/iconExit.png" width="20px"> <fmt:message key="header.signOut"/></a></li>
                        </ul>
                    </div>
                    <%}%>
                </div>
            </div>
        </header>
    </div>
    <div class="row" style="background:#A6A69F">
        <div class="col-md-1 col-lg-2 col-xl-3"></div>
        <div class="col-12 col-md-10 col-lg-8 col-xl-6">
            <div class="login-form">
                <h1>Створити аккаунт</h1>
                <form action="Registration" method="post">
                    <div class="user-details">
                        <div class="input-box">
                            <span class="details">Ім'я</span>
                            <input name="name" maxlength="30" type="text" placeholder="Введіть своє ім'я" required>
                        </div>
                        <div class="input-box">
                            <span class="details">Прізвище</span>
                            <input name="secondName" maxlength="30" type="text" placeholder="Введіть своє прізвище" required>
                        </div>
                        <div class="input-box">
                            <span class="details">Email</span>
                            <% String email = (String)request.getSession().getAttribute("emailRepeat"); %>
                            <% if (email==null){request.getSession().setAttribute("emailRepeat", " ");}%>
                            <input name="email" type="email" placeholder="Введіть свій email" required oninput="emailError()">
                                <p id="emailError">
                                    <%=request.getSession().getAttribute("emailRepeat")%>
                                    <%request.getSession().setAttribute("emailRepeat"," ");%>
                                </p>
                        </div>
                        <div class="input-box">
                            <% String phone = (String)request.getSession().getAttribute("phoneRepeat"); %>
                            <% if (phone==null){request.getSession().setAttribute("phoneRepeat", " ");}%>
                            <span class="details">Номер телефону</span>
                            <input name="phone" type="text" placeholder="Введіть свій номер телефону" required oninput="phoneError()">
                            <p id="phoneError">
                                <%=request.getSession().getAttribute("phoneRepeat")%>
                                <% request.getSession().setAttribute("phoneRepeat", " ");%>
                            </p>
                        </div>
                        <div class="input-box">
                            <span class="details">Виберіть свою країну</span>
                            <label>
                                <select name="country"  class="country" style="width: 100%" required>
                                    <optgroup label="Європа">
                                        <option value="Австрія">Австрія</option>
                                        <option value="Азербайджан">Азербайджан</option>
                                        <option value="Албанія">Албанія</option>
                                        <option value="Андорра">Андорра</option>
                                        <option value="Бельгія">Бельгія</option>
                                        <option value="Білорусь">Білорусь</option>
                                        <option value="Болгарія">Болгарія</option>
                                        <option value="Боснія і Герцеговина">Боснія і Герцеговина</option>
                                        <option value="Ватикан">Ватикан</option>
                                        <option value="Велика Британія">Велика Британія</option>
                                        <option value="Греція">Греція</option>
                                        <option value="Грузія">Грузія</option>
                                        <option value="Данія">Данія</option>
                                        <option value="Естонія">Естонія</option>
                                        <option value="Ірландія">Ірландія</option>
                                        <option value="Ісландія">Ісландія</option>
                                        <option value="Іспанія">Іспанія</option>
                                        <option value="Італія">Італія</option>
                                        <option value="Казахстан">Казахстан</option>
                                        <option value="Кіпр">Кіпр</option>
                                        <option value="Косово">Косово</option>
                                        <option value="Латвія">Латвія</option>
                                        <option value="Литва">Литва</option>
                                        <option value="Ліхтенштейн">Ліхтенштейн</option>
                                        <option value="Люксембург">Люксембург</option>
                                        <option value="Мальта">Мальта</option>
                                        <option value="Молдова">Молдова</option>
                                        <option value="Монако">Монако</option>
                                        <option value="Нідерланди">Нідерланди</option>
                                        <option value="Німеччина">Німеччина</option>
                                        <option value="Норвегія">Норвегія</option>
                                        <option value="Північна Македонія">Північна Македонія</option>
                                        <option value="Польща">Польща</option>
                                        <option value="Португалія">Португалія</option>
                                        <option value="Румунія">Румунія</option>
                                        <option value="Сан-Марино">Сан-Марино</option>
                                        <option value="Сербія">Сербія</option>
                                        <option value="Словаччина">Словаччина</option>
                                        <option value="Словенія">Словенія</option>
                                        <option value="Туреччина">Туреччина</option>
                                        <option value="Угорщина">Угорщина</option>
                                        <option value="Україна">Україна</option>
                                        <option value="Фінляндія">Фінляндія</option>
                                        <option value="Франція">Франція</option>
                                        <option value="Хорватія">Хорватія</option>
                                        <option value="Чехія">Чехія</option>
                                        <option value="Чорногорія">Чорногорія</option>
                                        <option value="Швейцарія">Швейцарія</option>
                                        <option value="Швеція">Швеція</option>
                                    </optgroup>
                                </select>
                            </label>
                        </div>
                        <div class="input-box">
                            <% String username = (String)request.getSession().getAttribute("usernameRepeat"); %>
                            <% if (username==null){request.getSession().setAttribute("usernameRepeat", " ");}%>
                            <span class="details">Нікнейм</span>
                            <input name="username" type="text" placeholder="Введіть нікнейм" required oninput="usernameError()">
                            <p id="usernameError" >
                                <%=request.getSession().getAttribute("usernameRepeat")%>
                                <%request.getSession().setAttribute("usernameRepeat"," ");%>
                            </p>
                        </div>
                        <div class="input-box">
                            <label for="userPassword">Пароль(мінімум 8 символів)</label>
                            <input name="password" minlength="8" id="userPassword" type="password" autocomplete="current-password" placeholder="Прийдумай пароль" required oninput="checkPasswords()">
                        </div>
                        <div class="input-box">
                            <label for="userPasswordRepeat">Підтвердження паролю</label>
                            <input minlength="8" id="userPasswordRepeat" type="password" autocomplete="current-password" placeholder="Повтори пароль" required oninput="checkPasswords()">
                            <p id="passwordError" style="display: none" >
                                Паролі повинні співпадати!
                            </p>
                        </div>
                    </div>
                    <div class="link">
                        <a href="login.jsp">Я вже маю аккаунт</a>
                    </div>

                    <div class="submit-button">
                        <input id="button" type="submit" value="Зареєструватись" >
                        <script type="text/javascript">
                            function emailError(){
                                var email = document.getElementsByName('email');
                                var error = document.getElementById('emailError');
                                if(email.length>=0){
                                    error.style.display = 'none';
                                }
                            }
                            function phoneError(){
                                var phone = document.getElementsByName('phone');
                                var error = document.getElementById('phoneError');
                                if(phone.length>=0){
                                    error.style.display = 'none';
                                }
                            }
                            function usernameError(){
                                var username = document.getElementsByName('username');
                                var error = document.getElementById('usernameError');
                                if(username.length>=0){
                                    error.style.display = 'none';
                                }
                            }
                            function checkPasswords(){
                                var firstPassword = document.getElementById('userPassword').value;
                                var secondPassword = document.getElementById('userPasswordRepeat').value;
                                var error = document.getElementById('passwordError');
                                var button = document.getElementById('button');
                                console.log(secondPassword==='')
                                if (firstPassword===secondPassword||secondPassword===''){
                                    error.style.display = 'none';
                                    button.disabled = false;
                                }
                                else{
                                    error.style.display = 'block';
                                    button.disabled = true;
                                }
                            }
                            function myFunction() {
                                document.getElementById("myDropdown").classList.toggle("show");
                            }

                            window.onclick = function(event) {
                                if (!event.target.matches('.dropbtn')) {
                                    var dropdowns = document.getElementsByClassName("dropdown-content");
                                    var i;
                                    for (i = 0; i < dropdowns.length; i++) {
                                        var openDropdown = dropdowns[i];
                                        if (openDropdown.classList.contains('show')) {
                                            openDropdown.classList.remove('show');
                                        }
                                    }
                                }
                            }
                        </script>
                    </div>
                </form>
            </div>
            </div>
        <div class="col-md-1 col-lg-2 col-xl-3"></div>
</div>
</div>
<script src="asserts/js/bootstrap.js"></script>
</body>
</html>