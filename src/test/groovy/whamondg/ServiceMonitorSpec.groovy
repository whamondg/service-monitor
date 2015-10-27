package whamondg

import org.junit.*
import spock.lang.*
import co.freeside.betamax.Betamax
import co.freeside.betamax.Recorder

class ServiceMonitorSpec extends Specification {

    @Rule public Recorder recorder = new Recorder();
    @Betamax(tape="my tape")
    def "Checking a remote service"() {
        setup:
            def monitor = new ServiceChecker()
        expect:
            monitor.check('http://localhost:5050/')
    }
}
