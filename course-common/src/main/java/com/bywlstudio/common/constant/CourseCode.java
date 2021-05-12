package com.bywlstudio.common.constant;

/**
 * @Author: zl
 * @Date: Create in 2021/4/8 14:43
 * @Description:
 *
 */
public enum CourseCode {

    SUCCESS(20000,"成功"),FileNotFound(20001,"文件不存在"),
    CourseIsFull(20002,"课程无选课名额"),ArgError(20003,"参数错误"),
    COURSE_JUDGE(30000,"开始选课"),COURSE_JUDGE_FAILURE(30001,"选课未开始"),
    COURSE_SELECT_SUCCESS(30002,"选课成功"),COURSE_SELECT_FAILURE(30003,"选课失败");

    private int code ;
    private String message;


    CourseCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }




}
