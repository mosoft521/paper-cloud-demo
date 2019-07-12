package com.gmail.mosoft521.paper.constants;

public enum DisabledEnum {
    //    //有效
    //    public static final int ENABLED = 0;
    //    //失效
    //    public static final int DISABLED = 1;
    ENABLED(0, "有效"),
    DISABLED(1, "失效");

    private Integer code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    DisabledEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static DisabledEnum valueOf(int code) {
        for (DisabledEnum disabledType : DisabledEnum.values()) {
            if (code == disabledType.getCode()) {
                return disabledType;
            }
        }
        return null;
    }
}
