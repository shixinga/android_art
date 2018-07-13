package com.mooc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class UserServlet
 */
@MultipartConfig
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath() + "\n");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String username = request.getParameter("username");
		Part part = request.getPart("filename");
		PrintWriter pw = response.getWriter();
		if(part != null) { // 需要在servlet上加@MultipartConfig
			
			pw.append("contenttype=" + part.getContentType() + ",\n size=" + part.getSize()
				+ ", file name=" + part.getName() + ",username=" + username);
			part.write("E:\\csx\\github\\android_art\\fw\\network\\server\\src\\fuck2.jpg"); //需要指定一个文件名！！
		} else {
			pw.append("whjat!!" + request.getParts().size());
		}
	}

}
