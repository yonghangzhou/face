package com.hfut.converter;

import com.hfut.VO.FaceVO;
import com.hfut.pojo.Faces;

import java.util.ArrayList;
import java.util.List;

public class Faces2FaceVOConverter {

    public static FaceVO convert(Faces faces){
        FaceVO faceVO=new FaceVO();
        faceVO.setFaceId(faces.getFaceID());
        faceVO.setFaceSex(faces.getFaceSex());
        faceVO.setFaceAge(faces.getFaceAge());
        faceVO.setFaceFlie(faces.getFaceFile());
        return faceVO;
    }

    public static List<FaceVO> convert(List<Faces> list){
        List<FaceVO> faceVOList=new ArrayList<>();
        for (Faces faces:list){
            FaceVO faceVO=new FaceVO();
            faceVO.setFaceId(faces.getFaceID());
            faceVO.setFaceSex(faces.getFaceSex());
            faceVO.setFaceAge(faces.getFaceAge());
            faceVO.setFaceFlie(faces.getFaceFile());

            faceVOList.add(faceVO);
        }
        return faceVOList;
    }
}
