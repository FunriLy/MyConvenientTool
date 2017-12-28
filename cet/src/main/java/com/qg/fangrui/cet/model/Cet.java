package com.qg.fangrui.cet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 查询实体类
 * @author FunriLy
 * Created by FunriLy on 2017/12/25.
 * From small beginnings comes great things.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Cet {

    private String name;
    private String school;
    private String type;
    private String listenScore;
    private String readingScore;
    private String translatingScore;
    private String certificateNumber;
    private String result;
}
