package com.martynyshyn.beautysalon.service;

/**
 * Class whit constant path and redirects URL.
 *
 * @author N.Martynyshyn
 */

public final class Url {
    private Url() {
        //It is necessary that there was no possibility to create an instance of the class
    }

    //Path to jsp page
    public static final String ALL_ORDER_PAGE = "/WEB-INF/view/admin/usersOrderPage.jsp";
    public static final String ORDER_REQUEST = "/WEB-INF/view/admin/orderRequestPage.jsp";

    public static final String MASTER_TIME_TABLE_PAGE = "/WEB-INF/view/master/masterPage.jsp";

    public static final String MY_ORDERS_LIST_PAGE = "/WEB-INF/view/client/myOrderList.jsp";
    public static final String ORDERS_LIST_PAGE = "/WEB-INF/view/client/regFormToOrder.jsp";

    public static final String MASTER_LIST_PAGE = "/WEB-INF/view/common/masterList.jsp";
    public static final String SERVICE_LIST_PAGE = "/WEB-INF/view/common/serviceList.jsp";

    public static final String ERROR_PAGE = "/WEB-INF/view/error/404.jsp";

    public static final String REGISTRATION_PAGE = "/BeautySalon/registered.jsp";
    public static final String LOGIN_PAGE = "/BeautySalon/index.jsp";

    //REDIRECTS url, required for PRG
    public static final String REDIRECT_MASTER_LIST_PAGE = "/BeautySalon/main-servlet/main_page?action" +
            "=open_master_list";
    public static final String REDIRECT_MASTER_TIMETABLE_PAGE = "/BeautySalon/main-servlet/main_page?action=show_timetable";
    public static final String REDIRECT_REG_TO_ORDER_FORM = "/BeautySalon/main-servlet/main_page?action=open_order_reg_form";
    public static final String REDIRECT_REQUEST_FORM = "/BeautySalon/main-servlet/main_page?action=check_request";
    public static final String REDIRECT_ALL_ORDER_PAGE = "/BeautySalon/main-servlet/main_page?action=check_all_order";
}
