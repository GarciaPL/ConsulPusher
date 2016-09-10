package pl.garciapl.consul.service;

import java.nio.file.Path;

public interface ConsulPusher {

    public void fillConsul(Path file, String profile, String key, String value);

}
