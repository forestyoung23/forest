package com.forest.common;

/**
 * @author Forest Dong
 * @date 2022年08月28日 15:37
 */
public class URL {
    private URLAddress urlAddress;
    private ServiceInfo serviceInfo;

    public URLAddress getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(URLAddress urlAddress) {
        this.urlAddress = urlAddress;
    }

    public ServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }
}
