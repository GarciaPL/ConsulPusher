## ConsulPusher

This small utility app might help you push content of your property files to configuration store like Consul, ZooKeeper or Etcd.

After cloning this repo, please have a look on file called **application.yml** which contains all necessary information regarding **mode**, **path** to directory which contains property files and consul connection settings like **host** and **port**.

Additionally this project contains also file called **profiles.json** which allows you to define keywords related with profiles. They will be used in case of searching those profiles in absolute path or in property file name and then they will be used to create appropriate directory on Consul side. At this moment this file looks like below, but feel free to add your own profiles based on which are used in your company. PS. This profiles configuration refers only to mode called **file**. More about this below.

```json
{
  "profiles": [
    {
      "name": "deployment"
    },
    {
      "name": "dev"
    },
    {
      "name": "devH2"
    },
    {
      "name": "performance"
    },
    {
      "name": "qa"
    },
    {
      "name": "staging"
    },
    {
      "name": "production"
    }
  ]
}
```

Regarding to **mode** setting, at this point of time application supports two modes or in other words ways of fetching properties files :

* **File** - app will detect if profiles defined in profiles.json are part of directory path or file name. In this case app will be able to push content to configuration store for properties stored as below for project_1 and project_2 as well. 

![Properties](https://cdn.rawgit.com/GarciaPL/GarciaPL.github.io/master/img/consulpusher/Properties.png)

* **Interactive** - user will provide via program arguments like profile, key and value using below syntax 

```console
Usage
 -p,--profile <arg>   Profile
 -k,--key <arg>       Key
 -v,--value <arg>     Value
``` 

Pushing your property on **dev** instance of Consul use :
```console
java -jar -Dspring.profiles.active=dev consul-pusher-1.0.0-SNAPSHOT.jar -profile dev -key db.driver.class -value org.h2.Driver
``` 

Pushing your property on **production** instance of Consul use :
```console
java -jar -Dspring.profiles.active=prod consul-pusher-1.0.0-SNAPSHOT.jar -profile dev -key db.driver.class -value org.h2.Driver
``` 

## Technology

#### Stack

- Java - 1.8.0_102
- Spring Boot - 1.4.0.RELEASE

#### Libraries

- Cfg4j - 4.4.0 (http://www.cfg4j.org)
- Orbitz - 0.12.7 (https://github.com/OrbitzWorldwide/consul-client)
- Guava - 19.0
- Gson - 2.7
- Apache Commons IO - 2.5
- Apache Commons CLI - 1.3.1
- Slf4j - 1.7.12
- Logback - 1.1.7

## Screenshot
![Consul](https://cdn.rawgit.com/GarciaPL/GarciaPL.github.io/master/img/consulpusher/Consul.png)

## License
Code released under the  Apache License 2.0. Docs released under Creative Commons.

## References
- [Consul](https://www.consul.io)
- [Cfg4j](http://www.cfg4j.org)
- [Cfg4j-Pusher](https://github.com/cfg4j/cfg4j-pusher)

Moreover I would like to put special greetings for [Norbert Potocki](https://github.com/norbertpotocki) who made initial version of this utility app under this link [Cfg4j-Pusher](https://github.com/cfg4j/cfg4j-pusher). Thanks a million!

[![ghit.me](https://ghit.me/badge.svg?repo=GarciaPL/ConsulPusher)](https://ghit.me/repo/GarciaPL/ConsulPusher)

[![Travis](https://travis-ci.org/GarciaPL/ConsulPusher.svg?branch=master)](https://travis-ci.org/GarciaPL/ConsulPusher)
[![CircleCI](https://circleci.com/gh/GarciaPL/ConsulPusher/tree/master.svg?style=shield)](https://circleci.com/gh/GarciaPL/ConsulPusher/tree/master)

[![Known Vulnerabilities](https://snyk.io/test/github/garciapl/consulpusher/badge.svg)](https://snyk.io/test/github/garciapl/consulpusher)