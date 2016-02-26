# Dependencies

Apart from one (jasmin), all the dependencies are managed by using Maven, so to install jasmin into the Maven, first download it from [http://jasmin.sourceforge.net/](http://jasmin.sourceforge.net/). Then run
```bash
mvn 'install:install-file' \
        '-Dfile=/home/kr2/Downloads/jasmin-2.4/jasmin.jar' \
        '-DgroupId=jasmin' '-DartifactId=jasmin' '-Dversion=2.4' \
        '-Dpackaging=jar' \
        '-DlocalRepositoryPath='${HOME}'/.m2/repository'
```

Then just run
```bash
mvn package
```
to compile `target/part-II-project-1.0-SNAPSHOT.jar`
