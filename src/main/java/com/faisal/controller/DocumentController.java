package com.faisal.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.faisal.dao.DocumentDAO;
import com.faisal.model.Document;

@Controller
public class DocumentController {

	@Autowired
	private DocumentDAO documentDao;

	@RequestMapping("/index")
	public String index(Map<String, Object> map) {
		System.out.println("inside index get");
		try {
			map.put("document", new Document());
			map.put("documentList", documentDao.list());
			System.out.println("documentList:-" + documentDao.list().size());
		} catch (Exception e) {
			System.out.println("e.message is " + e.getMessage());
			e.printStackTrace();
		}
		return "documents";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("document") Document document, @RequestParam("file") MultipartFile file) {

		System.out.println("inside save post");

		try {
			Blob blob = Hibernate.createBlob(file.getInputStream());
			document.setFilename(file.getOriginalFilename());
			document.setContent(blob);
			document.setContentType(file.getContentType());

		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		documentDao.save(document);
		System.out.println(document);
		return "redirect:/index.html";
	}

	@RequestMapping("/download/{documentId}")
	public String download(@PathVariable("documentId") Integer documentId, HttpServletResponse response) {
		Document doc = documentDao.get(documentId);

		try {
			response.setHeader("Content-Disposition", "inline;filename=\"" + doc.getFilename() + "\"");
			OutputStream out = response.getOutputStream();
			response.setContentType(doc.getContentType());
			IOUtils.copy(doc.getContent().getBinaryStream(), out);
			out.flush();
			out.close();
			
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		

		return null;
	}
	
	@RequestMapping("/view/{documentId}")
	public String view(@PathVariable("documentId") Integer documentId, HttpServletResponse response) {
		Document doc = documentDao.get(documentId);

		try {
			response.setHeader("Content-Disposition", "inline;filename=\"" + doc.getFilename() + "\"");
			OutputStream out = response.getOutputStream();
			response.setContentType(doc.getContentType());
			IOUtils.copy(doc.getContent().getBinaryStream(), out);
			out.flush();
			out.close();
			
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		

		return null;
	}

	
	
	@RequestMapping("/remove/{documentId}")
	public String remove (@PathVariable("documentId") Integer documentId){
		documentDao.remove(documentId);
		return "redirect:/index.html";
	}

}
