package kr.co.vilez.share.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import kr.co.vilez.data.HttpVO;
import kr.co.vilez.share.model.dto.ShareDto;
import kr.co.vilez.share.model.service.ShareService;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/shareboard")
@RestController
@Log4j2
public class ShareController {

    HttpVO httpVO = null;

    @Autowired
    ShareService shareService;

    @GetMapping("/bookmark/{boardId}")
    public ResponseEntity<?> bookmarkList(@PathVariable String boardId){
        httpVO = new HttpVO();

        try {
            httpVO = shareService.bookmarkList(boardId);
            httpVO.setFlag("success");
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<HttpVO>(httpVO, HttpStatus.OK);
    }

    @GetMapping("/bookmark/{boardId}/{email}")
    public ResponseEntity<?> isBookmark(@PathVariable("boardId") String boardId,
                                        @PathVariable("email") String email){
        httpVO = new HttpVO();

        try{
            httpVO = shareService.isBookmark(boardId, email);
            httpVO.setFlag("success");
        } catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<HttpVO>(httpVO, HttpStatus.OK);
    }
    @GetMapping("/bookmark")
    public ResponseEntity<?> bookmark(@RequestParam String boardId,
                                      @RequestParam String email,
                                      @RequestParam String state){
        httpVO = new HttpVO();

        try {
            httpVO = shareService.bookmark(boardId, email, state);
            httpVO.setFlag("success");
        } catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<HttpVO>(httpVO, HttpStatus.OK);
    }

    @GetMapping("/my/{userId}")
    public ResponseEntity<?> myDetail(@PathVariable int userId){
        httpVO = new HttpVO();

        try {
            httpVO = shareService.loadMyShareList(userId);
            httpVO.setFlag("success");
        } catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<HttpVO>(httpVO, HttpStatus.OK);
    }

    @GetMapping("/detail/{boardId}")
    public ResponseEntity<?> detail(@PathVariable String boardId){
        httpVO = new HttpVO();

        try{
            httpVO = shareService.detail(boardId);
            httpVO.setFlag("success");
        } catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<HttpVO>(httpVO, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestBody int boardId){
        httpVO = new HttpVO();

        try{
            shareService.delete(boardId);
            httpVO.setFlag("success");
        } catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<HttpVO>(httpVO, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestPart(value = "board") ShareDto shareDto,
                                    @RequestPart(value="image") List<MultipartFile> files){
        httpVO = new HttpVO();

        try {
            httpVO = shareService.update(shareDto, files);
            httpVO.setFlag("success");
        } catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<HttpVO>(httpVO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> insert(@RequestPart(value = "board") ShareDto shareDto,
                                    @RequestPart(value="image") List<MultipartFile> files){
        httpVO = new HttpVO();

        try {
            httpVO = shareService.insert(shareDto, files);
        } catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<HttpVO>(httpVO, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> boardList(){
        httpVO = new HttpVO();

        try {
            httpVO = shareService.loadShareList();
            httpVO.setFlag("success");
        } catch (Exception e){
            e.printStackTrace();
            log.warn("목록 조회중 오류 발생");
        }

        return new ResponseEntity<HttpVO>(httpVO, HttpStatus.OK);
    }

}
