<%@ page import="com.epam.exhibitions.db.entity.Exhibitions" %>
<%@ page import="com.epam.exhibitions.db.DAO.ExhibitionsDAO" %>
<%@ page import="com.epam.exhibitions.db.ExhibitionsDAOImpl" %>
<%@ page import="com.epam.exhibitions.db.ExhibitonHallsDAOImpl" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Exhibitions</title>
    <link rel="stylesheet" href="asserts/css/updateExhibitions-style.css">
    <link rel="stylesheet" href="asserts/css/header-style.css">
    <link rel="stylesheet" href="asserts/css/bootstrap.css">
    <link href="asserts/icons/favicon.ico" rel="shortcut icon" type="image/x-icon" />
</head>
<body>
    <%  String language;
    session.setAttribute("responsePage","updateExhibitions.jsp");
    if(session.getAttribute("language")==null){
        language = "uk";
    }   else{
        language = (String) session.getAttribute("language");
    }%>

<fmt:setLocale value="<%=language%>"/>
<fmt:setBundle basename="languages"/>
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
    <div class="row">
        <div class="col-md-1 col-lg-2 col-xl-2" style="background: #A6A69F"></div>
        <div class="col-12 col-md-10 col-lg-8 col-xl-8" style="background: #F2EAE4;">
            <div class="addExForm">
                <div class="row">
                    <form enctype="multipart/form-data"  method="post" action="UpdateExhibition">
                        <div class="col-12">
                            <h1 class="head">Оновлення виставки</h1>
                        </div>
                            <%ExhibitionsDAOImpl exhibitionsDAO = ExhibitionsDAOImpl.getInstance();
                            int id = (int) request.getSession().getAttribute("idForUpdate");
                            ExhibitonHallsDAOImpl exhibitonHallsDAO = ExhibitonHallsDAOImpl.getInstance();
                            List<Boolean> listHalls = exhibitonHallsDAO.getListHalls(id);

                            Exhibitions exhibitions = exhibitionsDAO.getExhibitionById(id);%>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-6"><h5><fmt:message key="toAddExhibition.input.title.uk"/> <img src="asserts/img/UkraineFlag.png" height="20px"></h5></div>
                            <div class="col-6"><h5><fmt:message key="toAddExhibition.input.title.en"/> <img src="asserts/img/EnglishFlag.png" height="20px"></h5></div>
                        </div>
                        <div class="row">
                            <div class="col-6" style="border-right: 1px #4F594F solid">
                                <h5><fmt:message key="toAddExhibition.input.name"/>:</h5>
                            </div>
                            <div class="col-6" style="border-left: 1px #4F594F solid">
                                <h5><fmt:message key="toAddExhibition.input.name"/>:</h5>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-6" style="border-right: 1px #4F594F solid">
                                <input type="text" class="inputName" name="nameUA" placeholder="<fmt:message key="toAddExhibition.input.lang.uk"/>" maxlength="75" value="<%=exhibitions.getNameUA()%>" oninput="namesError()" required>
                            </div>
                            <div class="col-6" style="border-left: 1px #4F594F solid">
                                <input type="text" class="inputName" name="nameEN" placeholder="<fmt:message key="toAddExhibition.input.lang.en"/>" maxlength="75" value="<%=exhibitions.getNameEN()%>" oninput="namesError()" required>
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
                                <h5><fmt:message key="toAddExhibition.input.theme"/>:</h5>
                            </div>
                            <div class="col-6" style="border-left: 1px #4F594F solid">
                                <h5><fmt:message key="toAddExhibition.input.theme"/>:</h5>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-6" style="border-right: 1px #4F594F solid">
                                <input  type="text" class="inputName" name="themeUA" value="<%=exhibitions.getThemeUA()%>" placeholder="<fmt:message key="toAddExhibition.input.lang.uk"/>" maxlength="50" required>
                            </div>
                            <div class="col-6" style="border-left: 1px #4F594F solid">
                                <input type="text" class="inputName" name="themeEN" value="<%=exhibitions.getThemaEN()%>" placeholder="<fmt:message key="toAddExhibition.input.lang.en"/>" maxlength="50" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <% String dateFrom = (String)request.getSession().getAttribute("dateFromError"); %>
                                <% if (dateFrom==null){request.getSession().setAttribute("dateFromError", " ");}%>
                                <h5><fmt:message key="toAddExhibition.input.startDate"/>:</h5>
                                <p id="dateError" style="text-align: center;color: #cb1111;font-size: 12px;-webkit-user-select: none;">
                                    <%=request.getSession().getAttribute("dateFromError")%>
                                    <%request.getSession().setAttribute("dateFromError"," ");%>
                                </p>
                            </div>
                            <div class="col-6">
                                <% String dateTo = (String)request.getSession().getAttribute("dateToError"); %>
                                <% if (dateTo==null){request.getSession().setAttribute("dateToError", " ");}%>
                                <h5><fmt:message key="toAddExhibition.input.endDate"/>:</h5>
                                <p id="dateToError" style="text-align: center;color: #cb1111;font-size: 12px;-webkit-user-select: none;">
                                    <%=request.getSession().getAttribute("dateToError")%>
                                    <%request.getSession().setAttribute("dateToError"," ");%>
                                </p>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-6">
                                <input  type="date" class="inputDate" name="date_from" value="<%=exhibitions.getDate_from()%>" oninput="dateError()" required>
                            </div>
                            <div class="col-6">
                                <input type="date" class="inputDate" name="date_to" value="<%=exhibitions.getDate_to()%>" oninput="dateToError()" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <% String timeFrom = (String)request.getSession().getAttribute("TimeToError"); %>
                                <% if (timeFrom==null){request.getSession().setAttribute("TimeToError", " ");}%>
                                <h5><fmt:message key="toAddExhibition.input.startTime"/>:</h5>
                                <p id="timeToError" style="text-align: center;color: #cb1111;font-size: 12px;-webkit-user-select: none;">
                                    <%=request.getSession().getAttribute("TimeToError")%>
                                    <%request.getSession().setAttribute("TimeToError"," ");%>
                                </p>
                            </div>
                            <div class="col-6">
                                <h5><fmt:message key="toAddExhibition.input.endTime"/>:</h5>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-6">
                                <input min="08:00" max="22:00" type="time" class="inputTime" name="working_time_from" value="<%=exhibitions.getWorking_time_from().toString().substring(0,5)%>" oninput="timeToError()" required>
                            </div>
                            <div class="col-6">
                                <input min="08:00" max="22:00" type="time" class="inputTime" name="working_time_to" value="<%=exhibitions.getWorking_time_to().toString().substring(0,5)%>" required>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-12">
                                <h5><fmt:message key="toAddExhibition.input.halls.title"/>:</h5>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-2" >
                                <div class="check">
                                    <input type="checkbox" id="hall1" name="hall1" <%if(listHalls.get(0)){%>checked<%}%> value="hall1" oninput="hallError()">
                                    <label for="hall1"> <fmt:message key="toAddExhibition.input.hall"/>1</label>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="check">
                                    <input type="checkbox" id="hall2" name="hall2" <%if(listHalls.get(1)){%>checked<%}%> value="hall2" oninput="hallError()">
                                    <label for="hall2"> <fmt:message key="toAddExhibition.input.hall"/>2</label>
                                </div>

                            </div>
                            <div class="col-4">
                                <div class="check">
                                    <input type="checkbox" id="hall3" name="hall3" <%if(listHalls.get(2)){%>checked<%}%> value="hall3" oninput="hallError()">
                                    <label for="hall3"> <fmt:message key="toAddExhibition.input.hall"/>3</label>
                                </div>

                            </div>
                            <div class="col-2">
                                <div class="check">
                                    <input type="checkbox" id="hall4" name="hall4" <%if(listHalls.get(3)){%>checked<%}%> value="hall4" oninput="hallError()">
                                    <label for="hall4"> <fmt:message key="toAddExhibition.input.hall"/>4</label>
                                </div>

                            </div>
                            <div class="col-2">
                                <div class="check">
                                    <input type="checkbox" id="hall5" name="hall5" <%if(listHalls.get(4)){%>checked<%}%> value="hall5" oninput="hallError()">
                                    <label for="hall5"> <fmt:message key="toAddExhibition.input.hall"/>5</label>
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
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-5">
                                <h5>Поточне фото</h5>
                            </div>
                            <div class="col-2">

                            </div>
                            <div class="col-5">
                                <h5>Нове фото</h5>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-5">
                                <img src="images/<%=exhibitions.getImage()%>" width="250" height="250" class="currentImage">
                            </div>
                            <div class="col-2">
                                <img src="asserts/img/change.png" width="60" height="60" style=" margin-left: auto;margin-right: auto;margin-top: 95px;display: block">
                            </div>
                            <div class="col-5">
                                <img src="asserts/img/thereIsNoPhotoUploaded.png" width="250" height="250" class="photoUploaded" id="photoUploaded">
                            </div>
                        </div>
                        <div class="col-12">
                            <label class="uploadLabel" style="margin-left: 40%">
                                <input type="file" id="file" name="file" class="uploadButton" accept="image/*" oninput="showImage()"/>
                                <img src="asserts/img/fileUpload.png" height="20px">
                                Upload new
                            </label>
                        </div>
                        <div class="row" style="margin-top: 20px;margin-bottom: 20px">
                            <div class="col-12">
                                <h5><fmt:message key="toAddExhibition.input.price"/>:</h5>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-12">
                                <input class="paidForm" type="text" name="price" placeholder="00.00" value="<%=exhibitions.getPrice()%>"
                                       oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" required>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 5px">
                            <div class="col-12">
                                <input type="submit" name="button" class="button" value="Оновити">
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 20px">
                            <div class="col-12">
                                <a href="" onclick="postToUrl('GetIdForUpdate', {'id':'<%=exhibitions.getId_exhibition()%>'}, 'POST');" class="buttonCansel">Скинути виправлення</a>
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
    function showImage(){
        console.log('=^.^=')
        var file = document.getElementById('file').files[0]
        var reader  = new FileReader();
        reader.onload = function(e)  {
            var image = document.createElement("img");
            image.src = e.target.result;
            var photoUploaded = document.getElementById('photoUploaded');
            photoUploaded.src = image.src
        }
        reader.readAsDataURL(file);
    }
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