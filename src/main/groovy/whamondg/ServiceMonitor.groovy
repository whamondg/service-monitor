package whamondg

import io.prometheus.client.Counter
import io.prometheus.client.Summary
import io.prometheus.client.exporter.MetricsServlet

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.servlet.ServletContextHandler

class ServiceChecker{

    void processGetRequest( name, address ) {
        def latency = Summary.build()
                          .name("${name}_request_latency_seconds")
                          .help("Request latency in seconds for ${address}.")
                          .register()

        def failures = Counter.build()
                           .name("${name}_request_failures_total")
                           .help("Request failures for ${address}.")
                           .register()

        def timer = latency.startTimer()
        println "Requesting $address"
        try {
            println address.toURL().text
        } catch ( Exception ignore ) {
            failures.inc();
        } finally {
            timer.observeDuration();
        }
    }

    void check( name, address ) {
        new Thread( [
            run : {
                int serviceTestFrequency = 3000
                while (true) {
                    processGetRequest( name, address )
                    sleep( serviceTestFrequency )
                }
            }
        ] as Runnable ).start()
    }
}

class Monitor {
    void start() {
        def server = new Server(5060)
        def context = new ServletContextHandler( contextPath : '/' )
        context.addServlet( new ServletHolder( new MetricsServlet() ), '/metrics' )
        server.handler = context

        new ServiceChecker().check( 'service5050', 'http://192.168.99.100:5050/')
        new ServiceChecker().check( 'service5051', 'http://192.168.99.100:5051/')

        server.start()
        server.join()
    }
}

new Monitor().start()
