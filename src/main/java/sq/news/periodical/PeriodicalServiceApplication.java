package sq.news.periodical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
public class PeriodicalServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PeriodicalServiceApplication.class, args);
	}

//	@Bean
//	public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
//		HibernateJpaSessionFactoryBean fact = new HibernateJpaSessionFactoryBean();
//		fact.setEntityManagerFactory(emf);
//		return fact;
//	}
}
