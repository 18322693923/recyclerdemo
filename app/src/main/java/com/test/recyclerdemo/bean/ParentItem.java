package com.test.recyclerdemo.bean;

/**
 * 父级Item实体类
 */
public class ParentItem {
    /**
     * 是否展开，默认否
     */
    protected boolean isExpand = false;
    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }
}
