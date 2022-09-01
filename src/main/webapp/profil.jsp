<%@ page import="com.epam.exhibitions.servlets.db.entity.Ticket" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.exhibitions.servlets.db.TicketsDAOImpl" %>
<%@ page import="com.epam.exhibitions.servlets.db.entity.Exhibitions" %>
<%@ page import="com.epam.exhibitions.servlets.db.ExhibitionsDAOImpl" %>
<%@ page import="com.epam.exhibitions.servlets.db.DAO.ExhibitonHallsDAO" %>
<%@ page import="com.epam.exhibitions.servlets.db.ExhibitonHallsDAOImpl" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Exhibitions</title>
    <link rel="stylesheet" href="asserts/css/profil.css">
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
    <div class="row" style="height: 100%">
        <div class="col-md-1 col-lg-2 col-xl-2" style="background: #A6A69F;"></div>
        <div class="col-12 col-md-10 col-lg-8 col-xl-8" style="background: #F2EAE4;">
            <div class="profile">
                <div class="row">
                    <div class="col-4">
                        <h3 class="nickname"><%=request.getSession().getAttribute("username")%></h3>
                    </div>
                    <div class="col-8">
                        <div class="row">
                            <div class="col-12">
                                <h1 class="name"><%=request.getSession().getAttribute("firthName")%> <%=request.getSession().getAttribute("secondName")%></h1>
                            </div>
                        </div>
                    </div>
                    <div class="col-4">
                        <img src="asserts/img/userProfil.png" width="100%">
                    </div>
                    <div class="col-8">
                        <div class="row">
                            <div class="col-12"><h5 style="font-family: 'Comfortaa', cursive;margin-top: 20px;margin-bottom: 20px">Пошта: <%=request.getSession().getAttribute("email")%></h5></div>
                            <div class="col-12"><h5  style="font-family: 'Comfortaa', cursive;margin-bottom: 20px">Телефон: <%=request.getSession().getAttribute("phone")%></h5></div>
                            <div class="col-12"><h5  style="font-family: 'Comfortaa', cursive;margin-bottom: 20px">Країна: <%=request.getSession().getAttribute("country")%></h5></div>
                            <div class="col-12"><h5  style="font-family: 'Comfortaa', cursive;">Баланс на рахунку: <%=request.getSession().getAttribute("wallet")%> $</h5></div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12">
                        <h3 style="text-align: center;font-family: 'Comfortaa', cursive">Мої квитки:</h3>
                    </div>
                    <div class="row tickets" style="margin: 0 0">
                        <% TicketsDAOImpl ticketsDAO = TicketsDAOImpl.getInstance();
                            List<Ticket> ticketsList = ticketsDAO.toGetTicketsForUser((String) request.getSession().getAttribute("username"));
                            ExhibitionsDAOImpl exhibitionsDAO = ExhibitionsDAOImpl.getInstance();
                            ExhibitonHallsDAOImpl exhibitonHallsDAO = ExhibitonHallsDAOImpl.getInstance();
                            for(Ticket ticket:ticketsList){%>
                                <div class="row ticket" style="margin: 20px 20px 20px">
                                    <div class="col-4 ticketImg">
                                        <%Exhibitions exhibitions = exhibitionsDAO.getExhibitionById(ticket.getIdTicket());%>
                                        <img src="images/<%=exhibitions.getImage()%>" width="100%" height="240px">
                                    </div >
                                    <div class="col-8">
                                        <div class="row">
                                            <div class="col-12">
                                                <p class="data"><%=exhibitions.getNameUA()%></p>
                                            </div>
                                            <div class="col-12">
                                                <p class="data"><%=exhibitions.getThemeUA()%></p>
                                            </div>
                                            <div class="col-12">
                                                <p class="data"><%=exhibitions.getDate_from()+" - "+exhibitions.getDate_to()%></p>
                                            </div>
                                            <div class="col-12">
                                                <% String time_from = exhibitions.getWorking_time_from().toString()+" ";
                                                    String time_to = exhibitions.getWorking_time_to().toString()+" ";
                                                %>
                                                <p class="data">Робочий час: <%= time_from.replace(":00 ","")+" - "+time_to.replace(":00 ","")%></p>
                                            </div>
                                            <div class="col-12">
                                                <p class="data">Зал(и): <%=exhibitonHallsDAO.getHalls(exhibitions.getId_exhibition())%></p>
                                            </div>
                                            <div class="col-12">
                                                <p class="data">Ціна: <%=exhibitions.getPrice()%></p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                        <% }%>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-1 col-lg-2 col-xl-2" style="background: #A6A69F;"></div>
    </div>
</div>
<script src="asserts/js/dropdown.js"></script>
<script src="asserts/js/bootstrap.js"></script>
</body>
</html>


