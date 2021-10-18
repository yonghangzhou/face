package com.hfut.controller;

import com.hfut.VO.FaceVO;
import com.hfut.VO.ResultVO;
import com.hfut.converter.Faces2FaceVOConverter;
import com.hfut.pojo.Faces;
import com.hfut.service.FaceService;
import com.hfut.service.FaceServiceImpl;
import com.hfut.utils.ResultVOUtil;
import com.mchange.io.FileUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/face")
public class FaceController {
    @Autowired
    @Qualifier("FaceServiceImpl")
    private FaceService faceService=new FaceServiceImpl();

    //增加一条数据
    @RequestMapping("/add")
    public ResultVO add(@RequestParam("sex") String sex,
                        @RequestParam("age") int age,
                        @RequestParam("picture") MultipartFile pic){
        //上传文件
        try {

            String fileName = pic.getOriginalFilename();
            String destFileName = System.getProperty("user.dir")+"/files/"+fileName;
            File destFile = new File(destFileName);
            if(!destFile.exists()) {
                destFile.getParentFile().mkdir();
            }
            pic.transferTo(destFile);

            Faces faces= new Faces(sex,age,destFileName);

            faceService.addFace(faces);

            return ResultVOUtil.success();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResultVOUtil.error(1,"文件上传失败"+e.getMessage());
        } catch (IOException e){
            e.printStackTrace();
            return ResultVOUtil.error(1,"文件上传失败"+e.getMessage());
        }

    }

    //删除数据
    @RequestMapping("/deleteFace/{faceId}")
    public String deleteFace(@PathVariable("faceId") int id){
        faceService.deleteFaceById(id);
        return "redirect:/face/all";
    }

    //跳转到修改界面
    @RequestMapping("/toUpdate")
    public String toUpdate(int id, Model model){
        Faces face=faceService.queryFaceById(id);
        model.addAttribute("QFace",face);
        return  "updateFace";

    }

    //修改数据
    @RequestMapping(value = "/updateFace", method = RequestMethod.POST)
    public String updateFace(HttpServletRequest request){
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile pic = multipartRequest.getFile("pic");

        //
        String sex = multipartRequest.getParameter("sex");
        int age = Integer.parseInt(multipartRequest.getParameter("age"));
        int id = Integer.parseInt(multipartRequest.getParameter("id"));


        //上传文件
        try {
            String fileName = pic.getOriginalFilename();
            String destFileName = System.getProperty("user.dir")+"/files/"+fileName;
            File destFile = new File(destFileName);
            if(!destFile.exists()) {
                destFile.getParentFile().mkdir();
            }
            pic.transferTo(destFile);

            Faces faces= new Faces(sex,age,destFileName);

            faceService.updateFace(faces,id);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/face/all";
    }

    //跳转到添加书籍界面
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "addFace";
    }


    //增加数据
    @RequestMapping(value = "/addFace", method = RequestMethod.POST)
    public String addFace(HttpServletRequest request){
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile pic = multipartRequest.getFile("pic");

        //
        String sex = multipartRequest.getParameter("sex");
        int age = Integer.parseInt(multipartRequest.getParameter("age"));


        //上传文件
        try {

            Calendar calendar= Calendar.getInstance();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            String fileName = pic.getOriginalFilename();

//            String newfileName=dateFormat.format(calendar.getTime())+fileName;
//            String newdestFileName = System.getProperty("user.dir")+"\\files\\"+newfileName;

            String destFileName = System.getProperty("user.dir")+"\\files\\"+fileName;

            System.out.println(destFileName);

            File destFile = new File(destFileName);
            if(!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdir();
            }


            pic.transferTo(destFile);

//            File f=new File(destFileName);
//
//            if ( f.renameTo(new File(newdestFileName))) {
//                System.out.println("sucess");
//            }
//            else{
//                System.out.println("fail");
//            }


            Faces faces= new Faces(sex,age,destFileName);
            faceService.addFace(faces);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/face/all";
    }


    //查询所有的数据
    @RequestMapping("/all")
    public String all(Model model){
        List<Faces> list=faceService.queryAllFace();
        model.addAttribute("list",list);
        return "all";

    }



    //查询所有的数据
    @RequestMapping("/queryall")
    public ResultVO queryall(){
        List<Faces> list=faceService.queryAllFace();
        List<FaceVO> faceVOList= Faces2FaceVOConverter.convert(list);
        return ResultVOUtil.success(faceVOList);
    }

    //查询单个数据
    @RequestMapping("/querybyid")
    public ResultVO querybyid(@RequestParam("id") int id){
        Faces face=faceService.queryFaceById(id);
        FaceVO faceVO= Faces2FaceVOConverter.convert(face);
        return ResultVOUtil.success(faceVO);
    }

    //删除数据
    @RequestMapping("/delete")
    public ResultVO delete(@RequestParam("id") int id){
        faceService.deleteFaceById(id);
        return ResultVOUtil.success();
    }

    //修改数据
    @RequestMapping("/update")
    public ResultVO update(@RequestParam("id") int id,
                        @RequestParam("sex") String sex,
                        @RequestParam("age") int age,
                        @RequestParam("picture") MultipartFile pic){
        //上传文件
        try {
            String fileName = pic.getOriginalFilename();
            String destFileName = System.getProperty("user.dir")+"/files/"+fileName;
            File destFile = new File(destFileName);
            if(!destFile.exists()) {
                destFile.getParentFile().mkdir();
            }
            pic.transferTo(destFile);

            Faces faces= new Faces(sex,age,destFileName);

            faceService.updateFace(faces,id);

            return ResultVOUtil.success();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResultVOUtil.error(1,"文件上传失败"+e.getMessage());
        } catch (IOException e){
            e.printStackTrace();
            return ResultVOUtil.error(1,"文件上传失败"+e.getMessage());
        }

    }


//    /** 下载文件. */
//    @GetMapping("/downloadFile")
//    @ApiOperation("下载文件")
//    @ResponseBody
//    public ResultVO fileDownload(@RequestParam("fileName") String fileName, HttpServletResponse response){
//
//        File file = new File( System.getProperty("user.dir")+"/files/" + fileName);
//        if (!file.exists()){
//            return ResultVOUtil.error(7,"文件不存在");
//        }
//
//        if (Objects.isNull(fileName)){
//            return ResultVOUtil.error(8,"文件下载失败，请选择要下载的文件");
//        }
//        try{
//            categoryService.download(fileName, response);
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResultVOUtil.error(6,"文件下载失败"+e.getMessage());
//        }
//        return ResultVOUtil.success();
//    }



    //首次使用联网激活引擎
    public void active(){
        faceService.active();
    }

    //人脸检测，传入的是文件路径,下同
    public void detect(String[] files){
        faceService.detect(files);
    }

    //年龄检测
    public void judgeage(String[] files){
        faceService.judgeage(files);
    }

    //性别检测
    public void judgesex(String[] files){
        faceService.judgesex(files);
    }
}
