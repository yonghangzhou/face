package com.hfut.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FaceVO {

    @JsonProperty("id")
    private Integer faceId;

    @JsonProperty("sex")
    private String faceSex;

    @JsonProperty("age")
    private Integer faceAge;

    @JsonProperty("file")
    private String faceFlie;
}
