package com.innowise.task.initializer;

import com.innowise.task.util.PropertyTestUtil;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.testcontainers.containers.PostgreSQLContainer;

public final class Postgres {
    public static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>("postgres:13.3")
            .withUsername(PropertyTestUtil.getProperty("spring.datasource.username"))
            .withPassword(PropertyTestUtil.getProperty("spring.datasource.password"))
            .withCreateContainerCmdModifier(cmd ->
                    cmd.getHostConfig()
                            .withPortBindings(new PortBinding(Ports.Binding.bindPort(5477), ExposedPort.tcp(5432))));

    private Postgres() {

    }

}