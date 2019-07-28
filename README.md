## Application Info

### Get application version from Manifest file
_____

#### Start

1) First you need to write Groovy script for autoincrement your application version into `build.gradle` file:

        tasks.register("incrementVersion") {
            doFirst {
                def ver = version
                println ver
                String lastNumber = ver.substring(ver.lastIndexOf('.') + 1)
                int increment = lastNumber.toInteger() + 1
                println increment
        
                String firstNumber = ver.substring(0, ver.lastIndexOf("."))
                println firstNumber
                String result = buildFile.getText().replaceFirst("version='$version'","version='" + firstNumber + "." + increment + "'")
                buildFile.setText(result)
            }
        }
        
You must specify the dependence of your task `incrementVersion` in relation to the `build` gradle task.
Those, when performing the `build` gradle task, your `incrementVersion` task will be executed first and thus the new value will be stored in your `build.gradle` file.
       
    jar.dependsOn incrementVersion
    bootJar.dependsOn incrementVersion
       
or simply

    build.dependsOn incrementVersion
        
Then you need to write what you need to display into your jar file in `MANIFEST.MF` file:

    bootJar {
        doFirst {
            manifest {
                attributes(
                        'Main-Class': 'com.example.appinfo.AppinfoApplication',
                        'Implementation-Title': 'Application Info project',
                        'Implementation-Version': "${version}"
                )
            }
        }
    }         
       
Indicate your original version:

    version='0.0.5'    
    
    
2) Then you need to write backend logic to get this application version, when your jar file will be ready:

        public String getManifestInfo() {
            return getClass().getPackage().getImplementationVersion();
        }
 
For example I created simple controller for check version.

_____

#### How to check
- in terminal:

        gradle build
        
You can see your version will be increment.

Your `MANIFEST` file will be on:
    
    /build/tmp/bootJar/MANIFEST.MF

Your jar file will be on:

    /build/libs/

You can see my MANIFEST.MF file after creation new build:

    Manifest-Version: 1.0
    Implementation-Title: Application Info project
    Implementation-Version: 0.0.4
    Start-Class: com.example.ApplicationInfo.ApplicationInfoApplication
    Spring-Boot-Classes: BOOT-INF/classes/
    Spring-Boot-Lib: BOOT-INF/lib/
    Spring-Boot-Version: 2.1.6.RELEASE
    Main-Class: com.example.appinfo.AppinfoApplication
    
Exactly the same file will be in your jar file.

- then start your jar file from command line

- go to `localhost:8080/info`

You will see application version from `MANIFEST.MF` file from jar.

    NOTES:
    
    If you will repeat this operations you will see autoincrement application version (+1)
