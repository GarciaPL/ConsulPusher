package pl.garciapl.consul.service.config;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;
import org.cfg4j.source.context.propertiesprovider.JsonBasedPropertiesProvider;
import org.cfg4j.source.context.propertiesprovider.PropertiesProviderSelector;
import org.cfg4j.source.context.propertiesprovider.PropertyBasedPropertiesProvider;
import org.cfg4j.source.context.propertiesprovider.YamlBasedPropertiesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsulConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsulConfiguration.class);

    @Value("${consul.host}")
    private String host;

    @Value("${consul.port}")
    private int port;

    @Bean
    public Consul consul() {
        LOGGER.info("Connecting to Consul at " + host + ":" + port);
        return Consul.builder().withHostAndPort(HostAndPort.fromParts(host, port)).build();
    }

    @Bean
    public PropertiesProviderSelector propertiesProviderSelector() {
        return new PropertiesProviderSelector(
                new PropertyBasedPropertiesProvider(), new YamlBasedPropertiesProvider(), new JsonBasedPropertiesProvider()
        );
    }

}
