package com.forest.common;

/**
 * @author Forest Dong
 * @date 2022年08月28日 15:40
 */
public class ServiceInfo {
    private String interfaceClass;

    public ServiceInfo(String interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(String interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
