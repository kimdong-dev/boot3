package com.henry.api.mapper;

import com.henry.api.dto.BoardDto;

public interface BoardMapper {

	public BoardDto selectBoardMap(Long idx);
	
}