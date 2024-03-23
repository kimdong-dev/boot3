--
-- 회원
--
DROP TABLE IF EXISTS BOARD_T;

CREATE TABLE BOARD_T COMMENT '게시판' (
    BOARD_IDX      INT     NOT NULL    COMMENT 'IDX'
    , TITLE        VARCHAR(100)                COMMENT '제목'
    , GBN        VARCHAR(10)                COMMENT '게시판구분'
    , PRIMARY KEY (BOARD_IDX)
    );