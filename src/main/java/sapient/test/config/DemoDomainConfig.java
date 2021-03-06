package sapient.test.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "sapient.test.demo.domain.repository")
public class DemoDomainConfig {

	private static final String PACKAGES_TO_SCAN = "sapient.test.demo.domain";

	@Bean
	@ConfigurationProperties(prefix = "sapient.test.demo.datasource")
	public DataSource getDataSource() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * Because we're defining a custom EntityManagerFactory, Spring Boot
	 * auto-configuration switches off its entity manager
	 *
	 * @return
	 */
	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource datasource) {

		boolean generateDdl = false;
		boolean showSql = true;
		String databaseDialect = "org.hibernate.dialect.H2Dialect";

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		// vendorAdapter.setDatabase(Database.ORACLE);
		vendorAdapter.setDatabasePlatform(databaseDialect);
		vendorAdapter.setGenerateDdl(generateDdl);
		vendorAdapter.setShowSql(showSql);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(PACKAGES_TO_SCAN);
		factory.setDataSource(datasource);
		// use enhanced generators for sequences to address the negative IDs
		// generated by hibernate.
		// see
		// https://vladmihalcea.com/2014/07/15/from-jpa-to-hibernates-legacy-and-enhanced-identifier-generators/
		Properties extraJpaProperties = new Properties();
		extraJpaProperties.setProperty("hibernate.id.new_generator_mappings", "true");

		factory.setJpaProperties(extraJpaProperties);
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);

		return txManager;
	}

}
