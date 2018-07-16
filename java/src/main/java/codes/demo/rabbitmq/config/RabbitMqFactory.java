package codes.demo.rabbitmq.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqFactory {

	@Autowired
	private RabbitmqLoanConfig rabbitmqLoanConfig;

	@Autowired
	private RabbitmqNdesConfig rabbitmqNdesConfig;


	@Bean(value = "loanTemplate")
	public RabbitTemplate loanTemplate() {
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setAddresses(rabbitmqLoanConfig.getAddresses());
		factory.setUsername(rabbitmqLoanConfig.getUsername());
		factory.setPassword(rabbitmqLoanConfig.getPassword());
		factory.setVirtualHost(rabbitmqLoanConfig.getVirtualHost());
		factory.setPublisherConfirms(rabbitmqLoanConfig.isPublisherConfirms());
		return new RabbitTemplate(factory);
	}

	@Bean(value = "ndesTemplate")
	public RabbitTemplate ndesTemplate() {
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setAddresses(rabbitmqNdesConfig.getAddresses());
		factory.setUsername(rabbitmqNdesConfig.getUsername());
		factory.setPassword(rabbitmqNdesConfig.getPassword());
		factory.setVirtualHost(rabbitmqNdesConfig.getVirtualHost());
		factory.setPublisherConfirms(rabbitmqNdesConfig.isPublisherConfirms());
		return new RabbitTemplate(factory);
	}

}
