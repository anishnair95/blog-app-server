package com.springboot.blog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health check test API")
@RestController
@RequestMapping("/internal")
public class HealthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthController.class);

    /**
     * Health check
     * @return string
     */
    @Operation(summary = "Health check API", description = "Give server status")
    @ApiResponse(responseCode = "200", description = "success")
    @GetMapping("/health")
    public String getStatus() {
        LOGGER.info("Inside getStatus");
        return "Server is up and running...";
    }

}