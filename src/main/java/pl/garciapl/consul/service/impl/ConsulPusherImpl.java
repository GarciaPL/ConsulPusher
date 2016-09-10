package pl.garciapl.consul.service.impl;

import com.google.common.collect.ImmutableMap;
import org.cfg4j.source.context.propertiesprovider.PropertiesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.garciapl.consul.service.ConsulPusher;
import pl.garciapl.consul.service.config.ConsulConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

@Component
public class ConsulPusherImpl implements ConsulPusher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsulPusherImpl.class);

    @Autowired
    private ConsulConfiguration consulConfiguration;

    @Override
    public void fillConsul(Path file, String profile, String key, String value) {
        if (file != null) {
            try (InputStream input = new FileInputStream(file.toFile())) {

                Properties properties = new Properties();
                PropertiesProvider propertiesProvider = consulConfiguration.propertiesProviderSelector().getProvider(file.getFileName().toString());
                properties.putAll(propertiesProvider.getProperties(input));

                sendProperty(profile, properties);
            } catch (IOException e) {
                LOGGER.error("Unable to load properties from file {} due to {}", file, e);
            }
        } else {
            Properties properties = new Properties();
            properties.putAll(ImmutableMap.<String, String>builder().put(key, value).build());
            sendProperty(profile, properties);
        }
    }

    private void sendProperty(String profile, Properties properties) {
        for (Map.Entry<Object, Object> prop : properties.entrySet()) {
            LOGGER.info("Put {} => {}", prop.getKey().toString(), prop.getValue().toString());
            consulConfiguration.consul().keyValueClient().putValue(profile + "/" + prop.getKey().toString(), prop.getValue().toString());
        }
    }
}
