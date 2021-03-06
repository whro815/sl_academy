package kr.co.Controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.co.DTO.BoardDTO;
import kr.co.ServiceClass.Board.BoardDeleteService;
import kr.co.ServiceClass.Board.BoardDetailService;
import kr.co.ServiceClass.Board.BoardInsertService;
import kr.co.ServiceClass.Board.BoardListService;
import kr.co.ServiceClass.Board.BoardModifyLookService;
import kr.co.ServiceClass.Board.BoardModifyService;
import kr.co.ServiceClass.Board.BoardReplyLookService;
import kr.co.ServiceClass.Board.BoardReplyService;
import kr.co.util.FileUpload;

@Controller
public class BoardController
{
	// 전체 게시글 조회
	@Autowired
	BoardListService boardListService;
	
	// 게시판 글쓰기
	@Autowired
	BoardInsertService boardInsertService;
	
	// 게시글 열기
	@Autowired
	BoardDetailService boardDetailService;
	
	// 게시글 삭제
	@Autowired
	BoardDeleteService boardDeleteService; 
	
	// 게시글 수정 조회
	@Autowired
	BoardModifyLookService boardModifyLookService;
	
	// 게시글 수정
	@Autowired
	BoardModifyService boardModifyService;
	
	// 답변 조회
	@Autowired
	BoardReplyLookService boardReplyLookService;
	
	// 답변
	@Autowired
	BoardReplyService boardReplyService;
	
	
	
	// 전체 게시글 조회
	@RequestMapping("/BoardList.bo")
	public String boardList(HttpServletRequest request, Model model)
	{
		boardListService.getBoardList(request, model);
		
		return "board/qna_board_list";
	}
	
	// 글쓰기 페이지
	@RequestMapping("/BoardWrite.bo")
	public String boardWriteForm()
	{
		return "board/qna_board_write";
	}
	
	// 게시판 글쓰기
	@RequestMapping("/BoardAddAction.bo")
	public String boardWrite(HttpSession session, BoardDTO boardDTO, FileUpload file, MultipartFile mf) throws Exception
	{
		boardInsertService.boardInsert(session, boardDTO, file, mf);
		
		return "redirect:BoardList.bo";
	}
	
	
	// 게시글 열기
	@RequestMapping("/BoardDetailAction.bo")
	public String boardDetail(HttpServletRequest request, Model model)
	{
		
		BoardDTO boardDTO = boardDetailService.boardDetail(request);
		
		model.addAttribute("boardDTO", boardDTO);
		
		return "board/qna_board_view";
	}
	
	
	// 게시글 삭제 페이지
	@RequestMapping("/BoardDelete.bo")
	public String boardDeleteForm(HttpServletRequest request, Model model)
	{
		model.addAttribute("num", request.getParameter("num"));
		
		return "board/qna_board_delete";
	}
	
	// 게시글 삭제
	@RequestMapping("/BoardDeleteAction.bo")
	public String boardDelete(HttpServletRequest request)
	{
		boardDeleteService.delete(request);
		
		return "redirect:BoardList.bo";
	}
	
	// 게시글 수정 페이지
	@RequestMapping("/BoardModify.bo")
	public String boardModifyForm(HttpServletRequest request, Model model)
	{
		BoardDTO boardDTO = boardModifyLookService.boardModifyLook(request);
		
		model.addAttribute("boardDTO", boardDTO);
		
		return "board/qna_board_modify";
	}
	
	// 게시글 수정
	@RequestMapping("/BoardModifyAction.bo")
	public String boardModify(BoardDTO boardDTO)
	{
		boardModifyService.boardModify(boardDTO);
		
		return "redirect:BoardList.bo";
	}
	
	// 답변 페이지
	@RequestMapping("/BoardReplyAction.bo")
	public String replyForm(HttpServletRequest request, Model model)
	{
		BoardDTO boardDTO = boardReplyLookService.replyLook(request);
		
		model.addAttribute("boardDTO", boardDTO);
		
		return "board/qna_board_reply";
	}
	
	// 답변하기
	@RequestMapping("/BoardReplyView.bo")
	public String reply(BoardDTO boardDTO)
	{
		boardReplyService.reply(boardDTO);
		
		return "redirect:BoardList.bo";
	}
}
