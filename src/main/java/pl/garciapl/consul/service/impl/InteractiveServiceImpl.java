package pl.garciapl.consul.service.impl;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.garciapl.consul.domain.Profiles;
import pl.garciapl.consul.service.ConsulPusher;
import pl.garciapl.consul.service.Filters;
import pl.garciapl.consul.service.InteractiveService;

import java.util.Comparator;

@Component
public class InteractiveServiceImpl implements InteractiveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InteractiveServiceImpl.class);

    private static Options options;
    private static HelpFormatter helpFormatter;

    static {
        options = new Options();
        options.addOption("p", "profile", true, "Profile");
        options.addOption("k", "key", true, "Key");
        options.addOption("v", "value", true, "Value");

        helpFormatter = new HelpFormatter();
        helpFormatter.setOptionComparator(new Comparator<Option>() {
            @Override
            public int compare(Option o1, Option o2) {
                if (o2.getOpt().equals("p")) {
                    return 1;
                } else {
                    return o1.getId() - o2.getId();
                }
            }
        });

    }

    @Autowired
    private Filters filters;

    @Autowired
    private ConsulPusher consulPusher;

    @Override
    public void processInteractive(String[] args, Profiles profiles) {
        try {
            helpFormatter.printHelp("Usage", options);
            CommandLine cmd = new DefaultParser().parse(options, args);
            if (cmd.hasOption('p') && cmd.hasOption('k') && cmd.hasOption('v')) {
                if (filters.filterProfilesForInteractive(cmd.getOptionValue('p'), profiles).isEmpty()) {
                    LOGGER.error("Wrong profile was provided. See profiles.json");
                } else {
                    consulPusher.fillConsul(null, cmd.getOptionValue('p'), cmd.getOptionValue('k'), cmd.getOptionValue('v'));
                }
            } else {
                LOGGER.error("Wrong parameters. All three are required to proceed. See usage");
            }
        } catch (ParseException e) {
            LOGGER.error("ParseException {}", e);
        }
    }
}
