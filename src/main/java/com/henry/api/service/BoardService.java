package com.henry.api.service;


import com.henry.api.dto.BoardDto;
import com.henry.api.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;

	public BoardDto selectBoardMap(Long idx) {
		return boardMapper.selectBoardMap(idx);
	}

}