# Dependencies

Apart from one (jasmin), all the dependencies are managed by using Maven, so to install jasmin into the Maven, first download it from [http://jasmin.sourceforge.net/](http://jasmin.sourceforge.net/) and unzip jasmin.jar into `pwd`. Then run
```bash
mvn 'install:install-file' \
        '-Dfile=jasmin-2.4/jasmin.jar' \
        '-DgroupId=jasmin' '-DartifactId=jasmin' '-Dversion=2.4' \
        '-Dpackaging=jar' \
        '-DlocalRepositoryPath='$HOME'/.m2/repository'
```

Then just run
```bash
mvn package assembly:single
```
to compile `target/part-II-project-1.0-SNAPSHOT-jar-with-dependencies.jar`
