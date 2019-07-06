package com.penn.factory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.penn.constant.Constant;

public class IAcsClientFactory {

    private static IAcsClient acsClient = null;

    /**
     * 获取一个IAcsClient对象
     *
     * @return
     */
    public static IAcsClient getInstance() {
        try {
            synchronized (IAcsClientFactory.class) {
                if (acsClient == null) {
                    DefaultProfile.addEndpoint(Constant.ENDPOINTNAME, Constant.REGIONID, Constant.PRODUCT, Constant.DOMAIN);
                    // 创建DefaultAcsClient实例并初始化
                    DefaultProfile profile = DefaultProfile.getProfile(Constant.REGIONID, Constant.ACCESS_KEY_ID, Constant.ACCESS_KEY_SECRET);
                    acsClient = new DefaultAcsClient(profile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acsClient;
    }

}
