# aspect-log-spring-boot-starter
基于spring-boot-aop封装的基于注解实现入参出参日志

## 用法
1. 在需要记录日志的方法上加入注解`@AopLog`;
2. 在application配置中加入`spring.aop.log=debug`, 来指定aop日志的级别，框架自带的日志处理器会按指定的级别输出日志;
3. 如果对默认的日志处理器不满意，可以自定义日志处理器，只需要实现`AopLogHandle`接口，并且注入为SpingBean，框架就会自动使用自定义的日志处理器;
