package controler;

import DAO.*;
import Model.Order;
import Model.Product;
import Model.User;
import com.mysql.cj.Session;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.validation.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "userServlet", urlPatterns = "/product")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class ProductServlet extends HttpServlet {
    List<Order> orderList;
    int recordsPerPage = 3;
    //    private static final long serialVersionUID = 1L;
    private String errors;
    private ProductDAO productDAO;
    private TypeDAO typeDAO;
    private IUserDao userDao;
    private IOrderDao orderDao;
    private HttpSession session;
    private ICountryDAO countryDAO;
    private ITypeOrderDao typeOrderDao;

    public void init() {
        orderList = new ArrayList<>();
        orderDao = new OrderDao();
        userDao = new UserDao();
        productDAO = new ProductDAO();
        typeDAO = new TypeDAO();
        countryDAO = new CountryDAO();
        typeOrderDao = new TypeOrderDao();
        if (this.getServletContext().getAttribute("listType") == null) {
            this.getServletContext().setAttribute("listType", typeDAO.selectAllType());
        }
        if (this.getServletContext().getAttribute("listProduct") == null) {
            this.getServletContext().setAttribute("listProduct", productDAO.selectAllProduct());
        }
        if (this.getServletContext().getAttribute("countryList") == null) {
            this.getServletContext().setAttribute("countryList", countryDAO.selectAllCountry());
        }
        if (this.getServletContext().getAttribute("typeOrderList") == null) {
            this.getServletContext().setAttribute("typeOrderList", typeOrderDao.selectAllType());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showNewForm(req, resp);
                break;
            case "update":
                try {
                    showEditForm(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "delete":
                deleteProduct(req, resp);
                break;
            case "filter":
                listProductFilter(req, resp);
                break;
            case "order":
                orderItems(req, resp);
                break;
            case "orderList":
                listOrder(req, resp);
                break;
            case "confirmorder":
                checkConfirm(req, resp);
                break;
            case "managerorder":
                managerOrder(req, resp);
                break;
            default:
                listProductPagging(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    inserProduct(req, resp);
                    break;
                case "update":
                    updateProduct(req, resp);
                    break;
                case "filter":
                    listProductFilter(req, resp);
                    break;
                case "confirmorder":
                    checkConfirm(req, resp);
                    break;
                case "managerorder":
                    managerOrder(req, resp);
                    break;
                default:
                    listProductPagging(req, resp);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }

    }

    private void managerOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println(req.getParameter("status"));
        System.out.println(req.getParameter("checked"));
        resp.sendRedirect("/product");
    }

    private void listProductFilter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int typeID = -1;
        int page = 1;
        if (request.getParameter("pages") != null) {
            page = Integer.parseInt(request.getParameter("pages"));
        }
        if (page <= 0) {
            page = 1;
        }
        if (request.getParameter("typeID") != null) {
            typeID = Integer.parseInt(request.getParameter("typeID"));
        }
        if (typeID == -1) {
            listProductPagging(request, response);
        } else {
            List<Product> productList = productDAO.selectProductPaggingFilter((page - 1) * recordsPerPage, recordsPerPage, typeID);
//            List<Product> listProduct = productDAO.selectProductFilter(typeID);
            int noOfRecord = productDAO.getNoOfRecord();
            int noOfPage = (int) Math.ceil((noOfRecord * 1.08) / recordsPerPage);
            String userName = null;
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("userName")) {
                    userName = cookie.getValue();
                }
            }
            request.setAttribute("typeUser", userDao.typeUser(userName));
            request.setAttribute("filter", "filter");
            request.setAttribute("typeProduct", typeID);
            request.setAttribute("logincheck", userName);
            request.setAttribute("listProduct", productList);
            request.setAttribute("noOfPage", noOfPage);
            request.setAttribute("currentPages", page);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/view/productlist.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private void checkConfirm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = null;
        String password = null;
        System.out.println(request.getParameter("checked"));
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equalsIgnoreCase("userName")) {
                userName = cookie.getValue();
            }
            if (cookie.getName().equalsIgnoreCase("password")) {
                password = cookie.getValue();
            }
        }
        if (checkLogin(request, response)) {
            request.setAttribute("typeUser", userDao.typeUser(userName));
            orderDao.confirmOrder(userName);
            request.setAttribute("success", "order is success!");
            response.sendRedirect("/product");
        } else {
            response.sendRedirect("/orderform.jsp");
        }
    }

    private void confirmOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (Order order : orderList) {
            order.setUserName(request.getParameter("fullName"));
            order.setPhoneNumber(request.getParameter("phone"));
            order.setEmail(request.getParameter("email"));
            order.setCity(Integer.parseInt(request.getParameter("city")));
            orderDao.InsertOrder(order);
            orderDao.confirmOrder(request.getParameter("fullName"));
        }
        request.setAttribute("success", "order is success!");
        response.sendRedirect("/product");
    }

    //order details, check user or not user
    private void orderItems(HttpServletRequest request, HttpServletResponse response) throws IOException {
        session = request.getSession();
        Order order = new Order();
        String userName = null;
        String password = null;
        int filter = 0;
        int id = 0;
        System.out.println(request.getAttribute("checked"));
        String path = null;
        if (request.getParameter("filter") != null) {
            filter = Integer.parseInt(request.getParameter("filter"));
        }
        if (request.getParameter("productID") != null) {
            id = (Integer.parseInt(request.getParameter("productID")));
        }
        Product product = productDAO.selectProductByID(id);
        /*đã có được typeID và product iD, làm như list filter
         * */
        int countOfPage = 0;
        if (filter != 0) {
            countOfPage = (int) Math.ceil(checkIndexFilter(id, filter) * 1.08 / recordsPerPage);
            path = "/product?action=filter&typeID=" + filter + "&pages=" + countOfPage;
            System.out.println("filter");
        } else {
            countOfPage = (int) Math.ceil((checkIndex(id)) * 1.08 / recordsPerPage);
            path = "/product?page=" + countOfPage;
            System.out.println("list");
        }
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equalsIgnoreCase("userName")) {
                userName = cookie.getValue();
            }
            if (cookie.getName().equalsIgnoreCase("password")) {
                password = cookie.getValue();
            }
        }
        User user = userDao.login(userName, password);
        if (user != null) {
            String type = userDao.typeUser(userName);
            order = new Order(userName, id, 1, user.getEmail()
                    , user.getPhoneNumber(), product.getPrice(), user.getAddress());
            orderDao.InsertOrder(order);
        } else {
            int count = 0;
            order.setProductID(id);
            order.setProductQuaility(1);
            for (Order orderInList : orderList) {
                if (orderInList.getProductID() == order.getProductID()) {
                    int number = orderInList.getProductQuaility();
                    orderInList.setProductQuaility(number + 1);
                    count++;
                }
            }
            if (count == 0) {
                orderList.add(order);
            }
        }
        request.setAttribute("typeUser", userDao.typeUser(userName));
        response.sendRedirect(path);
    }

    private void listOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = null;
        String password = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equalsIgnoreCase("username")) {
                username = cookie.getValue();
            }
            if (cookie.getName().equalsIgnoreCase("password")) {
                password = cookie.getValue();
            }
        }
        request.setAttribute("typeUser", userDao.typeUser(username));
        User user = userDao.login(username, password);
        String type = null;
        if (user != null) {
            type = userDao.typeUser(username);
        } else {
            type = "";
        }
        if (type.equalsIgnoreCase("admin")) {
            request.setAttribute("orderList", orderDao.selectAllOrder());
        } else {
            if (user != null) {
                request.setAttribute("orderList", orderDao.showOrderList(username));
            } else {
                request.setAttribute("orderList", orderList);
            }
        }
        request.setAttribute("type", type);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/orderList.jsp");
        requestDispatcher.forward(request, response);
    }

    private void listProductPagging(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String regex = "\\d+";
        int page = 1;
        if (request.getParameter("page") != null) {
            if (request.getParameter("page").matches(regex)) {
                page = Integer.parseInt(request.getParameter("page"));
            }
        }
        if (page <= 0) {
            page = 1;
        }
        if (page > (productDAO.getNoOfRecord() / recordsPerPage) + 1) {
            response.sendRedirect("/product?action=list");
        } else {
            List<Product> listProduct = productDAO.selectProductPagging((page - 1) * recordsPerPage, recordsPerPage);
            int noOfRecord = productDAO.getNoOfRecord();
            int noOfPage = (int) Math.ceil((noOfRecord * 1.0) / recordsPerPage);
            String userName = null;
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("userName")) {
                    userName = cookie.getValue();
                }
            }
            request.setAttribute("typeUser", userDao.typeUser(userName));
            request.setAttribute("logincheck", userName);
            request.setAttribute("listProduct", listProduct);
            request.setAttribute("noOfPage", noOfPage);
            request.setAttribute("currentPage", page);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/view/productlist.jsp");
            requestDispatcher.forward(request, response);
        }

    }

    private void listProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Product> listProduct = productDAO.selectAllProduct();
        request.setAttribute("listProduct", listProduct);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/view/productlist.jsp");
        requestDispatcher.forward(request, response);
    }


    private void inserProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException, NumberFormatException {
        Product product = getProduct(request, response);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
        if (!constraintViolations.isEmpty()) {
            request.setAttribute("errors", getErrorFromContraint(constraintViolations));
            request.setAttribute("product", product);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/view/createproduct.jsp");
            requestDispatcher.forward(request, response);
        } else {
            if (productDAO.selectProductByPrice(product.getProductName(), product.getPrice(), product.getTypeID())) {
                String errorsprice = "<ul>" +
                        "<li>Product already exist</li>" +
                        "<li> 1 Product can't have the same name,price and type</li>" +
                        "</ul>";
                request.setAttribute("errorsprice", errorsprice);
                request.setAttribute("product", product);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/view/createproduct.jsp");
                requestDispatcher.forward(request, response);
            } else {
                List<Product> list = productDAO.selectAllProduct();
                int noOfRecord = list.size();
                int noOfPage = (int) Math.ceil((noOfRecord * 1.08) / recordsPerPage);
                String path;
                if (noOfPage == 0) {
                    path = "/product?page=1";
                } else {
                    path = "/product?page=" + noOfPage;
                }
                productDAO.insertProduct(product);
                //request.setAttribute("success", "Insert product is success.");
                response.sendRedirect(path);
            }
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("productID"));
        productDAO.deleteProductByID(id);
        request.setAttribute("delete", "you wan't delete");
        RequestDispatcher dispatcher = request.getRequestDispatcher("product?action=users");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        request.setAttribute("action", action);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/createproduct.jsp");
        dispatcher.forward(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productID = Integer.parseInt(request.getParameter("productID"));
        Product product = getProduct(request, response);
        product.setProductID(productID);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
        if (!constraintViolations.isEmpty()) {
            request.setAttribute("errors", getErrorFromContraint(constraintViolations));
            request.setAttribute("product", product);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/view/createproduct.jsp");
            requestDispatcher.forward(request, response);
        } else {
            productDAO.updateProductByID(product);
            request.setAttribute("success", "Insert product is success.");
            request.getRequestDispatcher("/product?action=list").forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String regex = "\\d+";
        String action = "edit'";
        request.setAttribute("action", action);
        int id = 0;
        if (request.getParameter("productID").matches(regex)) {
            id = Integer.parseInt(request.getParameter("productID"));
            Product product = productDAO.selectProductByID(id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/createproduct.jsp");
            request.setAttribute("product", product);
            typeDAO = new TypeDAO();
            //request.setAttribute("listType",typeDAO.selectAllType() );

            //getServletContext().setAttribute("listType",typeDAO.selectAllType() );
            dispatcher.forward(request, response);
        } else {
            errors = "<ul><li>" +
                    "price is a number, please check again" +
                    "</li></ul>";
            response.sendRedirect("/product?action=list");
        }

    }

    private HashMap<String, List<String>> getErrorFromContraint(Set<ConstraintViolation<Product>> constraintViolations) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (ConstraintViolation<Product> c : constraintViolations) {
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

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    private Product getProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String regex = "\\d+";
        String regexF = "^[0-9]?([0-9]*[.])?[0-9]+";
//        int productID = Integer.parseInt(request.getParameter("productID"));
        String productName = request.getParameter("productName");
        String productDescription = request.getParameter("productDescription");
        double price = 0;
        if (request.getParameter("price").matches(regexF)) {
            price = Double.parseDouble(request.getParameter("price"));
        } else {
            errors = "<ul><li>" +
                    "price is a number, please check again" +
                    "</li></ul>";
        }
        int quaility = 0;
        if ((request.getParameter("quaility").matches(regex))) {
            quaility = Integer.parseInt(request.getParameter("quaility"));
        } else {
            errors = "<ul><li>" +
                    "Quantity is a number, please check again" +
                    "</li></ul>";
        }
        int typeID = Integer.parseInt(request.getParameter("typeID"));
        Product product = new Product(productName, productDescription, price, quaility, typeID);
        //star read file
        for (Part part : request.getParts()) {
            if (part.getName().equals("img")) {
                String file = extractFileName(part);
                if (!file.equals("")) {
                    file = new File(file).getName();
                    part.write("\\D:\\Module 3\\Module_3\\CaseStudyModule3\\WebCase\\src\\main\\webapp\\images\\" + file);
                    String serveletPath = this.getServletContext().getRealPath("/") + "images/" + file;
                    part.write(serveletPath);
                    product.setFileName(file);
                } else {
                    product.setFileName("boots.png");
                }
            }
        }
        //end read file
        return product;
    }

    private int checkIndex(int productID) {
        List<Product> list = productDAO.selectAllProduct();
        int index = 0;
        for (Product product : list) {
            if (product.getProductID() == productID) {
                return index;
            }
            index++;
        }
        return 0;
    }

    private int checkIndexFilter(int productID, int TypeID) {
        List<Product> list = productDAO.selectProductFilter(TypeID);
        int index = 0;
        for (Product product : list) {
            System.out.println(product.getProductID());
            if (product.getProductID() == productID) {
                return index;
            }
            index++;
        }
        return 0;
    }
}
