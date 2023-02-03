package com.maven.springbootvue.Util;

import com.maven.springbootvue.Dto.StandardResult;
import com.maven.springbootvue.Pojo.Teststandard;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 谢秉均
 * @date 2023/2/2--20:57
 * 封装校验逻辑的工具，用于对条码的校验
 */

public class StandardUtil {



    //整合所有校验逻辑 code:被校验的编码, len:条码长度
    public static List<StandardResult> Check(List<Teststandard> teststandards, int len , String code){
        //存储所有校验结果
        List<StandardResult> results = new ArrayList<>();
        for(Teststandard t: teststandards){
            StandardResult result = checkType(t);
            if(!result.getSuccess()){//如果第一步的校验参考值的类型失败，则不需要进行后续的值的校验，结束本次循环
                //添加校验的id
                result.setId(t.getId());
                results.add(result);
                continue;
            }
            StandardResult result1 = checkValue(t,code,len);
            result1.setId(t.getId());
            results.add(result1);

        }
        return results;
    }


    //1、校验值的类型类型--检验失败原因：参考值不是数字类型--只校验参考值类型，不对截取code校验
    private static StandardResult checkType(Teststandard teststandard){
        StandardResult result = new StandardResult();
        Boolean res = false;
        if (teststandard.getIscheck()){//开启校验才进行后续校验
            //根据valuetype进行类型判断
            if(teststandard.getValuetype().equals("int") ){
                if(!teststandard.getLogicvalue().equals("") && teststandard.getLogicvalue() != null){//若逻辑值有填则需要校验两个参考值，否则校验第一个参考值
                    res = StringUtils.isNumeric(teststandard.getCheckvalue()) && StringUtils.isNumeric(teststandard.getCheckvalue1());
                }else {
                    res = StringUtils.isNumeric(teststandard.getCheckvalue());
                }
                result.setSuccess(res);
            }else {
                //如果不是int不需要校验类型即为通过
                result.setSuccess(true);
            }

        }else {
            //不需要校验即为通过
            result.setSuccess(true);
        }
        if(!result.getSuccess()){
            result.setMessage("参考值"+teststandard.getCheckvalue()+" "+teststandard.getCheckvalue1()+"不是数字类型");
        }
        return result;
    }

    //2、根据起始位置和长度进行校验 teststandard:检验内容,code:被校验的编码, len:条码长度
    private static StandardResult checkValue(Teststandard teststandard,String code, int len){
        //存储校验结果
        StandardResult result = new StandardResult();
        int CheckStartPosition = Integer.valueOf(teststandard.getCheckstartposition());
        int StandardLength = Integer.valueOf(teststandard.getStandardlength());
        int startindex = CheckStartPosition-1;
        //因为substring截取的末尾索引并不会包括，所以要+1
        int endindex = StandardLength+startindex-1+1;
        if (startindex > len || endindex > len){//如果截取的位置已经超过条码长度则不需要往后操作
            System.out.println(startindex+" "+endindex);
            result.setSuccess(false);
            result.setMessage("起始位置至"+StandardLength+"的位置已经超过条码长度");
        }else {
            //截取指定校验部分code
            String checkcode = code.substring(startindex,endindex);
            //调试
//            System.out.println("截取："+checkcode);
            //校验编码
            StandardResult result1 = checkcode(teststandard.getValuetype(),checkcode, teststandard.getCheckvalue(),teststandard.getCheckcondition());
            if(!teststandard.getLogicvalue().equals("") && teststandard.getLogicvalue() != null){//若逻辑值不为空则进行后续第二个参考值和检验条件的校验
                StandardResult result2 = checkcode(teststandard.getValuetype(),checkcode, teststandard.getCheckvalue1(),teststandard.getCheckcondition1());
                switch (teststandard.getLogicvalue()){
                    case "and":
                        if(result2.getSuccess() && result1.getSuccess()){
                            result.setSuccess(true);
                        }else if(!result1.getSuccess()){
                            result.setSuccess(false);
                            result.setMessage(result1.getMessage());
                        }else{
                            result.setSuccess(false);
                            result.setMessage(result2.getMessage());
                        }
                        break;
                    case "or":
                        if(result2.getSuccess() || result1.getSuccess()){
                            result.setSuccess(true);
                        }else{
                            result.setSuccess(false);
                            result.setMessage(result2.getMessage()+" "+result1.getMessage());
                        }
                        break;
                }
            }else {//没有逻辑值，则直接返回第一次校验的结果
                result.setSuccess(result1.getSuccess());
                result.setMessage(result1.getMessage());
            }

        }
        return result;
    }

