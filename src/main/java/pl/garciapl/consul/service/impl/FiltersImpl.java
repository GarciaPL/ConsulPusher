package pl.garciapl.consul.service.impl;

import com.google.common.collect.Collections2;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import pl.garciapl.consul.domain.ProfileEntry;
import pl.garciapl.consul.domain.Profiles;
import pl.garciapl.consul.service.Filters;

@Component
public class FiltersImpl implements Filters {

    @Override
    public List<ProfileEntry> filterProfilesForFile(final Path file, Profiles profiles) {
        return new ArrayList<>(Collections2.filter(profiles.getProfiles(), input -> {
            String baseName = FilenameUtils.getBaseName(file.getFileName().toFile().getName());
            return baseName.equals(input.getName());
        }));
    }

    @Override
    public List<ProfileEntry> filterProfilesForDirectory(final String directory, Profiles profiles) {
        return new ArrayList<>(Collections2.filter(profiles.getProfiles(), input -> directory.contains("/" + input.getName() + "/")));
    }

    @Override
    public List<ProfileEntry> filterProfilesForInteractive(final String profile, Profiles profiles) {
        return new ArrayList<>(Collections2.filter(profiles.getProfiles(), input -> profile.equals(input.getName())));
    }

}
