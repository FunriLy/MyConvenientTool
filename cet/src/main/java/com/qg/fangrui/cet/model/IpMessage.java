package com.qg.fangrui.cet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * IP 类
 * @author FunriLy
 * Created by FunriLy on 2017/12/28.
 * From small beginnings comes great things.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class IpMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private String IPAddress;
    private String IPPort;
    private String IPType;
    private String IPSpeed;
}
