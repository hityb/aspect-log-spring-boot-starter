package net.jutil.aspect.log.start;

import net.jutil.aspect.log.AopLogHandle;
import net.jutil.aspect.log.AopLogProperties;
import net.jutil.aspect.log.DefaultAopLogHandle;
import net.jutil.aspect.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Aop日志自动配置
 *
 * @author TYB
 * @date 2019/12/21
 */
@Configuration
@AutoConfigureAfter(AopAutoConfiguration.class)
@ComponentScan("net.jutil.aspect.log")
@EnableConfigurationProperties(AopLogProperties.class)
public class AopLogAutoConfiguration {

    @Autowired
    private AopLogProperties aopLogProperties;

    @Bean
    @ConditionalOnMissingBean(AopLogHandle.class)
    public AopLogHandle aopLogHandle(){
        LogLevel logLevel = LogLevel.of(aopLogProperties.getLevel());
        AopLogHandle aopLogHandle = new DefaultAopLogHandle(logLevel);
        return aopLogHandle;
    }
}
