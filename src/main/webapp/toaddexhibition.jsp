<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Exhibitions</title>
    <link rel="stylesheet" href="asserts/css/toaddexhibition-style.css">
    <link rel="stylesheet" href="asserts/css/header-style.css">
    <link rel="stylesheet" href="asserts/css/bootstrap.css">
    <link href="asserts/icons/favicon.ico" rel="shortcut icon" type="image/x-icon" />
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
                        <li><a href="index.jsp" class="nav-link px-2 ">Домашня</a></li>
                        <li><a href="aboutMuseum.jsp" class="nav-link px-2 ">Про музей</a></li>
                        <li><a href="holes.jsp" class="nav-link px-2 ">Зали</a></li>
                        <li><a href="exhibitions.jsp" class="nav-link px-2 ">Виставки</a></li>
                        <% if("ADMINISTRATOR".equals(request.getSession().getAttribute("role"))){%>
                        <li><a href="toaddexhibition.jsp" class="nav-link active px-2 ">Добавити виставку</a></li>
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
                            <li><a class="dropdown-item" href="profil.jsp"><img src="asserts/img/iconProfil.png" width="20px"> Профіль</a></li>
                            <li><a class="dropdown-item" href="basket.jsp"><img src="asserts/img/iconTicket.png" width="20px"> Корзина</a></li>
                            <li><a class="dropdown-item" href="wallet.jsp"><img src="asserts/img/iconWallet.png" width="20px"> Гаманець</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li style="padding: 4px 16px; font-family: 'Comfortaa', cursive;color: #1c7430"><%=request.getSession().getAttribute("wallet")%> $</li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="SignOutServlet"><img src="asserts/img/iconExit.png" width="20px"> Вийти</a></li>
                        </ul>
                    </div>
                    <%}%>
                </div>
            </div>
        </header>
    </div>
    <div class="row">
        <div class="col-md-1 col-lg-2 col-xl-2" style="background: #A6A69F"></div>
        <div class="col-12 col-md-10 col-lg-8 col-xl-8" style="background: #F2EAE4">
            <div class="addExForm">
                <div class="row">
                    <form enctype='multipart/form-data' method="post" action="${pageContext.request.contextPath}/ToAddExhibitionServlet">
                        <div class="col-12">
                            <h1 class="head">Нова виставка</h1>
                        </div>
                        <div class="col-12">
                            <h5>Завантажте фото</h5>
                        </div>
                        <div class="col-12">
                            <label class="uploadLabel" style="margin-left: 40%">
                                <input type="file" name="file" class="uploadButton" accept="image/*" required/>
                                <img src="asserts/img/fileUpload.png" height="20px">
                                Upload
                            </label>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-6"><h5>Ввід українською <img src="asserts/img/ukraineFlag.png" height="20px"></h5></div>
                            <div class="col-6"><h5>Ввід аглійською <img src="asserts/img/englishFlag.png" height="20px"></h5></div>
                        </div>
                        <div class="row">
                            <div class="col-6" style="border-right: 1px #4F594F solid">
                                <h5>Введіть назву:</h5>
                            </div>
                            <div class="col-6" style="border-left: 1px #4F594F solid">
                                <h5>Введіть назву:</h5>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-6" style="border-right: 1px #4F594F solid">
                                <input type="text" class="inputName" name="nameUA" placeholder="українською" maxlength="200" oninput="namesError()" required>
                            </div>
                            <div class="col-6" style="border-left: 1px #4F594F solid">
                                <input type="text" class="inputName" name="nameEN" placeholder="англійською" maxlength="200" oninput="namesError()" required>
                            </div>
                            <% String nameRepeat = (String)request.getSession().getAttribute("namesError"); %>
                            <% if (nameRepeat==null){request.getSession().setAttribute("namesError", " ");}%>
                            <div class="col-12">
                                <p id="namesErrorid" style="text-align: center;color: #cb1111;font-size: 12px;-webkit-user-select: none;margin: 5px 0 0;">
                                    <%=request.getSession().getAttribute("namesError")%>
                                    <%request.getSession().setAttribute("namesError"," ");%>
                                </p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6" style="border-right: 1px #4F594F solid">
                                <h5>Введіть тему:</h5>
                            </div>
                            <div class="col-6" style="border-left: 1px #4F594F solid">
                                <h5>Введіть тему:</h5>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-6" style="border-right: 1px #4F594F solid">
                                <input  type="text" class="inputName" name="themeUA" placeholder="українською" maxlength="50" required>
                            </div>
                            <div class="col-6" style="border-left: 1px #4F594F solid">
                                <input type="text" class="inputName" name="themeEN" placeholder="англійською" maxlength="50" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <% String dateFrom = (String)request.getSession().getAttribute("dateFromError"); %>
                                <% if (dateFrom==null){request.getSession().setAttribute("dateFromError", " ");}%>
                                <h5>Введіть дату початку:</h5>
                                <p id="dateError" style="text-align: center;color: #cb1111;font-size: 12px;-webkit-user-select: none;">
                                    <%=request.getSession().getAttribute("dateFromError")%>
                                    <%request.getSession().setAttribute("dateFromError"," ");%>
                                </p>
                            </div>
                            <div class="col-6">
                                <% String dateTo = (String)request.getSession().getAttribute("dateToError"); %>
                                <% if (dateTo==null){request.getSession().setAttribute("dateToError", " ");}%>
                                <h5>Введіть дату закінчення:</h5>
                                <p id="dateToError" style="text-align: center;color: #cb1111;font-size: 12px;-webkit-user-select: none;">
                                    <%=request.getSession().getAttribute("dateToError")%>
                                    <%request.getSession().setAttribute("dateToError"," ");%>
                                </p>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-6">
                                <input  type="date" class="inputDate" name="date_from" oninput="dateError()" required>
                            </div>
                            <div class="col-6">
                                <input type="date" class="inputDate" name="date_to" oninput="dateToError()" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <% String timeFrom = (String)request.getSession().getAttribute("TimeToError"); %>
                                <% if (timeFrom==null){request.getSession().setAttribute("TimeToError", " ");}%>
                                <h5>Введіть час початку:</h5>
                                <p id="timeToError" style="text-align: center;color: #cb1111;font-size: 12px;-webkit-user-select: none;">
                                    <%=request.getSession().getAttribute("TimeToError")%>
                                    <%request.getSession().setAttribute("TimeToError"," ");%>
                                </p>
                            </div>
                            <div class="col-6">
                                <h5>Введіть час закінчення:</h5>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-6">
                                <input min="08:00" max="22:00" type="time" class="inputTime" name="working_time_from" oninput="timeToError()" required>
                            </div>
                            <div class="col-6">
                                <input min="08:00" max="22:00" type="time" class="inputTime" name="working_time_to" required>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-12">
                                <h5>Оберіть зал(и) які займатиме виставка:</h5>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-2" >
                                <div class="check">
                                    <input type="checkbox" id="hall1" name="hall1" value="hall1" oninput="hallError()">
                                    <label for="hall1"> Зал 1</label>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="check">
                                    <input type="checkbox" id="hall2" name="hall2" value="hall2" oninput="hallError()">
                                    <label for="hall2"> Зал 2</label>
                                </div>

                            </div>
                            <div class="col-4">
                                <div class="check">
                                    <input type="checkbox" id="hall3" name="hall3" value="hall3" oninput="hallError()">
                                    <label for="hall3"> Зал 3</label>
                                </div>

                            </div>
                            <div class="col-2">
                                <div class="check">
                                    <input type="checkbox" id="hall4" name="hall4" value="hall4" oninput="hallError()">
                                    <label for="hall4"> Зал 4</label>
                                </div>

                            </div>
                            <div class="col-2">
                                <div class="check">
                                    <input type="checkbox" id="hall5" name="hall5" value="hall5" oninput="hallError()">
                                    <label for="hall5"> Зал 5</label>
                                </div>
                            </div>
                            <% String Hall = (String)request.getSession().getAttribute("HallError"); %>
                            <% if (timeFrom==null){request.getSession().setAttribute("HallError", " ");}%>
                            <div class="col-12">
                                <p id="HallError" style="margin: 5px 0 0;text-align: center;color: #cb1111;font-size: 12px;-webkit-user-select: none;">
                                    <%=request.getSession().getAttribute("HallError")%>
                                    <%request.getSession().setAttribute("HallError"," ");%>
                                </p>
                            </div>
                        </div>
                        <div class="row" style="margin-top: 20px;margin-bottom: 20px">
                            <div class="col-12">
                                <h5>Введіть вартість квитка:</h5>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-12">
                                <input class="paidForm" type="text" name="price" placeholder="00.00"
                                       oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" required>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-12">
                                <input type="submit" name="button" class="button" value="Додати">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-1 col-lg-2 col-xl-2" style="background: #A6A69F"></div>
    </div>
