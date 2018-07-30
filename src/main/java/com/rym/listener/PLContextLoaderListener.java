package com.rym.listener;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.rym.server.PipeLineServer;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PLContextLoaderListener extends ContextLoaderListener {
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		PipeLineServer plserver =  WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()).getBean(PipeLineServer.class);
		Timer timer =new Timer();
    	timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					plserver.init();
				} catch (Exception e) {
					log.error("socket server start error，{}",e.getMessage());
				}
			}
		}, 10);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		log.debug("上下文开始销毁");
		super.contextDestroyed(event);
	}
}
