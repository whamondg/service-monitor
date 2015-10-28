Service Monitor
===============

Service-monitor is a tool for gathering metrics/monitoring remote services.  The basic idea is that even if you don't own the service it's a good idea to understand how it's performing.  When it's spun up the monitor makes requests against remote services and publishes Prometheus metrics.

*Note* This tool has been driven out as a means to an end for playing with Prometheus and its not going to work as a general purpose tool without work.  For example the service endpoints being monitored are not currently configurable...

Usage
-----
Service-monitor can be used by running an executable JAR or via a Docker container.

### Standalone ###
To use the JAR simply download it (substituting the version as appropriate):
`curl -L -o service-monitor.jar https://bintray.com/artifact/download/whamondg/maven/whamondg/service-monitor-0.0.2-all.jar`

Then run it: 
`java -jar service-monitor.jar`

Service-monitor publishes metrics at [http://localhost:5060/metrics](http://localhost:5060/meetrics).

### Docker ###
To use the Docker container pull it (substituting the version as appropriate):
`docker pull whamondg-docker-registry.bintray.io/service-monitor:0.0.2`

Then run it:

Docker: ```docker run -p 5060:5060 whamondg-docker-registry.bintray.io/service-monitor:0.0.2```

When running Docker On Linux you can now request [http://localhost:5060/](http://localhost:5060/).  However, if you are using Docker on Windows or Mac the Docker host will be running via a Linux VM; you will need to know the IP address of the Docker Host to access the service.  This can be found by using [Kitematic](https://kitematic.com/): simply click the running container in the Kitematic GUI and then open the 'IP and Ports' settings to find the host IP.
