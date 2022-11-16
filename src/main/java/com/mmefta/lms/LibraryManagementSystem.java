package com.mmefta.lms;

import com.google.common.base.Throwables;
import com.mmefta.lms.health.BasicHealthCheck;
import com.mmefta.lms.mongo.MongoManager;
import com.mmefta.lms.resources.BookResourceV1;
import com.mmefta.lms.resources.LibraryManagementServiceResourceV1;
import com.mmefta.lms.services.BookService;
import com.mmefta.lms.services.LibraryManagementService;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.Arrays;
import java.util.EnumSet;

public class LibraryManagementSystem extends Application<LibraryManagementSystemConfiguration> {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
        new LibraryManagementSystem().run(args);
    }

    @Override
    public String getName() {
        return "library-management-system";
    }

    @Override
    public void run(LibraryManagementSystemConfiguration libraryManagementSystemConfiguration, Environment environment) throws Exception {

        // Enable CORS headers
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "*");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,PATCH,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        MongoManager mongoManager = new MongoManager(getMongoClient(libraryManagementSystemConfiguration),
                libraryManagementSystemConfiguration.getMongoConfiguration());


        //final BookService bookService = new BookService();
        //BookResourceV1 bookResourceV1 = new BookResourceV1();
        final LibraryManagementService lms = new LibraryManagementService(mongoManager);
        LibraryManagementServiceResourceV1 libraryManagementServiceResourceV1
                = new LibraryManagementServiceResourceV1(lms);

        //environment.jersey().register(bookResourceV1);
        environment.jersey().register(libraryManagementServiceResourceV1);

        environment.healthChecks().register("basic", new BasicHealthCheck());



    }

    @Override
    public void initialize(Bootstrap<LibraryManagementSystemConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<LibraryManagementSystemConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(LibraryManagementSystemConfiguration configuration) {
                return configuration.getSwagger();
            }
        });


        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));

    }

    private MongoClient getMongoClient (LibraryManagementSystemConfiguration configuration) throws Exception {

        return new MongoClient(Arrays.asList(
                new ServerAddress(configuration.getMongoConfiguration().getMongoDBPrimary(), configuration.getMongoConfiguration().getMongoDBPort()),
                new ServerAddress(configuration.getMongoConfiguration().getMongoDBSecondary(), configuration.getMongoConfiguration().getMongoDBPort())
        ));
    }
}