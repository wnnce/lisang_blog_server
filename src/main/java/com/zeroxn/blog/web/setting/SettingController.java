package com.zeroxn.blog.web.setting;

import cn.dev33.satoken.annotation.SaIgnore;
import com.zeroxn.blog.core.utils.BaseUtil;
import com.zeroxn.blog.core.utils.Result;
import com.zeroxn.blog.entity.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ran
 * @date
 * Setting 站点设置控制层
 */
@RestController
@RequestMapping("/site")
public class SettingController {
    private final SettingService settingService;
    public SettingController(SettingService settingService){
        this.settingService = settingService;
    }

    /**
     * 获取所有站点设置
     * @return 返回站点设置
     */
    //拦截器放行接口的方式不支持根据请求方式区分 所以使用 @SaIgnore 来忽略鉴权
    @SaIgnore
    @GetMapping("/setting")
    public Result<Setting> getSiteSetting(){
        Setting setting = settingService.getSiteSetting();
        return Result.success(setting);
    }

    /**
     * 获取站点设置中的about属性
     * @return 返回String类型的about参数
     */
    @SaIgnore
    @GetMapping("/about")
    public Result<String> getSiteAbout(){
        String about = settingService.getSiteAbout();
        return Result.success(about);
    }

    /**
     * 更新站点设置
     * @param setting 入参封装的对象
     * @return 返回操作成功或失败
     */
    @PutMapping("/setting")
    public Result<String> updateSiteSetting(@RequestBody Setting setting){
        //检查必需属性是否为空
        if(!BaseUtil.checkObjectFieldIsNull(setting, "id", "notice", "year", "protocol")){
            settingService.updateSiteSetting(setting);
            return Result.success("更新成功");
        }
        return Result.error("必填参数不能为空");
    }

    /**
     * 更新站点设置中的about属性 （关于我）
     * @param setting 入参封装的对象
     * @return 返回操作成功或失败
     */
    @PutMapping("/about")
    public Result<String> updateSiteAbout(@RequestBody Setting setting){
        //检查about属性是否为空
        if(!BaseUtil.checkObjectFieldIsNull(setting, "about")){
            settingService.updateSiteAbout(setting.getAbout());
            return Result.success("更新成功");
        }
        return Result.error("不能为空");
    }
}
