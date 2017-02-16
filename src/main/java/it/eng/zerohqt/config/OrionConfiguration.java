package it.eng.zerohqt.config;

import it.eng.zerohqt.orion.OrionContextConsumer;
import it.eng.zerohqt.orion.OrionContextConsumerExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ascatox on 10/02/17.
 */
@Configuration
public class OrionConfiguration {

    @Value("${orion.server.url}")
    public String orionServerUrl;
    @Value("${orion.token}")
    public String orionToken;
    @Value("${orion.service}")
    public String orionService;
    @Value("${orion.service.path}")
    public String orionServicepath;
    @Value("${orion.reference}")
    public String orionReference;


    @Bean
    public OrionContextConsumer orionContextConsumer() {
        return new OrionContextConsumerExecutor(orionServerUrl, orionToken, orionService, orionServicepath, orionReference);
    }

}



