//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.dev33.satoken.spring;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.context.SaTokenContext;
import cn.dev33.satoken.json.SaJsonTemplate;
import cn.dev33.satoken.spring.json.SaJsonTemplateForJackson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 覆盖jar包中的 SaBeanRegister 原jar包中 getSaJsonTemplateForJackson 必须依赖jackSon，手动注入自定义template会报错
 * 现在添加了 @ConditionalOnMissingBean(SaJsonTemplate.class) 注解，如果用户没有注入才会使用默认值
 */
public class SaBeanRegister {
    public SaBeanRegister() {
    }

    @Bean
    @ConfigurationProperties(
            prefix = "sa-token"
    )
    public SaTokenConfig getSaTokenConfig() {
        return new SaTokenConfig();
    }

    @Bean
    public SaTokenContext getSaTokenContext() {
        return new SaTokenContextForSpring();
    }

    @Bean
    @ConditionalOnMissingBean(SaJsonTemplate.class)
    public SaJsonTemplate getSaJsonTemplateForJackson() {
        return new SaJsonTemplateForJackson();
    }
}

