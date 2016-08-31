# classpathHell

Gradle plugin that breaks the build if there are classpath collisions

## Getting started

```gradle

// expect failure from this particularbuild script because hamcrest-all contains hamcrest-core
// so there are lots of dupe'd resources

repositories {
    mavenLocal()
    mavenCentral()
}

buildscript {

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        classpath "com.portingle:classpath-hell:1.0"
    }
}

apply plugin: 'com.portingle.classpathHell'
apply plugin: 'java'

classpathHell {

    // Demonstrate replacing the default set of exclusions
    // leaving only things like license files and manifests in violation
    resourceExclusions = [
            ".*class",
            ".*/",
    ]
}

dependencies {
    compile group: 'org.hamcrest', name: 'hamcrest-all', version: '1.3'
    compile group: 'org.hamcrest', name: 'hamcrest-core', version: '1.3'
}

build.dependsOn(['checkClasspath'])

```
