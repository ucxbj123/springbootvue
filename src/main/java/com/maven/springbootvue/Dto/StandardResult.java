package com.maven.springbootvue.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 谢秉均
 * @date 2023/2/2--21:00
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class StandardResult {
    private int id; //校验的位置

    private Boolean success;    //校验结果

    private String message; //校验返回的信息
}
