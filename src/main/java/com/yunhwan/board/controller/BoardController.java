package com.yunhwan.board.controller;

import com.yunhwan.board.dto.BoardDTO;
import com.yunhwan.board.dto.PageRequestDTO;
import com.yunhwan.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // PageRequestDTO를 따로 모델어트리뷰트 처리하지않아도 자동으로 모델에 추가가 되어 전달이 된다.
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list............." + pageRequestDTO);
        model.addAttribute("result", boardService.getList(pageRequestDTO)); // PageReqeustDTO
    }


    // register 화면 조회
    @GetMapping("/register")
    public void register(){
        log.info("regiser get...");
    }
    // dto 정보를 받아와서 등록 -> 후 목록으로 리다이렉트
    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes
            redirectAttributes){
        log.info("dto..." + dto);
//새로 추가된 엔티티의 번호
        Long bno = boardService.register(dto);
        log.info("BNO: " + bno);
        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify" })
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model){
        log.info("bno: " + bno);
        BoardDTO boardDTO = boardService.get(bno);
        log.info(boardDTO);
        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){

        boardService.modify(dto);
        // 검색 조건을 유지하기 위한 속성 추가 전략
        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());
        redirectAttributes.addAttribute("bno",dto.getBno());
        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes){
        log.info("bno: " + bno);
        boardService.removeWithReplies(bno);
        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }


}
