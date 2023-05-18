package com.zeroxn.blog.web.setting;

import com.zeroxn.blog.entity.Setting;

/**
 * @author ran
 * @date
 */
public interface SettingService {
    /**
     * 获取站点配置
     * @return 返回站点配置
     */
    Setting getSiteSetting();

    /**
     * 获取站点配置的about属性
     * @return 返回String类型的about参数
     */
    String getSiteAbout();

    /**
     * 更新站点配置
     * @param setting
     */
    void updateSiteSetting(Setting setting);

    /**
     * 更新站点配置的about属性
     * @param about
     */
    void updateSiteAbout(String about);
}
