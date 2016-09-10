package pl.garciapl.consul.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import pl.garciapl.consul.domain.ProfileEntry;
import pl.garciapl.consul.domain.Profiles;
import pl.garciapl.consul.service.Filters;

import java.nio.file.Path;
import java.util.ArrayList;

@Component
public class FiltersImpl implements Filters {

    @Override
    public ArrayList<ProfileEntry> filterProfilesForFile(final Path file, Profiles profiles) {
        return new ArrayList<ProfileEntry>(Collections2.filter(profiles.getProfiles(), new Predicate<ProfileEntry>() {
            @Override
            public boolean apply(ProfileEntry input) {
                String baseName = FilenameUtils.getBaseName(file.getFileName().toFile().getName());
                if (baseName.equals(input.getName())) {
                    return true;
                }
                return false;
            }
        }));
    }

    @Override
    public ArrayList<ProfileEntry> filterProfilesForDirectory(final String directory, Profiles profiles) {
        return new ArrayList<ProfileEntry>(Collections2.filter(profiles.getProfiles(), new Predicate<ProfileEntry>() {
            @Override
            public boolean apply(ProfileEntry input) {
                if (directory.contains("/" + input.getName() + "/")) {
                    return true;
                }
                return false;
            }
        }));
    }

    @Override
    public ArrayList<ProfileEntry> filterProfilesForInteractive(final String profile, Profiles profiles) {
        return new ArrayList<ProfileEntry>(Collections2.filter(profiles.getProfiles(), new Predicate<ProfileEntry>() {
            @Override
            public boolean apply(ProfileEntry input) {
                if (profile.equals(input.getName())) {
                    return true;
                }
                return false;
            }
        }));
    }

}
