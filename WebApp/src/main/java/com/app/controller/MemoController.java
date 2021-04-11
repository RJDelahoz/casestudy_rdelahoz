package com.app.controller;

import com.app.dao.MemoDao;
import com.app.dao.PropertyDao;
import com.app.model.Memo;
import com.app.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class MemoController {

	private final PropertyDao propertyDao;
	private final MemoDao memoDao;

	@Autowired
	public MemoController(PropertyDao propertyDao, MemoDao memoDao) {
		this.propertyDao = propertyDao;
		this.memoDao = memoDao;
	}

	@RequestMapping(value = "/memo")
	public String memoFormHandler(Model model,
								  @RequestParam("id") long id) {
		model.addAttribute("propertyId", id);
		return "memo";
	}

	@Transactional
	@RequestMapping(value = "/memo", method = RequestMethod.POST)
	public ModelAndView processMemoFormHandler(@RequestParam("propertyId") long id,
											   @RequestParam("subject") String subject,
											   @RequestParam("message") String content) {
		Property property = propertyDao.getPropertyById(id);
		Memo memo = property.getMemo();
		memo.setSubject(subject);
		memo.setContent(content);
		memo.setTimestamp(new Date());
		memoDao.addMemo(memo);
		return new ModelAndView("redirect:/view-property?id="+property.getId());
	}
}
