package lk.jiat.bcd.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.bcd.model.User;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/signup")
public class SignUpController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String mobile = req.getParameter("mobile");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // 1. ServletContext eka gannawa server-wide data share karanna
        ServletContext context = getServletContext();

        // 2. Context eke "userList" kiyala attribute ekak thiyanawada balanawa.
        // Nathi nam aluth list ekak hadala context ekata dapanawa.
        ArrayList<User> userList;
        if (context.getAttribute("userList") == null) {
            userList = new ArrayList<>();
            context.setAttribute("userList", userList);
        } else {
            userList = (ArrayList<User>) context.getAttribute("userList");
        }

        if (!name.isEmpty() && !mobile.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

            for (User user : userList) {
                if (user.getEmail().equals(email)) {
                    resp.getWriter().write("Error: " + email + " is already in use!");
                    return;
                }
            }

            User newUser = new User(name, mobile, email, password);
            userList.add(newUser);

            resp.getWriter().write("Registration Successful for " + name);

        } else {
            resp.getWriter().write("Error: All fields are required!");
        }
    }
}