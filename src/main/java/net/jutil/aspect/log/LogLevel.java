package net.jutil.aspect.log;

import java.util.Objects;

public enum LogLevel {

    TRACE("trace"),
    DEBUG("debug"),
    INFO("info"),
    WARN("warn"),
    ERROR("error");


    private String level;

    LogLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    /**
     * of
     *
     * @param str str
     * @return {@link LogLevel}
     */
    public static LogLevel of(String str){
        LogLevel[] values = LogLevel.values();
        for (LogLevel value : values) {
            if (Objects.equals(value.getLevel(), str)){
                return value;
            }
        }
        return null;
    }
}
