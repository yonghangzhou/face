package com.hfut.service;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import com.hfut.dao.FaceMapper;
import com.hfut.pojo.Faces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import static com.arcsoft.face.toolkit.ImageFactory.getGrayData;
import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
@Service
public class FaceServiceImpl implements FaceService{

    private FaceMapper faceMapper;
    public void setFaceMapper(FaceMapper faceMapper){
        this.faceMapper=faceMapper;
    }


    public int addFace(Faces faces) {
        return faceMapper.addFace(faces);
    }

    public int deleteFaceById(int id) {
        return faceMapper.deleteFaceById(id);
    }

    public Faces queryFaceById(int id) {
        return faceMapper.queryFaceById(id);
    }

    public List<Faces> queryAllFace() {
        return faceMapper.queryAllFace();
    }

    public int updateFace(Faces faces,int id) {
        Faces face=faceMapper.queryFaceById(id);
        face.setFaceSex(faces.getFaceSex());
        face.setFaceAge(faces.getFaceAge());
        face.setFaceFile(faces.getFaceFile());
        return faceMapper.updateFace(face);
    }

    public void test(){
        System.out.println("测试");
        System.out.println(System.getProperty("user.dir"));
    }

    @Override
    public void active(){
        //从官网获取
        String appId = "Ag6EkqeA5aYZP1DCyTZFv7ESSezrJxCD1UE4APKS4YLL";
        String sdkKey = "tsziitsjpYnNpYQZe7Tq5VM4KJZEWXgwTA6Ro6LFdQC";


        FaceEngine faceEngine = new FaceEngine(System.getProperty("user.dir")+"/src/main/resources/facelibs/face");
        //激活引擎
        int errorCode = faceEngine.activeOnline(appId, sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("引擎激活失败");
        }


        ActiveFileInfo activeFileInfo=new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("获取激活文件信息失败");
        }
        System.out.println("引擎激活成功");
    }


    //检测人脸
    @Override
    public List<FaceInfo> detect(String[] file){

        FaceEngine faceEngine = new FaceEngine(System.getProperty("user.dir")+"/src/main/resources/facelibs/face");

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);//侦测模式
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);//侦测角度优先级
        engineConfiguration.setDetectFaceMaxNum(10);//最大人脸数
        engineConfiguration.setDetectFaceScaleVal(16);

        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportFaceDetect(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);

        //初始化引擎
        int errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }
        System.out.println("初始化引擎成功");
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        for(String f:file){
        ImageInfo imageInfo = getRGBData(new File(f));
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        }
        if (errorCode != ErrorInfo.MOK.getValue() ) {
            System.out.println("人脸检测失败");
            System.out.println(errorCode);
        }
        System.out.println(faceInfoList);
        return faceInfoList;

    }


    //判断性别
    @Override
    public void judgesex(String[] file){
        FaceEngine faceEngine = new FaceEngine(System.getProperty("user.dir")+"/src/main/resources/facelibs/face");
        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);//侦测模式
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);//侦测角度优先级
        engineConfiguration.setDetectFaceMaxNum(10);//最大人脸数
        engineConfiguration.setDetectFaceScaleVal(16);

        FunctionConfiguration configuration = new FunctionConfiguration();
        configuration.setSupportGender(true);
        engineConfiguration.setFunctionConfiguration(configuration);

        //初始化引擎
        int errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }

        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        for(String f:file){
            ImageInfo imageInfo = getRGBData(new File(f));
            errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);
            if (errorCode != ErrorInfo.MOK.getValue() ) {
                System.out.println("性别检测失败");
                System.out.println(errorCode);
            }
        }


        List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
        errorCode = faceEngine.getGender(genderInfoList);
        if (errorCode != ErrorInfo.MOK.getValue() ) {
            System.out.println("性别获取失败");
            System.out.println(errorCode);
        }
//        System.out.println("性别：" + genderInfoList.get(0).getGender());
    }


    //判断年龄
    @Override
    public void judgeage(String[] file){
        FaceEngine faceEngine = new FaceEngine(System.getProperty("user.dir")+"/src/main/resources/facelibs/face");

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);//侦测模式
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);//侦测角度优先级
        engineConfiguration.setDetectFaceMaxNum(10);//最大人脸数
        engineConfiguration.setDetectFaceScaleVal(16);

        FunctionConfiguration configuration = new FunctionConfiguration();
        configuration.setSupportAge(true);
        engineConfiguration.setFunctionConfiguration(configuration);

        //初始化引擎
        int errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }

        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        for(String f:file){
            ImageInfo imageInfo = getRGBData(new File(f));
            errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);
            if (errorCode != ErrorInfo.MOK.getValue() ) {
                System.out.println("年龄检测失败");
                System.out.println(errorCode);
            }
        }

        List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
        errorCode = faceEngine.getAge(ageInfoList);
        if (errorCode != ErrorInfo.MOK.getValue() ) {
            System.out.println("年龄获取失败");
            System.out.println(errorCode);
        }
        System.out.println("年龄：" + ageInfoList.get(0).getAge());
    }

    @Override
    public void unInit(FaceEngine faceEngine){
        int errorCode = faceEngine.unInit();
        if (errorCode != ErrorInfo.MOK.getValue() ) {
            System.out.println("卸载失败");
            System.out.println(errorCode);
        }
    }

    /** 文件下载. */
    @Override
    public void download(String fileName, HttpServletResponse response)throws Exception{
        File file = new File( System.getProperty("user.dir")+"/files/" + fileName);
        byte[] bytes = new byte[102400];
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            outputStream = response.getOutputStream();
            int length;
            while ((length = bufferedInputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, length);
            }
            outputStream.flush();
        }catch (Exception e){
            throw e;
        }finally {
            try {
                if (bufferedInputStream != null){
                    bufferedInputStream.close();
                }

                if (outputStream != null){
                    outputStream.close();
                }

                if (fileInputStream != null){
                    fileInputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
