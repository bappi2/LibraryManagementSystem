package com.mmefta.lms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmefta.lms.configuration.MongoConfiguration;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class LibraryManagementSystemConfiguration extends Configuration {
    @JsonProperty
    @Valid
    @NotNull
    private SwaggerBundleConfiguration swagger;

    public MongoConfiguration mongoConfiguration;
}
