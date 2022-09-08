<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.exhibitions.db.entity.ExhibitionsBasket" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.epam.exhibitions.db.ExhibitonHallsDAOImpl" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.epam.exhibitions.db.ExhibitionsDAOImpl" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Exhibitions</title>
    <link rel="stylesheet" href="asserts/css/basket-style.css">
    <link rel="stylesheet" href="asserts/css/header-style.css">
    <link rel="stylesheet" href="asserts/css/bootstrap.css">
    <link href="asserts/icons/favicon.ico" rel="shortcut icon" type="image/x-icon" />
</head>
<body>
<%  String language;
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
        <div class="col-12 col-md-10 col-lg-8 col-xl-8" style="background: #F2EAE4;padding: 10px">
            <%if(session.getAttribute("listUsersBasket")!=null){
            List<ExhibitionsBasket> exhibitionsBasketListCheck=(List<ExhibitionsBasket>) session.getAttribute("listUsersBasket");
            List<ExhibitionsBasket> exhibitionsBasketListResult = new ArrayList<>();
            ExhibitionsDAOImpl exhibitionsDAO = ExhibitionsDAOImpl.getInstance();
            for(ExhibitionsBasket exhibitionsBasket:exhibitionsBasketListCheck){
                if(exhibitionsDAO.getExhibitionById(exhibitionsBasket.getExhibitions().getId_exhibition())!=null){
                    exhibitionsBasketListResult.add(exhibitionsBasket);
                }
            }
                session.setAttribute("listUsersBasket",exhibitionsBasketListResult);
            %>
            <%}if(session.getAttribute("listUsersBasket")==null){%>
            <div class="row">
                <div class="col-12" style="margin-top: 40px">
                    <h1 style="text-align: center;font-family: 'Comfortaa', cursive"><fmt:message key="basket.empty.title"/></h1>
                </div>
                <div class="col-12" style="margin-top: 40px">
                    <img src="asserts/img/basket.png" width="200px" style="display: block;margin-right: auto;margin-left: auto;margin-bottom: 200px">
                </div>
            </div>
            <% } %>
            <% if (session.getAttribute("listUsersBasket")!=null){
                List<ExhibitionsBasket> exhibitionsBasket = (List<ExhibitionsBasket>) session.getAttribute("listUsersBasket");
                ExhibitonHallsDAOImpl exhibitonHallsDAO = ExhibitonHallsDAOImpl.getInstance();
                for(ExhibitionsBasket exhibitionsBasket1:exhibitionsBasket){%>
                    <div class="row ticket" style="margin: 20px 20px 20px">
                        <div class="col-4 ticketImg">
                            <img src="images/<%=exhibitionsBasket1.getExhibitions().getImage()%>" width="100%" height="240px">
                        </div >
                        <div class="col-8">
                            <div class="row">
                                <%if(language.equals("uk")){%>
                                <div class="col-12">
                                    <p class="data"><%= exhibitionsBasket1.getExhibitions().getNameUA()%></p>
                                </div>
                                <%}else{%>
                                <div class="col-12">
                                    <p class="data"><%= exhibitionsBasket1.getExhibitions().getNameEN()%></p>
                                </div>
                                <%}%>
                                <%if(language.equals("uk")){%>
                                <div class="col-12">
                                    <p class="data"><%=exhibitionsBasket1.getExhibitions().getThemeUA()%></p>
                                </div>
                                <%}else{%>
                                <div class="col-12">
                                    <p class="data"><%=exhibitionsBasket1.getExhibitions().getThemaEN()%></p>
                                </div>
                                <%}%>
                                <div class="col-12">
                                    <p class="data"><%=exhibitionsBasket1.getExhibitions().getDate_from()+" - "+exhibitionsBasket1.getExhibitions().getDate_to()%></p>
                                </div>
                                <div class="col-12">
                                    <% String time_from = exhibitionsBasket1.getExhibitions().getWorking_time_from().toString()+" ";
                                        String time_to = exhibitionsBasket1.getExhibitions().getWorking_time_to().toString()+" ";
                                    %>
                                    <p class="data"><fmt:message key="basket.card.workingTime"/>: <%= time_from.replace(":00 ","")+" - "+time_to.replace(":00 ","")%></p>
                                </div>
                                <div class="col-12">
                                    <p class="data"><fmt:message key="basket.card.halls"/>: <%=exhibitonHallsDAO.getHalls(exhibitionsBasket1.getExhibitions().getId_exhibition())%></p>
                                </div>
                                <div class="col-12">
                                    <p class="data"><fmt:message key="basket.card.price"/>: <%=exhibitionsBasket1.getExhibitions().getPrice()%></p>
                                </div>
                                <div class="row">
                                    <div class="col-4">
                                        <div class="row">
                                            <div class="col-5">
                                                <a href="minusTicket?id=<%=exhibitionsBasket1.getExhibitions().getId_exhibition()%>"><img src="asserts/img/minus.png" class="image"></a>
                                            </div>
                                            <div class="col-2" style="padding: 0 0">
                                                <p style="text-align: center;font-family: 'Kelly Slab', cursive;margin-bottom: 0"><%=exhibitionsBasket1.getNumber()%></p>
                                            </div>
                                            <div class="col-5">
                                                <a href="plusTicket?id=<%=exhibitionsBasket1.getExhibitions().getId_exhibition()%>" style="text-align: center"><img src="asserts/img/plus.png" class="image"></a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-4">
                                        <p class="data"><fmt:message key="basket.card.toPay"/>: <%=exhibitionsBasket1.getExhibitions().getPrice().multiply(new BigDecimal(exhibitionsBasket1.getNumber()))%></p>
                                    </div>
                                    <div class="col-4">
                                        <a href="toClean?id=<%=exhibitionsBasket1.getExhibitions().getId_exhibition()%>" style="text-decoration: none;"><p class="toClean"><fmt:message key="basket.card.clean"/> <img src="asserts/img/clean.png" width="20px"></p></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                <%}%>
                <div class="row" style="margin-top: 30px">
                    <div class="col-5">
                        <% BigDecimal number = new BigDecimal(0);
                            for(ExhibitionsBasket exhibitionsBasket1:exhibitionsBasket){
                                number = number.add(new BigDecimal(exhibitionsBasket1.getNumber()).multiply(exhibitionsBasket1.getExhibitions().getPrice()));
                            }
                            session.setAttribute("theSumInBasket",number);
                        %>
                        <h5 style="font-family: 'Comfortaa', cursive;text-align: center"><fmt:message key="basket.payment"/>: <%= number%> $</h5>
                    </div>
                    <div class="col-3">
                        <a href="#"  onclick="postToUrl('Payment', {'username':'<%=session.getAttribute("username")%>'}, 'POST');" style="text-decoration: none;"><p class="toClean" style="background: white"><fmt:message key="basket.button.toPay"/></p></a>
                    </div>
                    <div class="col-4"></div>
                </div>
            <div class="row">
                <div class="col-12">
                    <% String payment = (String)request.getSession().getAttribute("balanceError"); %>
                    <% if (payment==null){request.getSession().setAttribute("balanceError", " ");}%>
                    <a href="wallet.jsp" id="paymentError" >
                        <p> <%=request.getSession().getAttribute("balanceError")%>
                            <%request.getSession().setAttribute("balanceError"," ");%></p>
                    </a>
                </div>
            </div>
            <%}%>
        </div>
        <div class="col-md-1 col-lg-2 col-xl-2" style="background: #A6A69F"></div>
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


