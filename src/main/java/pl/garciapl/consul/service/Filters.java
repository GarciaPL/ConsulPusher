package pl.garciapl.consul.service;

import java.nio.file.Path;
import java.util.List;
import pl.garciapl.consul.domain.ProfileEntry;
import pl.garciapl.consul.domain.Profiles;

public interface Filters {

    public List<ProfileEntry> filterProfilesForFile(final Path file, Profiles profiles);

    public List<ProfileEntry> filterProfilesForDirectory(final String directory, Profiles profiles);

    public List<ProfileEntry> filterProfilesForInteractive(final String profile, Profiles profiles);
}
