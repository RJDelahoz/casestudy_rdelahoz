package com.app.controller;

import com.app.model.Memo;
import com.app.model.Property;
import com.app.service.MemoService;
import com.app.service.PropertyService;
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

	private final PropertyService propertyService;
	private final MemoService memoService;

	@Autowired
	public MemoController(PropertyService propertyService, MemoService memoService) {
		this.propertyService = propertyService;
		this.memoService = memoService;
	}

	@RequestMapping(value = "/memo")
	public String memoFormHandler(Model model,
								  @RequestParam("id") long id) {
		Memo memo = propertyService.getPropertyById(id).getMemo();
		model.addAttribute("propertyId", id);
		return "memo";
	}

	@Transactional
	@RequestMapping(value = "/memo", method = RequestMethod.POST)
	public ModelAndView processMemoFormHandler(@RequestParam("propertyId") long id,
											   @RequestParam("subject") String subject,
											   @RequestParam("message") String content) {
		Property property = propertyService.getPropertyById(id);
		Memo memo = property.getMemo();
		memo.setSubject(subject);
		memo.setContent(content);
		memo.setTimestamp(new Date());
		memoService.addMemo(memo);
		return new ModelAndView("redirect:/ViewProperty?id="+property.getId());
	}


}
