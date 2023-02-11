package com.zeroxn.blog.service.impl;

import com.zeroxn.blog.entity.Setting;
import com.zeroxn.blog.mapper.SettingMapper;
import com.zeroxn.blog.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ran
 * @date
 * SettingService的实现类
 */
@Service
public class SettingServiceImpl implements SettingService {
    @Autowired
    private SettingMapper settingMapper;
    @Override
    public Setting getSiteSetting() {
        return settingMapper.getSiteSetting();
    }

    @Override
    public String getSiteAbout() {
        return settingMapper.getSiteAbout();
    }

    @Override
    public void updateSiteSetting(Setting setting) {
        settingMapper.updateSiteSetting(setting);
    }

    @Override
    public void updateSiteAbout(String about) {
        settingMapper.updateSiteAbout(about);
    }
}
