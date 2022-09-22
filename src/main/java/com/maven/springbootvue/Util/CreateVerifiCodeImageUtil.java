package com.maven.springbootvue.Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author 谢秉均
 * @description:绘制验证码图片
 * @date 2022/8/31--13:32
 */
public class CreateVerifiCodeImageUtil {

    private static int WIDTH = 90;
    private static int HEIGHT = 35;
    private static int FONT_SIZE = 20;//字符大小
    private static char[] verifiCode; //验证码
    private static BufferedImage verifiCodeImage; //验证码图片


    /**
    *@description： 获取验证码图片
    *@Param:
    *@return: BufferedImage
    *@Author: 谢秉均
    *@date: 2022/8/31--14:11
    */
    public static BufferedImage getVerifiCodeImage(){
        //创建图对象
        verifiCodeImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_BGR);
        //声明一个画笔对象
        Graphics graphics = verifiCodeImage.getGraphics();
        //获取随机生成的验证码
        verifiCode = generateCheckCode();
        //绘制背景图片并添加圆形干扰点
        drawBackground(graphics);
        //将字符添加到背景图片
        drawRands(graphics,verifiCode);
        //处理好图片并释放资源，调用dispose()后无法使用图形对象。
        graphics.dispose();
        //返回绘制好的验证码图片
        return verifiCodeImage;
    }

    /**
    *@description：获取验证码
    *@Param:
    *@return:  char[]
    *@Author: 谢秉均
    *@date: 2022/8/31--14:03
    */
    public static char[] getVerifiCode(){
        return verifiCode;
    }


    /**
    *@description：获取随机生成的验证码
    *@Param:
    *@return: char[]
    *@Author: 谢秉均
    *@date: 2022/8/31--14:27
    */
    public static char[] generateCheckCode(){
        //设置生成验证码的范围
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //设置生成验证码为4个
        char[] rands = new char[4];
        //随机获取四个字符并添加到rands
        for(int i = 0; i < 4; i++){
            //生成[0,62)之间的随机浮点数，再强制转化为int类型
            int rand = (int)(Math.random() * (10 + 26 * 2));
            //往字符数组添加随机获取的单个字符，chars.charAt()是获取对应下标的字符
            rands[i] = chars.charAt(rand);
        }
        return rands;
    }


    /**
    *@description：绘制验证码图片背景(包括绘制干扰点)
    *@Param: Graphics
    *@return:
    *@Author: 谢秉均
    *@date: 2022/8/31--14:52
    */
    public static void drawBackground(Graphics g){
        //设置画笔颜色
        g.setColor(Color.white);
        //从(0,0)点到(wigth,height)点填充一个矩形，即设置背景颜色
        g.fillRect(0,0, WIDTH, HEIGHT);

        // 绘制验证码干扰点，200个圆形干扰点，宽度和高度都为1
        for(int i =0;i < 200; i++){
            //随机获取矩形内的x,y坐标
            int x = (int)(Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);
            //设置随机颜色的画笔
            g.setColor(getRandomColor());
            //设置干扰点
            /**绘制圆形（可以是圆或者椭圆）干扰点
             * drawOval(x,y,width,height)参数说明：
             * x–要绘制的椭圆左上角的x坐标。
             * y–要绘制的椭圆左上角的y坐标。
             * 宽度–要绘制的椭圆形的宽度。
             * 高度–要绘制的椭圆形的高度。
             */
            g.drawOval(x,y,1,1);
        }
    }


    /**
    *@description：绘制验证码（将字符绘制在验证码图片中）
    *@Param: Graphics g, char[] rands
    *@return:
    *@Author: 谢秉均
    *@date: 2022/8/31--15:33
    */
    public static void drawRands(Graphics g, char[] rands){
        //设置字体
        g.setFont(new Font("Console",Font.BOLD,FONT_SIZE));

        for(int i = 0;i < rands.length; i++){
            //随机设置画笔颜色
            g.setColor(getRandomColor());
            //drawString(str,x,y)   str–要绘制的字符串 x–x坐标：设置字符的x坐标，字符间隔为FONT_SIZE（20）   y–y坐标：因为height最长为35，所有可固定y坐标为25
            g.drawString(""+rands[i],i * FONT_SIZE + 10,25);
        }
    }

    /**
    *@description：获取随机颜色
    *@Param:
    *@return: Color
    *@Author: 谢秉均
    *@date: 2022/8/31--15:10
    */
    public static Color getRandomColor(){
        Random ran = new Random();
        //ran.nextInt(n)随机获取0~n的整数,不包括n
        /**
         * 使用指定的红色、绿色和蓝色值（0-255）创建不透明的sRGB颜色,Alpha默认为255
         *  Color(int r, int g, int b) 参数：
         * r–红色分量
         * g—绿色成分
         * b–蓝色成分
         */
        return new Color(ran.nextInt(220),ran.nextInt(220),ran.nextInt(220));
    }
}
