package com.li.pojo.vo;

//import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class FileVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //    @ApiModelProperty(value = "文件路径")
    private String downloadUrl;
}
