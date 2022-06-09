package com.martynyshyn.beautysalon.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * QueryCreator - create sql query by filter params
 *
 * @author N.Martynyshyn
 */
public final class QueryCreator {

    private QueryCreator() {

    }

    /**
     * Create query by param
     *
     * @param request         Http request
     * @param starterSqlQuery Initial sql query
     * @return finished query
     */
    public static String createSqlQuery(HttpServletRequest request, String starterSqlQuery) {

        StringBuilder stringBuilder = new StringBuilder(starterSqlQuery);

        String[] filerParams = request.getParameterMap().get("filer_param");

        //building query by filter parameters
        if (filerParams != null && filerParams.length > 0) {
            int numOfOr = filerParams.length - 1;
            int currentNumOfOr = 0;

            stringBuilder.append(" WHERE");

            for (String filterParam : filerParams) {
                stringBuilder.append(" speciality_id=").append(filterParam);
                if (numOfOr > 0 && currentNumOfOr < numOfOr) {
                    stringBuilder.append(" OR");
                    currentNumOfOr++;
                }
            }
            request.setAttribute("selected_filters", filerParams);
        }
        return setSortType(request, stringBuilder);
    }

    /**
     * Add to sql query data sort type.
     *
     * @param request HttpServletRequest.
     * @param stringBuilder Built query by builder.
     */
    private static String setSortType(HttpServletRequest request, StringBuilder stringBuilder) {

        String sortType = request.getParameter("sort_type");

        if (sortType == null) {
            sortType = "standard";
        }

        //added to sql finalQuery order by sortType
        switch (sortType) {
            case "by_name_up":
                stringBuilder.append(" ORDER BY firstName ASC");
                break;
            case "by_name_down":
                stringBuilder.append(" ORDER BY firstName DESC");
                break;
            case "by_rate_up":
                stringBuilder.append(" ORDER BY rate ASC");
                break;
            case "by_rate_down":
                stringBuilder.append(" ORDER BY rate DESC");
                break;
            default:
                System.out.println("Standard sort");
        }
        request.setAttribute("selected_sort", sortType);
        return stringBuilder.toString();
    }
}
