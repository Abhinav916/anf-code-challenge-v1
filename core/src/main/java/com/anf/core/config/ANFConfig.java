package com.anf.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/***
 * Begin Code
 * Name - Abhinav Chatharaboina*
 */
@ObjectClassDefinition(name = "ANF Configs", description = "Configs for age validation, data store, page root path etc.")
public @interface ANFConfig {
    @AttributeDefinition(name = "Country Data Path", description = "Enter Country JSON Path", type = AttributeType.STRING)
    String countryJsonPath() default "/content/dam/anf-code-challenge/exercise-1/countries.json";

    @AttributeDefinition(name = "Age Validation Config Path", description = "Enter the Age Validation Data Path Node", type = AttributeType.STRING)
    String ageLimitPath() default "/etc/age";

    @AttributeDefinition(name = "Form Data Repository Path", description = "Enter the Path where form data will be saved", type = AttributeType.STRING)
    String dataStorePath() default "/var/anf-code-challenge";

    @AttributeDefinition(name = "Page Content Path", description = "Enter the content Path where ANF pages are created", type = AttributeType.STRING)
    String pageContentPath() default "/content/anf-code-challenge/us/en";

    /* End Code*/
}
