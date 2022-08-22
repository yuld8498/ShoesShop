package controler;

import DAO.*;
import Model.*;
import com.mysql.cj.Session;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    private String errors;
    private IUserDao userDao;
    private IProductDAO productDAO;
    private ICountryDAO countryDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
        countryDAO = new CountryDAO();
        userDao = new UserDao();
        if (this.getServletContext().getAttribute("countryList") == null) {
            this.getServletContext().setAttribute("countryList", countryDAO.selectAllCountry());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        if (!checkLogin(req, resp)) {
                resp.sendRedirect("/loginpage.jsp");
        } else {
            switch (action) {
                case "login":
                    loginUser(req, resp);
                    break;
                case "create":
                    formRegister(req, resp);
                    break;
                case "changepassword":
                    formChangepassword(req, resp);
                    break;
                case "logout":
                    logOut(req, resp);
                    break;
                default:
                    showInfo(req,resp);
//                    resp.sendRedirect("/usermanager.jsp");
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "login":
                loginUser(req, resp);
                break;
            case "create":
                insertUser(req, resp);
                break;
            case "changepassword":
                changePassword(req, resp);
                break;
            case "logout":
                logOut(req, resp);
                break;
            default:
                showInfo(req,resp);
//                req.getRequestDispatcher("/usermanager.jsp").forward(req,resp);
//                resp.sendRedirect("/usermanager.jsp");
        }
    }

    private void formChangepassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = null;
        String userName = null;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("password")) {
                password = cookie.getValue();
            }
            if (cookie.getName().equals("userName")) {
                userName = cookie.getValue();
            }
        }
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("password")) {
                password = cookie.getValue();
            }
            if (cookie.getName().equals("userName")) {
                userName = cookie.getValue();
            }
        }
            User user = userDao.login(userName, password);
            Country address = countryDAO.selectCountryByID(user.getAddress());
            String addressName = address.getCountryName();
            req.setAttribute("Address", addressName);
            req.setAttribute("User", user);

        req.setAttribute("password", password);
        req.setAttribute("userName", userName);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/usermanager.jsp");
        requestDispatcher.forward(req, resp);
    }

    public void showInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = null;
        String userName = null;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("password")) {
                password = cookie.getValue();
            }
            if (cookie.getName().equals("userName")) {
                userName = cookie.getValue();
            }
        }
        if (userName != null && password != null) {
            User user = userDao.login(userName, password);
            Country address = countryDAO.selectCountryByID(user.getAddress());
            String addressName = address.getCountryName();
            if (addressName != null) {
                req.setAttribute("Address", addressName);
            } else {
                req.setAttribute("Address", "No Location");
            }
            req.setAttribute("User", user);
            req.getRequestDispatcher("/usermanager.jsp").forward(req, resp);
        }else {
            req.getRequestDispatcher("/product").forward(req, resp);
        }
    }

    private void changePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = null;
        String userName = null;
        int errorsCount = 0;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("password")) {
                password = cookie.getValue();
            }
            if (cookie.getName().equals("userName")) {
                userName = cookie.getValue();
            }
        }

        String inputPW = req.getParameter("password");
        String newPW = req.getParameter("newpassword");
        String newPWreaplay = req.getParameter("newpasswordreaplay");
        if (!(password.equals(inputPW))) {
            System.out.println("fail1");
//            errors = "<ul><li>Current password is incorrect </li></ul>";
//            req.setAttribute("errorspw", errors);
//            req.setAttribute("password", password);
            errorsCount++;
//            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/usermanager.jsp");
//            requestDispatcher.forward(req, resp);
//            return;
        }
        if (!(newPWreaplay.equals(newPW))) {
            System.out.println("fail2");
//            String errors2 = "<ul><li>Re-enter password is not correct </li></ul>";
//            req.setAttribute("errorspw2", errors2);
//            req.setAttribute("password", password);
            errorsCount++;
//            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/usermanager.jsp");
//            requestDispatcher.forward(req, resp);
//            return;
        }
        User user = userDao.login(userName, password);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        if (!constraintViolations.isEmpty() || errorsCount != 0) {
            System.out.println("fail3");
            req.setAttribute("password", password);
            String errors1 = null;
            String errors2 = null;
            if (errorsCount != 0) {
                 errors1 = "<ul><li>Current password is incorrect </li></ul>";
                req.setAttribute("errorspw", errors1);
                 errors2 = "<ul><li>Re-enter password is not correct </li></ul>";
                req.setAttribute("errorspw2", errors2);
            }
            if (!constraintViolations.isEmpty()){
                req.setAttribute("errors", getErrorFromContraint(constraintViolations));
                req.setAttribute("user", user);
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/usermanager.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            try {
                userDao.updateUserPassword(userName, newPW);
                for (Cookie cookie : req.getCookies()){
                    if (cookie.getName().equals("password")){
                        cookie.setValue(newPW);
                        resp.addCookie(cookie);
                    }
                }
//                req.setAttribute("success", "Insert product is success.");
//                req.getRequestDispatcher("/users?action=login");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/users?action=manager");
                requestDispatcher.forward(req, resp);
            } catch (SQLException e) {
                req.setAttribute("errors", "Insert product is fail.");
                req.getRequestDispatcher("/users?action=login");
            }
        }
    }

    private void loginUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("userName");
        String pw = req.getParameter("password");
        if (userDao.login(username, pw) != null) {
            Cookie cookie = new Cookie("userName", username);
            Cookie cookie2 = new Cookie("password", pw);
            resp.addCookie(cookie);
            resp.addCookie(cookie2);
            cookie.setMaxAge(60*60);
            cookie2.setMaxAge(60*60);
            req.setAttribute("User", userDao.login(username, pw));
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/usermanager.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            req.setAttribute("errors","user name or password is wrong!");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/loginpage.jsp");
            requestDispatcher.forward(req, resp);
//            resp.sendRedirect("/users?action=login");
        }
    }

    private void formRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
        dispatcher.forward(request, response);
    }

    public void insertUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String regex = "\\d+";
        String errorsUserName = null;
        String errorsAge = null;
        String errorsEmail = null;
        String errorsPhone = null;
        int errorsCount = 0;
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        int age = 0;
        if (!(request.getParameter("age").matches(regex))) {
            errorsAge = "<li>Age is a number, please check again.</li>";
            errorsCount++;
        } else {
            age = Integer.parseInt(request.getParameter("age"));
        }
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        int country =0;
        try {
            country = Integer.parseInt(request.getParameter("country"));
        }catch (Exception e){
            country =29;
        }
        if (userDao.checkUserName(userName)) {
            errorsUserName = "<li>User Name is already exist</li>";
            errorsCount++;
        }
        if (userDao.checkEmail(email)) {
            errorsEmail = "<li>Email is already exist</li>";
            errorsCount++;
        }
        if (userDao.checkPhone(phone)) {
            errorsPhone = "<li>Phone Number is already exist</li>";
            errorsCount++;
        }
        User user = new User(userName, password, fullName, age, email, phone, country);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        if (!constraintViolations.isEmpty() || errorsCount != 0) {
            if (!constraintViolations.isEmpty()) {
                request.setAttribute("errors", getErrorFromContraint(constraintViolations));
                request.setAttribute("user", user);
            }
            request.setAttribute("errorsUserName", errorsUserName);
            request.setAttribute("errorsEmail", errorsEmail);
            request.setAttribute("errorsPhone", errorsPhone);
            request.setAttribute("errorsAge", errorsAge);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/register.jsp");
            requestDispatcher.forward(request, response);
        } else {
            userDao.insertUser(user);
            request.setAttribute("success", "Insert product is success.");
            request.getRequestDispatcher("/users?action=login");
        }
    }

    public void logOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("userName") || cookie.getName().equals("password")) {
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
        }
        resp.sendRedirect("/users?action=login");
    }

    private HashMap<String, List<String>> getErrorFromContraint(Set<ConstraintViolation<User>> constraintViolations) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (ConstraintViolation<User> c : constraintViolations) {
            if (hashMap.keySet().contains(c.getPropertyPath().toString())) {
                hashMap.get(c.getPropertyPath().toString()).add(c.getMessage());
            } else {
                List<String> listMessages = new ArrayList<>();
                listMessages.add(c.getMessage());
                hashMap.put(c.getPropertyPath().toString(), listMessages);
            }
        }
        return hashMap;
    }

    private boolean checkLogin(HttpServletRequest req, HttpServletResponse resp) {
        String username = null;
        String password = null;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("userName")) {
                username = cookie.getValue();
            }
            if (cookie.getName().equals("password")) {
                password = cookie.getValue();
            }
        }
        return (userDao.login(username, password) != null);
    }
}
