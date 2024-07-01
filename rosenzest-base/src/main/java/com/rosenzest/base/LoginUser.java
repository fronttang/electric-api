/**
 * 
 */
package com.rosenzest.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 登录用户基础信息
 * 
 * @author fronttang
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LoginUser extends JwtPayLoad {

    private static final long serialVersionUID = -233861039549453822L;

    /**
     * 用户类型 00系统用户,01检测单位管理员
     */
    private String type;

    /**
     * 终端类型 APP
     */
    private String terminal;
    
    /**
     * 检测单位ID
     */
    private Long detectId;

}
