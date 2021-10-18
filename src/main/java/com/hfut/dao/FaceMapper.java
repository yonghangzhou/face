package com.hfut.dao;

import com.hfut.pojo.Faces;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FaceMapper {

    //增加
    int addFace(Faces faces);

    //删除
    int deleteFaceById(@Param("faceId") int id);

    //根据ID查询
    Faces queryFaceById(@Param("faceId") int id);

    //查询全部
    List<Faces> queryAllFace();

    //更新
    int updateFace(Faces faces);
}
