package com.maven.springbootvue.Util;


import java.time.LocalDate;

/**
 * @author 谢秉均
 * @description
 * @date 2023/2/1--11:57
 */
public class CreateStandardCodeUtil {

    private static String baseCode = "Dream";

    //自动生成编号
    public static String getStandardCode(Integer size){
        //获取当前时间的年月日作为编号的一部分
        LocalDate time = LocalDate.now();
        String tmiecode = time.toString().replace("-","");
        //String.format格式化数据
        // %作先导标记，
        //0表示自动补0,
        //2的意思是最小长度为2（如果用3，则1输出001），
        //d表示整数
        String StandardCode = baseCode+"-"+tmiecode+String.format("%05d",size+1);
        return StandardCode;
    }
}
