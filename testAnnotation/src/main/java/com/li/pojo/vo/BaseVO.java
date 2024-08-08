package com.li.pojo.vo;

//import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bright
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseVO {

    private static final long serialVersionUID = 1L;

    //    @ApiModelProperty(value = "结果码")
    private Boolean success = true;

    //    @ApiModelProperty(value = "错误码")
    private String errorCode;

    //    @ApiModelProperty(value = "错误信息")
    private String errorMsg;
}
