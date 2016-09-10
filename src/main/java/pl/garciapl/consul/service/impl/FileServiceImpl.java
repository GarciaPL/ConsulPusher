package pl.garciapl.consul.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pl.garciapl.consul.domain.ProfileEntry;
import pl.garciapl.consul.domain.Profiles;
import pl.garciapl.consul.service.ConsulPusher;
import pl.garciapl.consul.service.FileService;
import pl.garciapl.consul.service.Filters;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

@Component
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private Filters filters;

    @Autowired
    private ConsulPusher consulPusher;

    @Override
    public void processFiles(String configPath, final Profiles profiles) throws IOException {
        if (!StringUtils.isEmpty(configPath)) {
            Path start = Paths.get(configPath);
            if (start.toFile().exists()) {
                Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                        if (file.toString().endsWith("properties")) {
                            checkProfile(file, profiles);
                        }

                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {
                LOGGER.error("Path {} does not exist", start.toAbsolutePath());
            }
        } else {
            LOGGER.error("Path {} does not exist", configPath);
        }
    }

    private void checkProfile(Path file, Profiles profiles) {
        ArrayList<ProfileEntry> fileFilters = filters.filterProfilesForFile(file, profiles);
        if (!fileFilters.isEmpty()) {
            LOGGER.info("Process file {}", file.toAbsolutePath());
            for (ProfileEntry profileEntry : fileFilters) {
                LOGGER.info("File entry for profile {}", profileEntry.getName());
                consulPusher.fillConsul(file, profileEntry.getName(), null, null);
            }
            LOGGER.info("\n");
        } else {
            ArrayList<ProfileEntry> directoryFilters = filters.filterProfilesForDirectory(file.toAbsolutePath().toString(), profiles);
            if (!directoryFilters.isEmpty()) {
                LOGGER.info("Process directory {}", file.toAbsolutePath());
                for (ProfileEntry profileEntry : directoryFilters) {
                    LOGGER.info("Directory entry for profile {}", profileEntry.getName());
                    consulPusher.fillConsul(file, profileEntry.getName(), null, null);
                }
                LOGGER.info("\n");
            } else {
                LOGGER.error("Unknown file or directory {}. You might add it to profiles.json", file.toAbsolutePath().toString());
            }
        }
    }
}
