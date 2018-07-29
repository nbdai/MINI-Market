package com.dzl.sport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  @author DZL
 *  @desc  处理Ajax的controller
 *  */
@Controller
@ResponseBody

public class UploadController {
    //ajax异步上传图片
    @RequestMapping("ImgLoad")
    public  List<String>  brandImgLoad(@RequestParam MultipartFile[] pics, HttpSession session){
        String path = session.getServletContext().getRealPath("images/pic");
        List<String> paths = new ArrayList<String>();
        for (MultipartFile file : pics){
            try {
                file.transferTo(new File(path+File.separator+file.getOriginalFilename()));

                paths.add("images/pic/"+file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
              return  paths ;
    }
}
