package com.application.administration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.application.apps.administration.AdministrationBackendApplication;
import com.application.shared.infrastructure.InfrastructureTestCase;

@ContextConfiguration(classes = AdministrationBackendApplication.class)
@SpringBootTest
public abstract class AdministrationContextInfrastructureTestCase extends InfrastructureTestCase {
}
