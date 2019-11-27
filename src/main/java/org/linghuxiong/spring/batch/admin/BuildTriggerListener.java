package org.linghuxiong.spring.batch.admin;


import lombok.extern.slf4j.Slf4j;
import org.linghuxiong.spring.batch.admin.quartz.QuartzTriggerManager;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author eric
 * <p>
 * 监听是用于回调的：：回调的时机就是容器初始化后
 */
@Slf4j
public class BuildTriggerListener implements SpringApplicationRunListener {

    public BuildTriggerListener(SpringApplication sa, String[] args) {
        super();
    }

    @Override
    public void starting() {
        //1
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        //2
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        //3
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        //4
    }

    //这个是容器启动之后
    @Override
    public void started(ConfigurableApplicationContext context) {
        //5
        log.info("@@ batch server started @@");

        QuartzTriggerManager quartzTriggerManager = null;

        try {
            quartzTriggerManager = context.getBean(QuartzTriggerManager.class);
        } catch (NoSuchBeanDefinitionException e) {
            return;
        }

        try {

            if (quartzTriggerManager != null) {
                quartzTriggerManager.initTriggers();
            } else {
                log.warn("quartzTriggerManager is not existed!");
                return;
            }
        } catch (Exception e) {
            log.error("cannot initTriggers", e);
        }
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        //6
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }
}
