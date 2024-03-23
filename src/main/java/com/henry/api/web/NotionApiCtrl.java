package com.henry.api.web;


import com.henry.api.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class NotionApiCtrl {

	private BoardService boardService;

	@GetMapping(value = "/notion/list")
	public String getNotionList() {
		return "Hello World";
	}

}