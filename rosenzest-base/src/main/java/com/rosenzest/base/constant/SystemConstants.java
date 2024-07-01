/** */
package com.rosenzest.base.constant;

/**
 * 常用常量
 *
 * @author fronttang
 */
public interface SystemConstants {

    /**
     * seqNo
     */
    String SEQ_NO = "SEQ_NO";

    /**
     * id
     */
    String ID = "id";

    /**
     * 名称
     */
    String NAME = "name";

    /**
     * 编码
     */
    String CODE = "code";

    /**
     * 值
     */
    String VALUE = "value";

    /**
     * 默认标识状态的字段名称
     */
    String STATUS = "status";

    /**
     * 默认逻辑删除的状态值
     */
    String DEFAULT_LOGIC_DELETE_VALUE = "0";

    /**
     * 用户代理
     */
    String USER_AGENT = "User-Agent";

    /**
     * 请求头token表示
     */
    String AUTHORIZATION = "Authorization";

    /**
     * token类型
     */
    String TOKEN_TYPE_BEARER = "Bearer";

    /**
     * 未知标识
     */
    String UNKNOWN = "Unknown";

    /**
     * 默认包名
     */
    String DEFAULT_PACKAGE_NAME = "com.xmzy";

    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "123456";

    /**
     * 请求号在header中的唯一标识
     */
    String REQUEST_NO_HEADER_NAME = "Request-No";

    /**
     * 24小时的秒数
     */
    Integer ONE_DAY_OF_SECONDS = 86400;

    /**
     * 7*24小时的秒数
     */
    Integer SEVEN_DAYS_OF_SECONDS = 604800;

    /**
     * 按照31天计算的秒数
     */
    long MONTH_SECONDS = 2678400;

    /**
     * 默认分页大小
     */
    int DEFAULT_PAGE_SIZE = 10;

    /**
     * 默认页码
     */
    int DEFAULT_PAGE = 1;

    /**
     * 默认操作员
     */
    String SYSTEM_OPERATOR = "SYSTEM";

    /**
     * request中存入的token信息
     */
    String REQUEST_TOKEN = "REQUEST_TOKEN";

    /**
     * request中存入的用户信息
     */
    String REQUEST_USER = "REQUEST_USER";

    /**
     * 错误码信息前缀
     */
    String ERROR_CODE_MESSAGE_SOURCE_PREFIX = "RESULT.CODE.";
}
