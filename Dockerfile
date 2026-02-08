FROM icr.io/appcafe/open-liberty:full-java25-openj9-ubi-minimal

USER root
# Create the target directory inside the image
RUN mkdir -p /opt/ol/wlp/usr/shared/resources/jdbc && \
    chown -R 1001:0 /opt/ol/wlp/usr/shared/resources/jdbc

USER 1001

# 1. Copy the server configuration
COPY --chown=1001:0 src/main/liberty/config/server.xml /opt/ol/wlp/usr/servers/defaultServer/server.xml

# 2. Copy the JDBC driver using a wildcard for the version
COPY --chown=1001:0 target/liberty/wlp/usr/shared/resources/jdbc/ojdbc11-*.jar /opt/ol/wlp/usr/shared/resources/jdbc/

# 3. Copy the WAR file
COPY --chown=1001:0 target/service.war /opt/ol/wlp/usr/servers/defaultServer/apps/service.war

# 4. Finalize the server (installs features like servlet-6.0)
RUN configure.sh