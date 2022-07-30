mvn io.quarkus.platform:quarkus-maven-plugin:2.11.1.Final:create \
    -DprojectGroupId=at.htl.elytronfile \
    -DprojectArtifactId=security-elytron-file \
    -Dextensions="resteasy,resteasy-jackson"
cd security-elytron-file
