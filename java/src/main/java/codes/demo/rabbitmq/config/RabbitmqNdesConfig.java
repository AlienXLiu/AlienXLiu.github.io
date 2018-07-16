package codes.demo.rabbitmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.rabbitmq.ndes")
public class RabbitmqNdesConfig {

	private String addresses;
	private String username;
	private String password;
	private String virtualHost;
	private boolean publisherConfirms;

	public String getAddresses() {
		return addresses;
	}

	public void setAddresses(String addresses) {
		this.addresses = addresses;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	public boolean isPublisherConfirms() {
		return publisherConfirms;
	}

	public void setPublisherConfirms(boolean publisherConfirms) {
		this.publisherConfirms = publisherConfirms;
	}
}
