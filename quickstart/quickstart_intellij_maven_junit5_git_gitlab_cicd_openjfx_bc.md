quickstart_intellij_maven_junit5_git_gitlab_cicd_openjfx_bc.md
Last change: 17.07.2020

IntelliJ + Maven + JUnit5 + Git + GitLab CI/CD + OpenJFX + BC

Ressources:
https://openjfx.io/openjfx-docs/#IDE-Intellij
https://www.petrikainulainen.net/programming/testing/junit-5-tutorial-running-unit-tests-with-maven/
https://www.tutorialspoint.com/junit/junit_basic_usage.htm
https://dzone.com/articles/junit-5-basics
https://hackernoon.com/setting-up-ci-cd-on-gitlab-step-by-step-guide-part-1-826385728223

Starting Point:
OpenJDK 14 installed

Start IntelliJ:
bin/idea.sh (Linux)

Create Project:
File -> New -> Project
-> Select Maven
-> Enable "Create from archetype"

Project SDK version shall be 14

Select org.apache.maven.archetypes:maven-archetype-quickstart
-> Next

Expand Artifact Coordinates
GroupId := edu.junit5.quickstart
ArtifactId := SimpleJunit5
-> Next

Enable Auto-import

Replace the generated pom.xml with the given pom.xml

Save pom.xml

Delete all Java files under src/main/java/edu.junit5.quickstart

Copy Java file MessageUtil.java
into <Your Workspace>\SimpleJunit5\src\main\java\edu\junit5\quickstart

Copy Java file HelloFX.java from
https://github.com/openjfx/samples/blob/master/HelloFX/Maven/hellofx/src/main/java/HelloFX.java
into <Your Workspace>\SimpleJunit5\src\main\java\edu\junit5\quickstart

Add
   package edu.junit5.quickstart;
at the beginning of the Java file HelloFX.java

Copy Java file ListProviders.java from
Java Cryptography: Tools and Techniques from Hook and Eeaves, Java Listing "ListProviders" on page 6
into <Your Workspace>\SimpleJunit5\src\main\java\edu\junit5\quickstart

Include BouncyCastleProvider as a security provider dynamically by adding
   import org.bouncycastle.jce.provider.BouncyCastleProvider;
at the beginning of the Java file ListProviders.java and by adding
   Security.addProvider(new BouncyCastleProvider());
as the first statement into the main method of the Java file ListProviders.java

Delete all Java files under src/test/java/edu.junit5.quickstart

Copy Java files MessageUtilTest.java, TestRunner.java
into <Your Workspace>\SimpleJunit5\src\test\java\edu\junit5\quickstart

Try running the TestRunner: View->Tool Windows->Maven->Expand SimpleJunit5->Expand Lifecycle
Execute clean
Execute compile
Execute test
Execute clean
Execute install

Change test case (so it fails):
```
    @Test
    public void testPrintMessage() {
        message = "New World";
        assertEquals(message,messageUtil.printMessage());
    }
```
Execute clean
Execute test

Try running the JavaFX: View->Tool Windows->Maven->Expand SimpleJunit5->Expand Plugins
Execute javafx:run

Try running the Java class ListProviders: Select ListProviders->Run->Run'List Providers'->Run

VCS->Import into Version Control->Create Git Repository

OK

VCS->Commit Changes

Select all unversioned files

Commit Message := Initial Commit

Commit

Create GitLab project gitlab-demo w/o initial commit at https://gitlab.com/

VCS->Git->Push

URL := https://gitlab.com/USERNAME/REPONAME.git

Create .gitlab-ci.yml file in git root folder

Content := 
image: maven:3.6.3-jdk-14

clean:
  script: "mvn clean -B"

compile:
  script: "mvn compile -B"

install:
  script: "mvn install -B"

Commit and push the .gitlab-ci.yml file
