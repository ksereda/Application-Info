package com.example.ApplicationInfo.util;

import org.springframework.stereotype.Component;

@Component
public class AppInfo {

    public String getManifestInfo() {
        return getClass().getPackage().getImplementationVersion();
    }

}
