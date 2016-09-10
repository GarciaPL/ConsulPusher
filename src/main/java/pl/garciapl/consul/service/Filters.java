package pl.garciapl.consul.service;

import pl.garciapl.consul.domain.ProfileEntry;
import pl.garciapl.consul.domain.Profiles;

import java.nio.file.Path;
import java.util.ArrayList;

public interface Filters {

    public ArrayList<ProfileEntry> filterProfilesForFile(final Path file, Profiles profiles);

    public ArrayList<ProfileEntry> filterProfilesForDirectory(final String directory, Profiles profiles);

    public ArrayList<ProfileEntry> filterProfilesForInteractive(final String profile, Profiles profiles);
}
