package com.zeroxn.blog.web.setting;

import com.zeroxn.blog.entity.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ran
 * @date
 * SettingService的实现类
 */
@Service
public class SettingServiceImpl implements SettingService {
    private final SettingMapper settingMapper;
    public SettingServiceImpl(SettingMapper settingMapper){
        this.settingMapper = settingMapper;
    }
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
