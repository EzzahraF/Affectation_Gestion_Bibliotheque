package com.ensa.servlets;

import com.ensa.Dao.UserDao;
import com.ensa.Dao.DocumentDAO;
import com.ensa.entities.User;
import com.ensa.entities.Document;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/borrow")
public class BorrowServlet extends HttpServlet {
    private UserDao userDAO = new UserDao();
    private DocumentDAO documentDAO = new DocumentDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        BorrowRequest request = gson.fromJson(req.getReader(), BorrowRequest.class);

        User user = userDAO.findUserById(request.getUserId());
        Document document = documentDAO.findDocumentById(request.getDocumentId());

        if (user != null && document != null) {
            if (user.getDocuments().contains(document)) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                resp.getWriter().write("{\"error\": \"User already borrowed this document\"}");
                return;
            }

            user.getDocuments().add(document);
            userDAO.updateUser(user);

            resp.getWriter().write("{\"message\": \"Document borrowed successfully\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid user or document ID\"}");
        }
    }

    private static class BorrowRequest {
        private Long userId;
        private Long documentId;

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public Long getDocumentId() { return documentId; }
        public void setDocumentId(Long documentId) { this.documentId = documentId; }
    }
}
