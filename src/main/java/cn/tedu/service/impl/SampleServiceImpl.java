package cn.tedu.service.impl;

import org.springframework.stereotype.Service;

import cn.tedu.service.SampleService;

@Service
public class SampleServiceImpl implements SampleService {

	@Override
	public String test() {
		return "从SampleServiceImpl发来的祝贺！！";
	}

}
