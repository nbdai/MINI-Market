package com.dzl.sport.solrserviceimpl;

import com.dzl.sport.sku.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
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
    @Resource
    private SkuService skuService;
    //ajax异步上传图片
    @RequestMapping("updateSku")
    public void updateSku(BufferedReader bufferedReader){
        String str = null;
        try {
             str =  bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] strs = str.split(",");
        skuService.updateSku(strs);
    }
    @RequestMapping("KindImgLoad")
    public String kindImgLoad(@RequestParam MultipartFile pics, HttpSession session){
         if(pics==null){
             return "{\"error\" : 0,\"message\" : \"上传文件不存在\"}";
         }
        String path = session.getServletContext().getRealPath("images/pic");
        try {
            pics.transferTo(new File(path+File.separator+pics.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }

          return "{\"error\" : 0,\"url\" : \""+"images/pic/"+pics.getOriginalFilename()+"\"}";
    }

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
    @RequestMapping("ImgLoads")
    public  List<String>  brandImgLoads(@RequestParam MultipartFile[] pics, HttpSession session){
        String path = session.getServletContext().getRealPath("images/t");

        List<String> paths = new ArrayList<String>();
        for (MultipartFile file : pics){
            try {

                file.transferTo(new File(path+File.separator+file.getOriginalFilename()));

                paths.add("images/t/"+file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  paths ;
    }
}
