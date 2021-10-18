package com.hfut.service;

import com.hfut.pojo.Faces;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

public class FaceServiceImplTest {

    @Test
    public void test() {
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        FaceService faceService=(FaceService) context.getBean("FaceServiceImpl");
        faceService.test();
    }

    @Test
    public void test1() {
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        FaceService faceService=(FaceService) context.getBean("FaceServiceImpl");
        faceService.active();
    }
    @Test
    public void test2() {
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        FaceService faceService=(FaceService) context.getBean("FaceServiceImpl");
        Faces faces=new Faces("男",23,"地址");
        faceService.addFace(faces);
    }
    @Test
    public void test3() {
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        FaceService faceService=(FaceService) context.getBean("FaceServiceImpl");
        Faces faces=new Faces("女",23,"地址");
        faceService.updateFace(faces ,1);
    }

    @Test
    public void test4() {
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        FaceService faceService=(FaceService) context.getBean("FaceServiceImpl");
        List<Faces> list=faceService.queryAllFace();
        for(Faces faces:list){
            System.out.println(faces);
        }
    }
    @Test
    public void test5() {
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        FaceService faceService=(FaceService) context.getBean("FaceServiceImpl");
        faceService.deleteFaceById(1);
    }
    //人脸检测
    @Test
    public void test6(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        FaceService faceService=(FaceService) context.getBean("FaceServiceImpl");
        String[] file={"C:\\Users\\zsj\\Desktop\\4.jfif"};
        faceService.detect(file);
    }
    //年龄检测
    @Test
    public void test7(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        FaceService faceService=(FaceService) context.getBean("FaceServiceImpl");
        String[] file={"C:\\Users\\zsj\\Desktop\\1.jpg"};
        faceService.judgeage(file);
    }
    //性别检测
    @Test
    public void test8(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        FaceService faceService=(FaceService) context.getBean("FaceServiceImpl");
        String[] file={"C:\\Users\\zsj\\Desktop\\1.jpg"};
        faceService.judgesex(file);
    }

}