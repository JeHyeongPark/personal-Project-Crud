package kr.co.crud.service;

import kr.co.crud.dao.BoardDAO;
import kr.co.crud.domain.BoardVO;
import kr.co.crud.domain.CommentVO;
import kr.co.crud.domain.FileVO;
import kr.co.crud.utils.PageHandler;
import kr.co.crud.utils.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardDAO boardDAO;


    /*selectBoardList*/
    public List<BoardVO> selectBoardList(Model model, SearchCondition sc){

        int totalCnt = boardDAO.countBoard(sc);
        int totalPage = (int)Math.ceil(totalCnt / (double)sc.getPageSize());
        if(sc.getPage() > totalPage) sc.setPage(totalPage);




        PageHandler pageHandler = new PageHandler(totalCnt, sc);

        List<BoardVO> boardList = boardDAO.selectBoardList(sc);

        List<Integer> noList = new ArrayList<>();
        int startNo = totalCnt - (sc.getPage() - 1) * sc.getPageSize();

        for (int i = 0; i < boardList.size(); i++) {
            noList.add(startNo - i);
        }

        model.addAttribute("ph", pageHandler);
        model.addAttribute("boardList", boardList);
        model.addAttribute("noList", noList);

        return boardList;
    }


    /* 현재시간날짜 메소드 */
    public static String getTodayDateString(){

        /* 현재 시간 날짜 */
        LocalDateTime now = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.now());

        /* 현재 시간 날짜 포맷 설정 */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateString = now.format(formatter);

        return dateString;
    }


    /*insertBoard*/
    public void insertBoard(BoardVO boardVO){


        /* 현재날짜시간날짜 메소드 불러와서 No에 세팅 */
        String todayDateString = getTodayDateString();
        boardVO.setNo(todayDateString);

        /* 게시글 작성 */
        boardDAO.insertBoard(boardVO);


        if(boardVO.getName() != null){
        /* 불러진 원래 파일명 가져오기 */
        String oriName = boardVO.getName();
        /* .확장자명 앞에까지 자르기 */
        String tempName = oriName.substring(oriName.lastIndexOf("."));
        /* 랜덤명생성 */
        String newName = UUID.randomUUID().toString() + tempName;

        /* 파일 정보 작성 */
        FileVO fileVO = new FileVO();
        fileVO.setParent(todayDateString);
        fileVO.setOriName(oriName);
        fileVO.setNewName(newName);

        boardDAO.insertFile(fileVO);
    }


    }


    /* 게시글 보기 */
    public BoardVO selectBoard(BoardVO boardVO){

        return boardDAO.selectBoard(boardVO);
    }


    /* 조회수 */
    public void updatehit(BoardVO boardVO){

        boardDAO.updatehit(boardVO);
    }


    /* 게시글 수정 */
    public void updateBoard(BoardVO boardVO) {

        boardDAO.updateBoard(boardVO);

        if (boardVO.getName() != null) {
            /* 불러진 원래 파일명 가져오기 */
            String oriName = boardVO.getName();
            /* .확장자명 앞에까지 자르기 */
            String tempName = oriName.substring(oriName.lastIndexOf("."));
            /* 랜덤명생성 */
            String newName = UUID.randomUUID().toString() + tempName;
            int fno = boardVO.getFno();

            /* 파일 정보 작성 */
            FileVO fileVO = new FileVO();
            fileVO.setOriName(oriName);
            fileVO.setNewName(newName);
            fileVO.setFno(fno);


            boardDAO.updateFile(fileVO);
        }

    }

    /* 게시글 삭제 */
    public void deleteBoard(BoardVO boardVO){

        boardDAO.deleteBoard(boardVO);
    }


    /* 첨부파일 다운로드 */
    public ResponseEntity<Resource> fileDownload(String oriName) throws IOException {

        String uploadPath = "/Users/parkjehyeong/Desktop/upload/";
        Path path = Paths.get(uploadPath, oriName);

        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(oriName)
                .build());

        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }


    /* 파일 선택 */
    public FileVO selectFile(int fno){

        return boardDAO.selectFile(fno);
    }


    /* 댓글 등록 */
    public void commentInsert(CommentVO commentVO){
        boardDAO.commentInsert(commentVO);
    }


    /* 댓글 보기 목록 */
    public List<CommentVO> selectComment(CommentVO commentVO){
        return boardDAO.selectComment(commentVO);
    }

    /* 댓글 수정 */
    public void updateComment(CommentVO commentVO){
        boardDAO.updateComment(commentVO);
    }

    /* 댓글 삭제 */
    public void deleteComment(CommentVO commentVO){
        boardDAO.deleteComment(commentVO);
    }

}

