package com.ensa.servlets;

import com.ensa.Dao.DocumentDAO;
import com.ensa.entities.Document;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/documents")
public class DocumentServlet extends HttpServlet {
    private DocumentDAO documentDAO = new DocumentDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String documentId = req.getParameter("id");

        if (documentId != null) {
            // Rechercher un document par ID
            try {
                Long id = Long.parseLong(documentId);
                Document document = documentDAO.findDocumentById(id);

                if (document != null) {
                    resp.getWriter().write(gson.toJson(document));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\": \"Document not found\"}");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Invalid document ID\"}");
            }
        } else {
            // Récupérer tous les documents
            List<Document> documents = documentDAO.findAllDocuments();
            resp.getWriter().write(gson.toJson(documents));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Document document = gson.fromJson(req.getReader(), Document.class);

        if (document.getTitle() == null || document.getTitle().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Title is required\"}");
            return;
        }

        documentDAO.addDocument(document);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write("{\"message\": \"Document added successfully\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String documentId = req.getParameter("id");

        if (documentId == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Document ID is required\"}");
            return;
        }

        try {
            Long id = Long.parseLong(documentId);
            boolean deleted = documentDAO.deleteDocument(id);

            if (deleted) {
                resp.getWriter().write("{\"message\": \"Document deleted successfully\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Document not found\"}");
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid document ID\"}");
        }
    }
}
