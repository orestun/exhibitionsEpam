<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Exhibitions</title>
    <link rel="stylesheet" href="asserts/css/wallet-style.css">
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
    <form action="RefillBalanceServlet" method="post">
        <div class="row">
            <div class="col-1 col-md-3 col-lg-4 col-xl-4" style="background: #A6A69F">
                <div class="row">
                    <div class="col-6"></div>
                    <div class="col-6">
                        <div class="row" style="background:#F2EAE4;height: 689px"></div>
                    </div>
                </div>
            </div>
            <div style="padding: 0;background: #F2EAE4" class="col-10 col-md-6 col-lg-4 col-xl-4" >
                <div class="card1">
                    <div class="space">
                        <img style=" height: 40px" src="asserts/img/mastercard.png">
                    </div>
                    <p class="userDatails" style="font-family: 'Kelly Slab', cursive;font-size: 18px;color: gold;margin-left: 5%"><%=request.getSession().getAttribute("firthName")%> <%= request.getSession().getAttribute("secondName")%></p>
                        <div class="row">
                            <div class="col-12">
                                <input class="card-number" name="numberOfCard" type="text" pattern="[0-9]*" placeholder="0000 0000 0000 0000" maxlength="16" minlength="16">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <p style="font-family: 'Kelly Slab', cursive;font-size: 12px;margin-left: 90%">valid thru</p>
                            </div>
                            <div class="col-6">
                                <input class="dateValid" maxlength="5" minlength="5" type="text" placeholder="00/00">
                            </div>
                        </div>
                </div>
                <div class="paid">
                    <div class="row">
                        <div class="col-6">
                            <p style="font-family: 'Comfortaa', cursive"><fmt:message key="wallet.sumOfPayment"/>:</p>
                        </div>
                        <div class="col-6">
                            <input class="paidForm" type="text" name="paid" placeholder="00.00"
                                   oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" REQUIRED>
                        </div>
                        <div class="col-12">
                            <p style="font-family: 'Comfortaa', cursive"><fmt:message key="wallet.balance"/>: <%=request.getSession().getAttribute("wallet")%> $</p>
                        </div>
                        <div class="col-12">
                            <input id="button" type="submit" value="<fmt:message key="wallet.button"/>" >
                        </div>
                    </div>
                </div>
                <div class="row" style="height: 300px"></div>
            </div>
            <div class="col-1 col-md-3 col-lg-4 col-xl-4" style="background: #A6A69F">
                <div class="row">
                    <div class="col-6">
                        <div class="row" style="background:#F2EAE4;height: 689px"></div>
                    </div>
                    <div class="col-6"></div>
                </div>
            </div>
        </div>
    </form>
</div>
<script src="asserts/js/dropdown.js"></script>
<script src="asserts/js/bootstrap.js"></script>
<script>
</script>
</body>
</html>


