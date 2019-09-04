package com.test.recyclerdemo.bean;

import java.util.List;

/**
 * 数据实体类 外部类是省份数据，内部类是城市数据
 * 外部类继承了ParentItem类，表示这个类中的数据是要展示列表的父级item的数据
 */
public class Province extends ParentItem {
    //省份名称
    private String provinceName;
    //城市列表
    private List<City> cities;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    //内部类继承了ChildItem类，表示这个类中的数据是要展示列表的子级item的数据
    public static class City extends ChildItem {
        //城市名称
        private String cityName;

        public String getProvinceName() {
            return super.parentName;
        }

        public void setProvinceName(String provinceName) {
            super.parentName = provinceName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        @Override
        public String toString() {
            return "City{" +
                    "cityName='" + cityName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Province{" +
                "isExpand=" + isExpand +
                ", provinceName='" + provinceName + '\'' +
                ", cities=" + cities +
                '}';
    }
}
