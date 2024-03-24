package com.henry.api.web;


import com.henry.api.dto.BoardDto;
import com.henry.api.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class BoardCtrl {

	private BoardService boardService;
	//git remote add origin https://github.com/kimdong-dev/boot3.git
	@GetMapping(value = "/main")
	public String doGetHelloWorld() {
		return "Hello World";
	}

	@GetMapping("/board/{idx}")
	public ResponseEntity<?> findBoard(@PathVariable(name = "idx") Long idx) {
		BoardDto dto = boardService.selectBoardMap(idx);
		return ResponseEntity.ok(dto);
	}

}