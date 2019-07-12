package com.gmail.mosoft521.paper.entity;

import java.util.Date;

public class CommonXzqh {
    private Long dictId;

    private String dictCode;

    private String dictCodeText;

    private String iconMongodbKey;

    private Long creater;

    private Date createTime;

    private Long modifier;

    private Date modifyTime;

    private Long version;

    private Integer disabled;

    private Integer adminRank;

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
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }

    public String getDictCodeText() {
        return dictCodeText;
    }

    public void setDictCodeText(String dictCodeText) {
        this.dictCodeText = dictCodeText == null ? null : dictCodeText.trim();
    }

    public String getIconMongodbKey() {
        return iconMongodbKey;
    }

    public void setIconMongodbKey(String iconMongodbKey) {
        this.iconMongodbKey = iconMongodbKey == null ? null : iconMongodbKey.trim();
    }

    public Long getCreater() {
        return creater;
    }

    public void setCreater(Long creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Integer getAdminRank() {
        return adminRank;
    }

    public void setAdminRank(Integer adminRank) {
        this.adminRank = adminRank;
    }
}