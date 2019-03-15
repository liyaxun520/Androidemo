package com.example.http.entity;

public class TradeCardEntity {
    private String corpid; //企业 ID  corpid  是  String(32) 8888888888888888  消费平台的企业 ID，分支平台 ID 为自身
    private String mch_id;  //商户号  mch_id  是  String(32) 1900000109  消费平台的商户号
    private String device_id;  //设备号  device_id  是  String(32) 013467007045764  消费平台的终端设备号
    private String nonce_str;  //随机字符串  nonce_str  是  String(32) 5K8264ILTKCH16CQ2502SI8ZNMTM67VS随机字符串，不长于 32 位。
    private String sign;  //签名  sign  是  String(32) C380BEC2BFD727A4B6845133519F3AD6签名
    private String sign_type;  //签名类型  sign_type  否  String(32) HMAC-SHA256  签名类型，默认为 MD5
    private String body;  //商品描述  body  是  String(128)餐费  消费商品简单描述
    private String attach;  //附加数据  attach  否  String(12说明  附加数据，在查询 API 和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
    private String dev_trade_no;//终端订单号  dev_trade_no  是  String(32) 1217752501201407033233368018终端订单号，要求 32 个字符内，只能是数字、大小写字母且在同一个终端号下唯一。
    private String total_fee;  //订单金额  total_fee  是  Int  888  订单总金额，单位为分，只能为整数。参数值不能带小数。
    private String fee_type;  //货币类型  fee_type  否  String(16) CNY  符合ISO4217标准的三位字母代码，默认人民币：CNY
    private String create_ip;  //终端 IP  create_ip  是  String(16) 8.8.8.8  调用 API 的机器 IP
    private String time_start;  //交易起始时间  time_start  否  String(14) 20091225091010  订单生成时间，格式为 yyyyMMddHHmmss，如 2009 年 12 月 25 日 9 点 10 分 10 秒表示为 20091225。091010。
    private String time_expire;  //交易结束时间  time_expire  否  String(14) 20091227091010  订单失效时间，格式为 yyyyMMddHHmmss，如 2009 年 12 月 27 日 9 点 10 分 10 秒表示为 20091227091010。注意：最短失效时间间隔需大于 1 分钟
    /**
     * 授权码  auth_code  是  String(12
     * 8)
     * 120061098828009406 扫码支付授权码，设备读取用户（APP）小程序
     * 中的条码或者二维码信息。（注：行业码用户二
     * 维码信息，以 55 开头。如果是刷卡，以 60 开头，
     * 后面跟卡号信息）
     */
    private String auth_code;
    private String scene_info;

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getDev_trade_no() {
        return dev_trade_no;
    }

    public void setDev_trade_no(String dev_trade_no) {
        this.dev_trade_no = dev_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getCreate_ip() {
        return create_ip;
    }

    public void setCreate_ip(String create_ip) {
        this.create_ip = create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public String getScene_info() {
        return scene_info;
    }

    public void setScene_info(String scene_info) {
        this.scene_info = scene_info;
    }

    @Override
    public String toString() {
        return "TradeCardEntity{" +
                "corpid='" + corpid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", device_id='" + device_id + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", body='" + body + '\'' +
                ", attach='" + attach + '\'' +
                ", dev_trade_no='" + dev_trade_no + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", fee_type='" + fee_type + '\'' +
                ", create_ip='" + create_ip + '\'' +
                ", time_start='" + time_start + '\'' +
                ", time_expire='" + time_expire + '\'' +
                ", auth_code='" + auth_code + '\'' +
                ", scene_info='" + scene_info + '\'' +
                '}';
    }
}
