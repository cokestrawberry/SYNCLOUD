package com.example.acdc.domain;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ScriptUnit {

    public static void init(HttpServletResponse response) {
        response.setContentType("text/html; charset=euc-kr");
        response.setCharacterEncoding("euc-kr");
    }
    public static void alert(HttpServletResponse response, String alertText) throws IOException {
        init(response);
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + alertText +"'); history.go(-1); </script>");
        out.flush();
    }

    public static void alert_clear(HttpServletResponse response, String pagePath, String alertText) throws IOException {
        init(response);
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + alertText +"'); location.href='" + pagePath +"' ;</script>");
        out.flush();
    }
}
