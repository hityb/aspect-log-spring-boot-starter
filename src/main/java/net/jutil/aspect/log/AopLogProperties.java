package net.jutil.aspect.log;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Aop日志属性
 *
 * @author TYB
 * @date 2019/12/21
 */
@ConfigurationProperties(prefix = "spring.aop.log")
public class AopLogProperties {

    /**
     * 日志级别
     */
    private String level = "debug";

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
