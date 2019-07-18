package com.gmail.mosoft521.paper.vo;

import java.io.Serializable;

public class TreeState implements Serializable {
    private static final long serialVersionUID = 5523721504105907079L;
    private boolean opened;
    private boolean disabled;
    private boolean selected;

    public TreeState() {
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
