package com.hfut.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Faces {
    private int faceID;
    private String faceSex;
    private int faceAge;
    private String faceFile;

    public Faces(String sex,int age,String faceFile){
        this.faceSex=sex;
        this.faceAge=age;
        this.faceFile=faceFile;
    }

}
