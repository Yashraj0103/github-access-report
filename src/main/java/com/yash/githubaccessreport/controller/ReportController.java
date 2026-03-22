package com.yash.githubaccessreport.controller;

import com.yash.githubaccessreport.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService service;

    @GetMapping("/{org}")
    public ResponseEntity<Map<String, Set<String>>> getReport(
            @PathVariable String org) {

        return ResponseEntity.ok(service.generateReport(org));
    }
}