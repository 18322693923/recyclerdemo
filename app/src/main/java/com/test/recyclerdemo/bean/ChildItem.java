package com.test.recyclerdemo.bean;

/**
 * 子级Item实体类
 */
public class ChildItem {
    /**
     * 该子级类所对应父级类的名称
     */
    protected String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
