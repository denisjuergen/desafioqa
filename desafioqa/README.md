## QA Challenge
Applicant
- *Name:* Denis Juergen Brat
- *Email:* denis.juergen@gmail.com

## Requirements
- JDK 1.8

## Application build
```
bash gradlew compileJava
bash gradlew compileTestJava
```

## Start unit tests (POSIX)
```
bash gradlew runMobileTestsUsingChrome
```
If you're using Windows, then try with the gradlew.bat executable variant

## Notice
The build script automatically downloads a specific Selenium Chrome Driver version under the build path, so make sure you run the Gradle script logged as an appropriate user (with enough permissions)
