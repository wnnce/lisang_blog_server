package com.zeroxn.blog.web.setting;

import com.zeroxn.blog.entity.Setting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ran
 * @date
 * 操作数据库中的 tb_setting 表
 */
@Mapper
public interface SettingMapper {
    Setting getSiteSetting();
    String getSiteAbout();
    void updateSiteSetting(Setting setting);
    void updateSiteAbout(@Param("about") String about);
}
