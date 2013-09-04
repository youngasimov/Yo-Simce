/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamer8.yosimce.server;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;
import gwtupload.shared.UConsts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.Session;

import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.utils.StringUtils;

/**
 * 
 * @author jorge
 */
public class UploadServlet extends UploadAction {

	private static final long serialVersionUID = 1L;

	/**
	 * Override executeAction to save the received files in a custom place and
	 * delete this items from session.
	 */
	@Override
	public String executeAction(HttpServletRequest request,
			List<FileItem> sessionFiles) throws UploadActionException {
		String response = "";
		int cont = 0;
		for (FileItem item : sessionFiles) {
			if (false == item.isFormField()) {
				cont++;
				try {
					// / Create a new file based on the remote file name in the
					// client
					// String saveName =
					// item.getName().replaceAll("[\\\\/><\\|\\s\"'{}()\\[\\]]+",
					// "_");
					// File file =new File("/tmp/" + saveName);

					// / Create a temporary file placed in /tmp (only works in
					// unix)
					// File file = File.createTempFile("upload-", ".bin", new
					// File("/tmp"));

					File tmpDir = CustomRemoteServiceServlet.getUploadDir();

					String extension = StringUtils.getExtension(item.getName());

					// / Create a temporary file placed in the default system
					// temp folder
					File file = File.createTempFile(
							item.getName().replaceAll(extension, ""),
							extension, tmpDir);
					item.write(file);

					// / Save a list with the received files
					setReceivedFile(request, item.getFieldName(), file);
					setReceivedFileType(request, item.getFieldName(),
							item.getContentType());
					setReceivedFileName(request, item.getFieldName(),
							item.getName());

					// / Compose a xml message with the full file information
					response += "<file-" + cont + "-field>"
							+ item.getFieldName() + "</file-" + cont
							+ "-field>\n";
					response += "<file-" + cont + "-name>" + item.getName()
							+ "</file-" + cont + "-name>\n";
					response += "<file-" + cont + "-size>" + item.getSize()
							+ "</file-" + cont + "-size>\n";
					response += "<file-" + cont + "-type>"
							+ item.getContentType() + "</file-" + cont
							+ "type>\n";
				} catch (Exception e) {
					throw new UploadActionException(e);
				}
			}
		}

		// / Remove files from session because we have a copy of them
		removeSessionFileItems(request);

		// / Send information of the received files to the client.
		return "<response>\n" + response + "</response>\n";
	}

	/**
	 * Get the content of an uploaded file.
	 */
	@Override
	public void getUploadedFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fieldName = request.getParameter(UConsts.PARAM_SHOW);
		File f = getReceivedFile(request, fieldName);
		if (f != null) {
			response.setContentType(getReceivedFileType(request, fieldName));
			FileInputStream is = new FileInputStream(f);
			copyFromInputStreamToOutputStream(is, response.getOutputStream());
		} else {
			renderXmlResponse(request, response, XML_ERROR_ITEM_NOT_FOUND);
		}
	}

	/**
	 * Remove a file when the user sends a delete request.
	 */
	@Override
	public void removeItem(HttpServletRequest request, String fieldName)
			throws UploadActionException {
		File file = getReceivedFile(request, fieldName);
		removeReceivedFile(request, fieldName);
		// removeReceivedFileName(request, fieldName);
		removeReceivedFileType(request, fieldName);
		if (file != null) {
			file.delete();
		}
	}

	private File getReceivedFile(HttpServletRequest request, String fieldName) {
		Hashtable<String, File> files = (Hashtable<String, File>) request
				.getSession().getAttribute(CustomRemoteServiceServlet.FILES);
		return (files != null) ? files.get(fieldName) : null;
	}

	private String getReceivedFileType(HttpServletRequest request,
			String fieldName) {
		Hashtable<String, String> types = (Hashtable<String, String>) request
				.getSession().getAttribute(
						CustomRemoteServiceServlet.FILE_TYPES);
		return (types != null) ? types.get(fieldName) : null;
	}

	private void setReceivedFile(HttpServletRequest request, String fieldName,
			File file) {
		Hashtable<String, File> files = (Hashtable<String, File>) request
				.getSession().getAttribute(CustomRemoteServiceServlet.FILES);
		if (files == null) {
			files = new Hashtable<String, File>();
		}
		files.put(fieldName, file);
		request.getSession().setAttribute(CustomRemoteServiceServlet.FILES,
				files);
	}

	private void setReceivedFileType(HttpServletRequest request,
			String fieldName, String type) {
		Hashtable<String, String> types = (Hashtable<String, String>) request
				.getSession().getAttribute(
						CustomRemoteServiceServlet.FILE_TYPES);
		if (types == null) {
			types = new Hashtable<String, String>();
		}
		types.put(fieldName, type);
		request.getSession().setAttribute(
				CustomRemoteServiceServlet.FILE_TYPES, types);
	}

	private void setReceivedFileName(HttpServletRequest request,
			String fieldName, String name) {
		Hashtable<String, String> names = (Hashtable<String, String>) request
				.getSession().getAttribute(
						CustomRemoteServiceServlet.FILE_NAMES);
		if (names == null) {
			names = new Hashtable<String, String>();
		}
		names.put(name, fieldName);
		request.getSession().setAttribute(
				CustomRemoteServiceServlet.FILE_NAMES, names);
	}

	private void removeReceivedFile(HttpServletRequest request, String fieldName) {
		Hashtable<String, File> files = (Hashtable<String, File>) request
				.getSession().getAttribute(CustomRemoteServiceServlet.FILES);
		if (files != null) {
			files.remove(fieldName);
			request.getSession().setAttribute(CustomRemoteServiceServlet.FILES,
					files);
		}
	}

	private void removeReceivedFileType(HttpServletRequest request,
			String fieldName) {
		Hashtable<String, String> types = (Hashtable<String, String>) request
				.getSession().getAttribute(
						CustomRemoteServiceServlet.FILE_TYPES);
		if (types != null) {
			types.remove(fieldName);
			request.getSession().setAttribute(
					CustomRemoteServiceServlet.FILE_TYPES, types);
		}
	}
	/*
	 * private void removeReceivedFileName(HttpServletRequest request, String
	 * fieldName){ Hashtable<String, String> names = (Hashtable<String, String>)
	 * request.getSession().getAttribute(CustomRemoteServiceServlet.FILE_NAMES);
	 * if(names != null){ names.remove(fieldName);
	 * request.getSession().setAttribute(CustomRemoteServiceServlet.FILE_NAMES,
	 * names); } }
	 */
}
