package kr.co.crud.dao;

import kr.co.crud.domain.BoardVO;
import kr.co.crud.domain.CommentVO;
import kr.co.crud.domain.FileVO;
import kr.co.crud.utils.SearchCondition;
import org.apache.ibatis.annotations.Mapper;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

@Mapper
public interface BoardDAO {

    /* 게시글 목록 */
    List<BoardVO> selectBoardList(SearchCondition sc);

    /* 게시글 개수 */
    public int countBoard(SearchCondition sc);

    /* 글 쓰기 */
    public void insertBoard(BoardVO boardVO);

    /*파일 이름*/
    public void insertFile(FileVO fileVO);

    /* 게시글 보기 */
    public BoardVO selectBoard(BoardVO boardVO);

    /* 조회수 */
    public void updatehit(BoardVO boardVO);

    /* 게시글 수정 */
    public void updateBoard(BoardVO boardVO);

    /* 파일 수정 */
    public void updateFile(FileVO fileVO);

    /* 게시글 삭제 */
    public void deleteBoard(BoardVO boardVO);

    /* 파일 선택 */
    public FileVO selectFile(int fno);

    /* 댓글 등록 */
    public void commentInsert(CommentVO commentVO);

    /* 댓글 목록 보기 */
    public List<CommentVO> selectComment(CommentVO commentVO);

    /* 댓글 수정 */
    public void updateComment(CommentVO commentVO);

    /* 댓글 삭제 */
    public void deleteComment(CommentVO commentVO);


}
