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
    protected String orionServerUrl;
    @Value("${orion.token}")
    protected String orionToken;
    @Value("${orion.service}")
    protected String orionService;
    @Value("${orion.service.path}")
    protected String orionServicepath;
    @Value("${orion.reference}")
    protected String orionReference;


    @Bean
    public OrionContextConsumer orionContextConsumer() {
        return new OrionContextConsumerExecutor(orionServerUrl, orionToken, orionService, orionServicepath, orionReference) {
        };
    }

}



