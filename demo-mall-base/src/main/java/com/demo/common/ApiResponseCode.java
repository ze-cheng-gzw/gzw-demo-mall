package com.demo.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by han
 */
public enum ApiResponseCode {
    SUCCESS(0, "成功"),
    FAILURE(1, "失败"),
    ERROR(2, "错误"),
    WARNING(301, "警告"),
    PROMPT(401, "提示"),
    //自定义异常由301编号开始
    //系统或第三方类库的异常转换的编码由351开始
    UN_KNONW_ERROR(1000, "未知异常"),
    DB_ERROR(1002, "数据库操作异常"),
    DB_TIMEOUT(1003, "数据库访问超时"),
    NETWORK_EXP(1004, "网络异常"),
    EXTERNAL_SERVICE__EXP(1005, "外部服务器异常"),
    EXTERNAL_SERVICE_TIMEOUT(1006, "外部服务器超时"),
    API_OVERDUE(1007, "API已过期"),
    METHOD_UN_IMPL(1008, "当前操作未实现"),
    JSONP_NOT_SUP(1009, "不支持Jsonp请求"),
    ILLEGAL_REQ(1010, "非法请求"),
    INVALID_OP(1011, "无效操作"),
    DATA_ERR(2000, "数据错误"),
    DATA_EXIST(2002, "数据不存在"),
    DATA_FORMAT_ERR(2003, "数据格式错误"),
    DATA_TYPE_ERR(2004, "数据类型错误"),
    DATA_REPAT_ERR(2005, "数据重复"),
    DATA_UN_ACCESS(2006, "数据没有授权"),
    DATA_OVER_LIMIT(2007, "数据数量超出限制"),
    PARA_ERR(3000, "参数错误"),
    PARA_MISSING_ERR(3002, "缺少参数"),
    PARA_NIL(3003, "参数不能为空值"),
    PARA_FORMAT_ERR(3004, "参数格式错误"),
    PARA_OUT_RANGE(3005, "参数值超出允许范围"),
    TOKEN_INVALID(3006, "令牌无效，请重新登录"),
    TOKEN_IS_USE(3007, "令牌已使用"),
    TOKEN_TIMEOUT(3008, "令牌过期"),
    SIGNATURE_INVALID(3009, "签名无效"),
    TIME_OUT_RANGE(3010, "时间戳超出允许范围"),
    CHR_ILLEGAL(3011, "存在非法字符"),
    PARA_OUT_LEN(3012, "参数值长度超过限制"),
    HAS_SUBTLE(3013, "存在有敏感词"),
    SIGNATURE_ERROR(3014, "签名错误"),
    CAPTCHA_ERROR(3015, "验证码错误"),
    APP_VERSION_FORCE_UPDATE(3020, "请及时更新APP版本"),
    ACCESS_DENIED(4000, "无权限"),
    NOT_LOG(4002, "未登录或已过期"),
    IP_LIMIT(4003, "IP限制"),
    DATA_NOT_EXIST(2002, "数据不存在"),
    API_ACCESS_DENIED(4004, "API未授权"),
    PARSEFAIL(5001, "解析错误"),
    UPLOADFAIL(5002, "上传失败"),
    REGISTERFAUL(5003, "用户账号注册失败"),
    LOGINFAIL(5004, "本地登录失败"),
    ACCOUNTEXSIT(5005, "前端提示消息"),
    ONT_ORDER(5006,"订单不存在"),
    ORDER_NOT_PAY(5007,"订单未支付"),
    ORDER_APY_TIME_OUT(5008,"订单超时");
    private int code;
    private String message;

    ApiResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private Map<Integer, ApiResponseCode> map = null;

    private void initMap() {
        if (map == null) {
            map = new HashMap<>();
            ApiResponseCode[] values = ApiResponseCode.values();
            for (ApiResponseCode item : values) {
                map.put(item.code, item);
            }
        }
    }

    public ApiResponseCode getByCode(int code) {
        initMap();
        return map.get(code);
    }

    public String getMessageByCode(int code) {
        ApiResponseCode item = getByCode(code);
        if (item == null) {
            BizException.fail("无法识别的code");
        }
        return item.message;
    }
}
