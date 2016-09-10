package pl.garciapl.consul.service;

import pl.garciapl.consul.domain.Profiles;

import java.io.IOException;

public interface FileService {

    public void processFiles(String configPath, Profiles profiles) throws IOException;
}
