package kr.co.crud.controller;

import kr.co.crud.domain.BoardVO;
import kr.co.crud.domain.CommentVO;
import kr.co.crud.domain.FileVO;
import kr.co.crud.security.MyUserDetails;
import kr.co.crud.service.BoardService;
import kr.co.crud.utils.SearchCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;


    /*selectBoardList*/
    @GetMapping("/")
    public String selectBoardList(Model model,
                                  @RequestParam Map map,
                                  @ModelAttribute SearchCondition sc,
                                  @AuthenticationPrincipal UserDetails details) {

        sc.setMap(map);

        boardService.selectBoardList(model,  sc);

        if(details != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            String nickname = userDetails.getNickname();

            model.addAttribute("nickname", nickname);
            model.addAttribute("username", details.getUsername());
        }
        return "/board/list";
    }


    /*writeBoard*/
    @GetMapping("/board/write")
    public String writeBoard(Model model,
                             @AuthenticationPrincipal UserDetails details) {

        if(details != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            String nickname = userDetails.getNickname();

            model.addAttribute("nickname", nickname);
            model.addAttribute("username", details.getUsername());
        }

        return "/board/write";
    }

    /*insertBoard*/
    @PostMapping("/board/write")
    public ResponseEntity<BoardVO> insertBoard(@RequestBody BoardVO boardVO, @AuthenticationPrincipal UserDetails details) {

        if(details != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boardVO.setUid(authentication.getName());
        }

        boardService.insertBoard(boardVO);

        return ResponseEntity.ok(boardVO);
    }


    /* File Upload */
    @PostMapping("/board/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String uploadPath = "/Users/parkjehyeong/Desktop/upload";
        String originalFilename = file.getOriginalFilename();

        Path filePath = Paths.get(uploadPath, originalFilename);
        try {
            Files.write(filePath, file.getBytes());
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            log.error("Error uploading file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file. Please try again.");
        }
    }


    /* 게시글 상세보기 */
    @GetMapping("/board/view")
    public String selectBoard(BoardVO boardVO,
                              @RequestParam("no") String no,
                              CommentVO commentVO,
                              Model model,
                              @AuthenticationPrincipal UserDetails details) {

        if(details != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            String nickname = userDetails.getNickname();


            model.addAttribute("username", details.getUsername());          // 현재 로그인한 사용자 아이디
            model.addAttribute("nickname", nickname);                       // 현재 로그인한 사용자 이름
        }


        boardVO.setNo(no);
        commentVO.setNo(no);

        /* 상세조회 */
        BoardVO board = boardService.selectBoard(boardVO);
        model.addAttribute("board", board);
        model.addAttribute("no", no);

        /* 댓글 목록 보기 */
        List<CommentVO> comments = boardService.selectComment(commentVO);
        model.addAttribute("commentList", comments);
        model.addAttribute("commentSize",comments.size());


        /* 조회수 +1 */
        boardService.updatehit(boardVO);

        return "/board/view";


    }


    /* 수정 화면 */
    @GetMapping("/board/modify")
    public String modifyBoard(BoardVO boardVO,
                              @RequestParam("no") String no,
                              Model model,
                              @AuthenticationPrincipal UserDetails details) {

        if(details != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            String nickname = userDetails.getNickname();


            model.addAttribute("username", details.getUsername());      // 현재 로그인한 사용자 아이디
            model.addAttribute("nickname", nickname);                   // 현재 로그인한 사용자 이름
        }

        BoardVO board = boardService.selectBoard(boardVO);
        model.addAttribute("board", board);
        model.addAttribute("no", no);

        return "/board/modify";
    }


    /* 게시글 수정 */
    @PostMapping("/board/modify/update")
    public ResponseEntity<BoardVO> updateBoard(@RequestBody BoardVO boardVO) {

        boardService.updateBoard(boardVO);
        return ResponseEntity.ok(boardVO);
    }


    /* 게시글 삭제 */
    @PostMapping("/board/delete")
    public ResponseEntity<BoardVO> deleteBoard(@RequestBody BoardVO boardVO) {

        boardService.deleteBoard(boardVO);
        return ResponseEntity.ok(boardVO);
    }


    /* 파일 다운로드 */
    @GetMapping("/board/fileDownload")
    @ResponseBody
    public ResponseEntity<Resource> fileDownload(@RequestParam("fno") String fno) throws IOException {

        int fnoInt = Integer.parseInt(fno);

        FileVO file = boardService.selectFile(fnoInt);

        ResponseEntity<Resource> response = boardService.fileDownload(file.getOriName());

        return response;

    }



    /* 댓글 등록 */
    @PostMapping("/board/comment/insert")
    public ResponseEntity<CommentVO> insertComment(@RequestBody CommentVO commentVO, @AuthenticationPrincipal UserDetails details) {

        log.warn("insertComment");

        if(details != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            commentVO.setUid(authentication.getName());
        }

        boardService.commentInsert(commentVO);
        return ResponseEntity.ok(commentVO);
    }


    /* 댓글 수정 */
    @PostMapping("/board/comment/update")
    public ResponseEntity<CommentVO> updateComment(@RequestBody CommentVO commentVO){
        boardService.updateComment(commentVO);
        return ResponseEntity.ok(commentVO);
    }


    /* 댓글 삭제 */
    @PostMapping("/board/comment/delete")
    public ResponseEntity<CommentVO> deleteComment(@RequestBody CommentVO commentVO){
        boardService.deleteComment(commentVO);
        return ResponseEntity.ok(commentVO);
    }


}