    //校验编码
    // type:参数数据类型, String code:校验的部分, CheckValue:参考值, CheckCondition:校验条件。例如=,!=,<
    private static StandardResult checkcode(String type, String code, String CheckValue, String CheckCondition){
        //存储校验结果
        StandardResult result = new StandardResult();
        //字符串进行比较的字符
        List<String> Stringlist = Arrays.asList("=","!=");
        //数值型进行比较的字符
        List<String> intlist = Arrays.asList("=","!=","<",">","<=",">=");

        if (type.equals("int") && intlist.contains(CheckCondition)){//校验数值型
            int codes;
            if(StringUtils.isNumeric(code)){//判断截取的部分是否全为数字，若是继续执行；否则直接校验失败，提示错误信息
                codes = Integer.valueOf(code);
            }else {
                result.setSuccess(false);
                result.setMessage(code+"存在非数字的字符");
                return result;
            }
            int Checkvalues = Integer.valueOf(CheckValue);
            switch (CheckCondition){
                case "=":
                    if (codes == Checkvalues){
                        result.setSuccess(true);
                    }else {
                        result.setSuccess(false);
                        result.setMessage(codes+"与"+Checkvalues+"不相等");
                    }
                    break;
                case "!=":
                    if (codes != Checkvalues){
                        result.setSuccess(true);
                    }else {
                        result.setSuccess(false);
                        result.setMessage(codes+"与"+Checkvalues+"相等了，与比较条件"+CheckCondition+"不符");
                    }
                    break;
                case ">":
                    if (codes > Checkvalues){
                        result.setSuccess(true);
                    }else {
                        result.setSuccess(false);
                        result.setMessage(codes+"不"+CheckCondition+Checkvalues+"，与比较条件不符");
                    }
                    break;
                case "<":
                    if (codes < Checkvalues){
                        result.setSuccess(true);
                    }else {
                        result.setSuccess(false);
                        result.setMessage(codes+"不"+CheckCondition+Checkvalues+"，与比较条件不符");
                    }
                    break;
                case ">=":
                    if (codes >= Checkvalues){
                        result.setSuccess(true);
                    }else {
                        result.setSuccess(false);
                        result.setMessage(codes+"不"+CheckCondition+Checkvalues+"，与比较条件不符");
                    }
                    break;
                case "<=":
                    if (codes <= Checkvalues){
                        result.setSuccess(true);
                    }else {
                        result.setSuccess(false);
                        result.setMessage(codes+"不"+CheckCondition+Checkvalues+"，与比较条件不符");
                    }
                    break;
                default:
                    result.setMessage("暂时未处理");
                    result.setSuccess(true);
            }

        }else if(type.equals("string") && Stringlist.contains(CheckCondition)){//校验字符串
            switch (CheckCondition){
                case "=":
                    if (code.equals(CheckValue)){
                        result.setSuccess(true);
                    }else {
                        result.setSuccess(false);
                        result.setMessage(code+"与"+CheckValue+"不相等");
                    }
                    break;
                case "!=":
                    if (!code.equals(CheckValue)){
                        result.setSuccess(true);
                    }else {
                        result.setSuccess(false);
                        result.setMessage(code+"与"+CheckValue+"相等了，与比较条件"+CheckCondition+"不符");
                    }
                    break;
            }

        }else{
            result.setMessage("int类型只支持= !=比较符");
            result.setSuccess(false);
        }
        return result;

    }
}
