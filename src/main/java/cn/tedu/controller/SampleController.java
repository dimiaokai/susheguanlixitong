package cn.tedu.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tedu.service.SampleService;

@Controller
public class SampleController {
	
	@Resource
	private SampleService sampleService;
	
	@RequestMapping("totest")
	public String test(Model model){
		String msg = sampleService.test();
		model.addAttribute("msg", msg);
		return "test";
	}
	
}
