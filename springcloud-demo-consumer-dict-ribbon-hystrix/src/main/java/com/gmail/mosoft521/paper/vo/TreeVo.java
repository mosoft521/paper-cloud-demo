package com.gmail.mosoft521.paper.vo;

import java.io.Serializable;

// Alternative format of the node (id & parent are required)
/*{
id          : "string" // required
parent      : "string" // required
text        : "string" // node text
icon        : "string" // string for custom
state       : {
    opened    : boolean  // is the node open
    disabled  : boolean  // is the node disabled
    selected  : boolean  // is the node selected
},
li_attr     : {}  // attributes for the generated LI node
a_attr      : {}  // attributes for the generated A node
}*/
public class TreeVo implements Serializable {
    private static final long serialVersionUID = -6241419839204981550L;
    private String id;
    private String parent;
    private String text;
    private String icon;
    private String state;
    private String liAttr;
    private String aAttr;

    public TreeVo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLiAttr() {
        return liAttr;
    }

    public void setLiAttr(String liAttr) {
        this.liAttr = liAttr;
    }

    public String getaAttr() {
        return aAttr;
    }

    public void setaAttr(String aAttr) {
        this.aAttr = aAttr;
    }
}
