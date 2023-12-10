# Pixel

A collection of reusable and essential code snippets for dicedpixel's mods.

### Usage

Artifacts are currently hosted on [repsy.io](https://repsy.io/).

#### build.gradle
```groovy
repositories {
    maven { url 'https://repo.repsy.io/mvn/dicedpixels/fabric/'}
}

dependencies {
    modImplementation "xyz.dicedpixels:pixel:${project.pixel_version}"
    include "xyz.dicedpixels:pixel:${project.pixel_version}"
}
```

#### gradle.properties
```properties
pixel_version = 0.1.1+1.20.4
```