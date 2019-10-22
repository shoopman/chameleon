# Introduction

Chameleon is a light-weight library to manage the configurations of an application.
It can support for distributed systems by redis module.

# Usage

## Introduce to your project

Gradle build.gradle

```gradle
    compile("net.meku.chameleon:chameleon-starter:1.0.2")
```

Maven pom.xml 

```xml

	<dependency>
		<groupId>net.meku.chameleon</groupId>
		<artifactId>chameleon-starter</artifactId>
		<version>1.0.2</version>
	</dependency>
        
```

## Import *ConfigService* and have fun.


```java

    @Autowired
    private ConfigService configService;

    // Add or update a configuration
    configService.save(configable);

    // Get the value of a configuration
    String s1 = configService.getString(key1);
    int i1 = configService.getInt(key2);

```

