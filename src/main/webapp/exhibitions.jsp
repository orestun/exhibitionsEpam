<%@ page import="java.util.List" %>
<%@ page import="com.epam.exhibitions.db.entity.Exhibitions" %>
<%@ page import="com.epam.exhibitions.db.ExhibitionsDAOImpl" %>
<%@ page import="com.epam.exhibitions.db.DAO.ExhibitonHallsDAO" %>
<%@ page import="com.epam.exhibitions.db.ExhibitonHallsDAOImpl" %>
<%@ page import="com.epam.exhibitions.db.TicketsDAOImpl" %>
<%@ page import="java.util.Objects" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Exhibitions</title>
    <link rel="stylesheet" href="asserts/css/exhibitions-style.css">
    <link rel="stylesheet" href="asserts/css/header-style.css">
    <link rel="stylesheet" href="asserts/css/bootstrap.css">
    <link href="asserts/icons/favicon.ico" rel="shortcut icon" type="image/x-icon" />
</head>
<body>
    <%  String language;
        session.setAttribute("responsePage","exhibitions.jsp");
        if(session.getAttribute("language")==null){
            language = "uk";
        }   else{
            language = (String) session.getAttribute("language");
        }
    %>
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
                        <li><a href="index.jsp" class="nav-link px-2 "><fmt:message key="header.home"/></a></li>
                        <li><a href="aboutMuseum.jsp" class="nav-link px-2 "><fmt:message key="header.aboutMuseum"/> </a></li>
                        <li><a href="holes.jsp" class="nav-link px-2 "><fmt:message key="header.halls"/></a></li>
                        <li><a href="exhibitions.jsp" class="nav-link active px-2 "><fmt:message key="header.exhibition"/></a></li>
                        <% if("ADMINISTRATOR".equals(request.getSession().getAttribute("role"))){%>
                        <li><a href="toaddexhibition.jsp" class="nav-link px-2 "><fmt:message key="header.toAddExhibition"/></a></li>
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
        <div class="col-md-2 col-lg-2 col-xl-2" style="background: #A6A69F">
        </div>
        <div class="col-10 col-md-10 col-lg-8 col-xl-8" style="background: #F2EAE4">
            <div class="exhibitions">
                <form action="SortExhibitions" method="post">
                    <div class="row" style="margin-top: 20px">
                        <div class="col-2">
                                <h6 style=" text-align: center;font-family: 'Comfortaa', cursive;"><fmt:message key="exhibitions.sorting.title"/>: </h6>
                        </div>
                        <div class="col-7">
                            <div class="row">
                                <div class="col-4">
                                    <a href="SortingByPrice?sorting=common" class="sortingLinks"><p><fmt:message key="exhibitions.sorting.allExhibitions"/></p></a>
                                </div>
                                <div class="col-4">
                                    <a href="SortingByPrice?sorting=FromHighToLow" class="sortingLinks"><p><fmt:message key="exhibitions.sorting.PriceFromHigh"/></p></a>
                                </div>
                                <div class="col-4">
                                    <a href="SortingByPrice?sorting=FromLowToHigh" class="sortingLinks"><p><fmt:message key="exhibitions.sorting.PriceFromLow"/></p></a>
                                </div>
                            </div>
                        </div>
                        <div class="col-3">
                            <input style="height: 33.33px;width: 100%;padding: 5px;text-align: center;font-family: 'Comfortaa', cursive;border-radius: 5px;border: #4F594F 2px solid;font-size: 16px;" type="submit" value="<fmt:message key="exhibitions.sorting.button.title"/>" name="buttonSort" class="buttonSort">
                        </div>
                    </div>
                    <div class="row" style="margin-top: 20px">
                        <div class="col-4">
                            <input type="text" name="name" class="dataSort" placeholder="Search by name">
                        </div>
                        <div class="col-5">
                            <div class="row">
                                <div class="col-4">
                                    <p style="text-align: center;font-family: 'Comfortaa', cursive"><fmt:message key="exhibitions.sorting.PriceFrom.title"/></p>
                                </div>
                                <%ExhibitionsDAOImpl exhibitionsDAO = ExhibitionsDAOImpl.getInstance();%>
                                <div class="col-3" style="padding: 0 0">
                                    <input class="dataSort" min="<%=exhibitionsDAO.minPrice().toString()%>" max="<%=exhibitionsDAO.maxPrice().toString()%>" type="number" name="priceFrom" width="40px">
                                </div>
                                <div class="col-2">
                                    <p style="text-align: center;font-family: 'Comfortaa', cursive"><fmt:message key="exhibitions.sorting.PriceTo.title"/></p>
                                </div>
                                <div class="col-3" style="padding: 0 0">
                                    <input class="dataSort" min="<%=exhibitionsDAO.minPrice()%>" max="<%=exhibitionsDAO.maxPrice()%>" type="number" name="priceTo" width="40px" >
                                </div>
                            </div>
                        </div>
                        <div class="col-3">
                            <div class="row">
                                <div class="col-5">
                                    <p style="text-align: center;font-family: 'Comfortaa', cursive"><fmt:message key="exhibitions.sorting.Data.title"/></p>
                                </div>
                                <div class="col-7" style="padding: 0 0">
                                    <input class="dataSort" type="date" name="date" width="40px" min="">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="row">
                <%if(session.getAttribute("sortingReturn")!=null){
                    session.setAttribute("sorting","true");
                }%>
                <%  TicketsDAOImpl ticketsDAO = TicketsDAOImpl.getInstance();
                    ExhibitonHallsDAO exhibitonHallsDAO = ExhibitonHallsDAOImpl.getInstance();
                    List<Exhibitions> exhibitionsList = new ArrayList<>();
                    if(session.getAttribute("sorting")==null) {
                    if (session.getAttribute("sortingListCommon") != null) {
                        exhibitionsList = (List<Exhibitions>) session.getAttribute("sortingListCommon");
                    } else {
                        exhibitionsList = exhibitionsDAO.exhibitionsCommonList();
                    }
                    session.setAttribute("sortingListCommon", exhibitionsList);
                } else if(session.getAttribute("sorting")=="true") {
                    exhibitionsList = (List<Exhibitions>) request.getSession().getAttribute("sortingList");
                } else{
                        System.out.println("hi");
                    }
                    for(Exhibitions exhibition:exhibitionsList){ %>
                        <div class="col-6" style="padding: 0 0;" >
                            <div class="row exhibitionCard" style="margin: 10px;box-shadow: 9px 9px 22px -4px rgba(0,0,0,0.62);">
                                <div class="col-6" style="padding: 0 0">
                                    <img src="images/<%= exhibition.getImage()%>" class="cardImage" width="100%">
                                </div>
                                <div class="col-6">
                                    <div class="row">
                                        <%if(language.equals("uk")){%>
                                        <div class="col-12">
                                            <p><%= exhibition.getNameUA()%></p>
                                        </div>
                                        <%}else{%>
                                        <div class="col-12">
                                            <p><%= exhibition.getNameEN()%></p>
                                        </div>
                                        <%}%>
                                        <%if(Objects.equals(language, "uk")){%>
                                        <div class="col-12">
                                            <p><fmt:message key="exhibitions.card.theme"/>: <%= exhibition.getThemeUA()%></p>
                                        </div>
                                        <%}else{%>
                                        <div class="col-12">
                                            <p><fmt:message key="exhibitions.card.theme"/>: <%= exhibition.getThemaEN()%></p>
                                        </div>
                                        <%}%>
                                        <div class="col-12">
                                            <p><%=exhibition.getDate_from()+" - "+exhibition.getDate_to()%></p>
                                        </div>
                                        <div class="col-12">
                                            <% String time_from = exhibition.getWorking_time_from().toString()+" ";
                                                String time_to = exhibition.getWorking_time_to().toString()+" ";
                                            %>
                                            <p><fmt:message key="exhibitions.card.workingTime"/>: <%= time_from.replace(":00 ","")+" - "+time_to.replace(":00 ","")%></p>
                                        </div>
                                        <div class="col-12">
                                            <p><fmt:message key="exhibitions.card.halls"/>: <%= exhibitonHallsDAO.getHalls(exhibition.getId_exhibition())%></p>
                                        </div>
                                        <div class="col-12">
                                            <p><fmt:message key="exhibitions.card.Price"/>: <%=exhibition.getPrice() %></p>
                                        </div>
                                        <%  if(request.getSession().getAttribute("role")!=null&&request.getSession().getAttribute("role").equals("USER")){%>
                                        <div class="col-12 buttonForCard" >
                                            <a href="toAddInBasket?id=<%=exhibition.getId_exhibition()%>"><img src="asserts/img/basket.png" width="20px"></a>
                                        </div>
                                        <%}if(request.getSession().getAttribute("role")!=null&&request.getSession().getAttribute("role").equals("ADMINISTRATOR")){%>
                                        <div class="col-12" style="color: #bd2130">
                                            <p><fmt:message key="exhibitions.card.quality"/>: <%=ticketsDAO.numberOfVisitors(exhibition.getId_exhibition())%></p>
                                        </div>
                                        <div class="col-6 buttonForCardUpdate" style="border: #575757 2px solid;border-radius: 5px;font-family: 'Kelly Slab', cursive;text-align: center;background: #f1cb58;width: 40%;margin-left: auto;margin-right: auto" >
                                            <a href="toAddInBasket?id=<%=exhibition.getId_exhibition()%>"><img src="asserts/img/update.png" width="20px"></a>
                                        </div>
                                        <div class="col-6 buttonForCardDelete" style="border: #575757 2px solid;border-radius: 5px;font-family: 'Kelly Slab', cursive;text-align: center;background: #de3c3c; width: 40%;margin-left: auto;margin-right: auto">
                                            <a href="#" onclick="postToUrl('DeleteExhibition', {'id':'<%=exhibition.getId_exhibition()%>'}, 'POST');"><img src="asserts/img/delete.png" width="20px"></a>
                                        </div>
                                        <%}%>
                                    </div>
                                </div>
                            </div>
                        </div>
                <%}%>
            </div>
        </div>
        <div class="col-md-2 col-lg-2 col-xl-2" style="background: #A6A69F">

        </div>
    </div>
</div>
<script src="asserts/js/dropdown.js"></script>
<script src="asserts/js/bootstrap.js"></script>
<script>
    function postToUrl(path, params, method) {
        method = method || "post";

        var form = document.createElement("form");
        form.setAttribute("method", method);
        form.setAttribute("action", path);
        for(var key in params) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
        }

        document.body.appendChild(form);
        form.submit();
    }
</script>
</body>
</html>

