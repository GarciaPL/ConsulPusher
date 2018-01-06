package pl.garciapl.consul.service;

import java.io.IOException;
import pl.garciapl.consul.domain.Profiles;

public interface FileService {

    public void processFiles(String configPath, Profiles profiles) throws IOException;
}
