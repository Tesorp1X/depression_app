package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import dbService.DBService;
import dbService.NoSuchUserException;
import dbService.dataSets.AdminDataSet;
import dbService.dataSets.TesterDataSet;

import org.hibernate.HibernateException;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

/**
 * @author Tesorp1X
 * Url: /info
 * Param: <action = {add, getList, rm} & whom = {admins, testrs}> [& t_id = telegram_id ]
 */
public class ServletUtilityInfo extends HttpServlet {

    private final DBService dbService;

    public ServletUtilityInfo(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String whom = request.getParameter("whom");

        if (action == null || whom == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        List<?> list = Collections.emptyList();


        switch (action) {
            case "getList":
                if (whom.equals("admins")) {

                     list = dbService.getListOfEntities(AdminDataSet.class);

                } else if (whom.equals("testers")) {

                    list = dbService.getListOfEntities(TesterDataSet.class);
                    response.setStatus(HttpServletResponse.SC_OK);
                }

                break;

            case "rm":
                try {
                    String telegramId = request.getParameter("t_id");

                    if (whom.equals("admins")) {

                        list = dbService.removeEntity(AdminDataSet.class, "telegram_id", telegramId);
                        response.setStatus(HttpServletResponse.SC_OK);

                    } else if (whom.equals("testers")) {

                        list = dbService.removeEntity(TesterDataSet.class, "telegram_id", telegramId);
                        response.setStatus(HttpServletResponse.SC_OK);
                    }
                } catch (NoResultException e) {

                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.setContentType("text/plain;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.print("{ ERROR: No such user. }");
                    out.flush();

                } catch (HibernateException e) {

                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.setContentType("text/plain;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.print("{ ERROR: Server error. }");
                    out.flush();
                }

                break;

            case "add":
                try {
                    if (whom.equals("admins")) {

                        String adminsTelegramId = request.getParameter("t_id");
                        list = dbService.addAdmin(adminsTelegramId);
                        response.setStatus(HttpServletResponse.SC_OK);

                    } else if (whom.equals("testers")) {

                        String testersTelegramId = request.getParameter("t_id");
                        list = dbService.addTester(testersTelegramId);
                        response.setStatus(HttpServletResponse.SC_OK);
                    }

                } catch (NoSuchUserException e) {

                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.setContentType("text/plain;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.print("{ ERROR: No such user. }");
                    out.flush();

                } catch (HibernateException e) {

                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.setContentType("text/plain;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.print("{ ERROR: Server error. }");
                    out.flush();
                }

                break;
        }
        //TODO: implement rmAll option

        ObjectMapper objectMapper = new ObjectMapper();
        String json_string = objectMapper.writeValueAsString(list);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=utf-8");

        PrintWriter out = response.getWriter();
        out.print(json_string);
        out.flush();


    }
}
