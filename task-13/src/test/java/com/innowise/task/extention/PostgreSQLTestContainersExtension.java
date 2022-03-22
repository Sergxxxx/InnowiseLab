package com.innowise.task.extention;

import com.innowise.task.initializer.Postgres;
import org.junit.jupiter.api.extension.*;

public class PostgreSQLTestContainersExtension implements
                BeforeAllCallback,
                AfterAllCallback
{

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        Postgres.CONTAINER.start();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        Postgres.CONTAINER.close();
    }

}