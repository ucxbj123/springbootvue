package com.maven.springbootvue.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * API接口的基础返回类
 * 使用lombok简化代码
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseResponse<T> {
    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 说明
     */
    private String msg;

    /**
     * 返回数据
     */
    @Nullable
    private T data;

    /**
     * 返回的状态，前端根据返回的状态进行拦截处理
     */
    private Integer code;



}
