package fr.example.technicaloffer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import io.r2dbc.spi.ConnectionFactory;

@Configuration
public class UserManagementConfig {

	@Value("classpath:db/user-table.sql")
	private Resource userTableFile;

	/**
	 * Method to create the user table in h2 embedded database at startup
	 * 
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	ConnectionFactoryInitializer databaseInitializer(ConnectionFactory connectionFactory) {
		ConnectionFactoryInitializer dbInitializer = new ConnectionFactoryInitializer();
		dbInitializer.setConnectionFactory(connectionFactory);
		dbInitializer.setDatabasePopulator(new ResourceDatabasePopulator(userTableFile));
		return dbInitializer;
	}

}
