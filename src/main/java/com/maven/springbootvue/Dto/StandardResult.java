package com.maven.springbootvue.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 谢秉均
 * @date 2023/2/2--21:00
 */
@Data
@AllArgsConstructor
public class StandardResult {
    private int id; //校验的位置

    private String standardproject; //检验项名称

    private Boolean success;    //校验结果

    private String message; //校验返回的信息

    public StandardResult() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
