package com.example.ApplicationInfo.controller;

import com.example.ApplicationInfo.util.AppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppInfoResource {

    @Autowired
    private AppInfo appInfo;

    @GetMapping("/info")
    public String getAppInfo() {
        String manifestInfo = appInfo.getManifestInfo();
        if (manifestInfo == null) {
            return "start jar file go to localhost:8080/info";
        }
        return manifestInfo;
    }
}

