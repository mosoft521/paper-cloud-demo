package com.gmail.mosoft521.paper.vo;

import java.io.Serializable;

public class CommonDictVO implements Serializable {
    private static final long serialVersionUID = 2167443924971169548L;
    private Long dictId;
    private String dictCode;
    private String dictCodeText;
    private String iconMongodbKey;
    private String iconMongodbUrl;
    private Integer disabled;

    public CommonDictVO() {
    }

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictCodeText() {
        return dictCodeText;
    }

    public void setDictCodeText(String dictCodeText) {
        this.dictCodeText = dictCodeText;
    }

    public String getIconMongodbKey() {
        return iconMongodbKey;
    }

    public void setIconMongodbKey(String iconMongodbKey) {
        this.iconMongodbKey = iconMongodbKey;
    }

    public String getIconMongodbUrl() {
        return iconMongodbUrl;
    }

    public void setIconMongodbUrl(String iconMongodbUrl) {
        this.iconMongodbUrl = iconMongodbUrl;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }
}