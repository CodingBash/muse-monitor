package com.codingbash.musemonitor.socketserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Heroku URL: https://muse-monitor-socketserver.herokuapp.com/muse-ws
 * Local URL: http://localhost:8080/muse-monitor-socketserver/muse-ws
 */
@SpringBootApplication
public class MuseMonitorSocketserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuseMonitorSocketserverApplication.class, args);
	}
}
