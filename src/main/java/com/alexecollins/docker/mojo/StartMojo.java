package com.alexecollins.docker.mojo;

import com.alexecollins.docker.orchestration.DockerOrchestrator;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.Map;

/**
 * Start all the containers.
 */
@Mojo(name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class StartMojo extends AbstractDockerMojo {

    @Override
    protected void doExecute(DockerOrchestrator orchestrator) {
        if (parallelStart) {
            orchestrator.start(threadCount);
        } else {
            orchestrator.startAsync();
        }
        Map<String, String> idAndIpMap = orchestrator.getIPAddresses();
        for (String id : idAndIpMap.keySet()) {
            getProject().getProperties().setProperty("docker." + id + ".ipAddress", idAndIpMap.get(id));
        }
    }

}
