package com.mmefta.lms.health;

import com.codahale.metrics.health.HealthCheck;

public class BasicHealthCheck extends HealthCheck {
    public BasicHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy("All OK by mohammad");
    }
}
