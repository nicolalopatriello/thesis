FROM openjdk:8-jre-alpine

WORKDIR /opt/


ADD docker/bin/ bin/
RUN chmod +x bin/*
ADD target/lib lib/
ADD target/*.jar lib/
ADD docker/cfg/ cfg/
ENV TZ=UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone \
    && addgroup -S -g 601 thesis \
    && adduser -D -S -h /opt/thesis -s /sbin/nologin -G thesis -u 601 thesis \
    && chown thesis.thesis -R /opt

RUN mkdir -p /var/thesis/vulnerabilities/python

EXPOSE 8080
CMD ["/opt/bin/run.sh", "-DFOREGROUND"]
