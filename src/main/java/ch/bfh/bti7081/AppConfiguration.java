package ch.bfh.bti7081;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author siegn2
 */
@Configuration
@EnableVaadin
@ComponentScan("ch.bfh.bti7081.model")
@ComponentScan("ch.bfh.bti7081.presenter")
@ComponentScan("ch.bfh.bti7081.view")
@EnableJpaRepositories("ch.bfh.bti7081.model.repositories")
public class AppConfiguration {
}
