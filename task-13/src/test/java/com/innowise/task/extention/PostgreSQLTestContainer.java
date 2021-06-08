package com.innowise.task.extention;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, METHOD })
@Retention(RUNTIME)
@ExtendWith(PostgreSQLTestContainersExtension.class)
public @interface PostgreSQLTestContainer {

}
