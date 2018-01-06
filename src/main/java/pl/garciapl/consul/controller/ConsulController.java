package pl.garciapl.consul.controller;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.annotation.PostConstruct;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import pl.garciapl.consul.domain.Profiles;
import pl.garciapl.consul.service.FileService;
import pl.garciapl.consul.service.InteractiveService;

@Controller
@EnableAutoConfiguration
@ComponentScan(basePackages = "pl.garciapl.consul")
public class ConsulController implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsulController.class);

    @Value("${mode}")
    private String mode;

    @Value("${configPath}")
    private String configPath;

    @Autowired
    private Gson gson;

    @Autowired
    private FileService fileService;

    @Autowired
    private InteractiveService interactiveService;

    private Profiles profiles;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ConsulController.class, args);
    }

    public void run(String... args) throws Exception {
        if (!StringUtils.isEmpty(mode)) {
            if (mode.equals("file")) {
                fileService.processFiles(configPath, profiles);
            } else if (mode.equals("interactive")) {
                interactiveService.processInteractive(args, profiles);
            } else {
                LOGGER.error("Wrong mode. Accepted mode is file or interactive");
            }
        } else {
            LOGGER.error("Unspecified mode");
        }
    }

    @PostConstruct
    public void fetchProfiles() throws URISyntaxException, IOException {
        LOGGER.info("Loading profiles from profiles.json");
        String content = IOUtils.toString(this.getClass().getResourceAsStream("/profiles.json"), Charsets.UTF_8);
        Profiles profiles = gson.fromJson(content, Profiles.class);
        if (profiles != null) {
            this.profiles = profiles;
        } else {
            LOGGER.error("Error during converting profiles.json to object");
        }
    }

}
