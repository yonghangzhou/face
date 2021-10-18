package com.hfut.service;

import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceInfo;
import com.hfut.pojo.Faces;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Service
public interface FaceService {

    //激活引擎
    void active();
    //人脸检测(IMAGE模式)
    List<FaceInfo> detect( String[] file);

    //识别性别
    void judgesex(String[] file);

    //识别年龄
    void judgeage(String[] file);

    //引擎卸载
    void unInit(FaceEngine faceEngine);



    //增加
    int addFace(Faces faces);

    //删除
    int deleteFaceById(int id);

    //根据ID查询
    Faces queryFaceById(int id);

    //查询全部
    List<Faces> queryAllFace();

    //更新
    int updateFace(Faces faces,int id);

    //下载
    public void download(String fileName, HttpServletResponse response)throws Exception;

    void test();
}
