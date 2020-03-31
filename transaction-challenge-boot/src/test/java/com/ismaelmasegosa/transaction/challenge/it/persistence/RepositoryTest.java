package com.ismaelmasegosa.transaction.challenge.it.persistence;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableAutoConfiguration
@ComponentScan(
    {"com.ismaelmasegosa.transaction.challenge.infrastructure.persistence,com.ismaelmasegosa.transaction.challenge.infrastructure.provider"})
@ContextConfiguration(classes = {Config.class})
@Profile("it")
public @interface RepositoryTest {

}
