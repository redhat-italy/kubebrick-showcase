package com.kubebrick.actions;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.kubebrick.model.PiecesLot;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StorageAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        String success = request.getParameter("success");

        List<PiecesLot> piecesLots = getPiecesLots();
        request.setAttribute("piecesLots", piecesLots);
        return mapping.findForward("success");
    }

    private List<PiecesLot> getPiecesLots() throws Exception {
        List<PiecesLot> piecesLots = new ArrayList<PiecesLot>();

        DataSource ds = getDataSource();

        Connection conn = ds.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from packagelog");
            while (rs.next()) {
                PiecesLot book = new PiecesLot();
                book.setColor(rs.getString("color"));
                book.setCreationTimestamp(rs.getString("creationtimestamp"));
                book.setPieces(rs.getInt("pieces"));
                piecesLots.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, stmt, rs);
        }

        return piecesLots;
    }

    private void closeAll(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private DataSource getDataSource() throws Exception {
        InitialContext cxt = new InitialContext();
        if (cxt == null) {
            throw new Exception("Uh oh -- no context!");
        }

        DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/kubebrick");

        if (ds == null) {
            throw new Exception("Data source not found!");
        }
        return ds;
    }

}
