package com.gmail.mosoft521.paper.constants;

public enum DisabledType {
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

    DisabledType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static DisabledType DisabledType(int code) {
        for (DisabledType disabledType : DisabledType.values()) {
            if (code == disabledType.getCode()) {
                return disabledType;
            }
        }
        return null;
    }
}