</div>
<script src="asserts/js/dropdown.js"></script>
<script src="asserts/js/bootstrap.js"></script>
<script>
    function dateError(){
        var date_from = document.getElementsByName('date_from');
        var error = document.getElementById('dateError');
        if(date_from.length>=0){
            error.style.display = 'none';
        }
    }
    function dateToError(){
        var date_to = document.getElementsByName('date_to');
        var error = document.getElementById('dateToError');
        if(date_to.length>=0){
            error.style.display = 'none';
        }
    }
    function timeToError(){
        var time_to = document.getElementsByName('working_time_from');
        var error = document.getElementById('timeToError');
        if(time_to.length>=0){
            error.style.display = 'none';
        }
    }
    function namesError(){
        var nameUA = document.getElementsByName('nameUA');
        var nameEN = document.getElementsByName('nameEN');
        var error = document.getElementById('namesErrorid');
        if(nameUA.length>=0||nameEN.length>=0){
            error.style.display = 'none';
        }
    }
    function hallError(){
        var hall1 = document.getElementsByName('hall1');
        var hall2 = document.getElementsByName('hall2');
        var hall3 = document.getElementsByName('hall3');
        var hall4 = document.getElementsByName('hall4');
        var hall5 = document.getElementsByName('hall5');
        var error = document.getElementById('HallError');
        if(hall1.length>=0||hall2.length>=0||hall3.length>=0||hall4.length>=0||hall5.length>=0){
            error.style.display = 'none';
        }
    }
</script>
</body>
</html>


