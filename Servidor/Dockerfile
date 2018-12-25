FROM openjdk

RUN mkdir -p /usr/src/Servidor
WORKDIR /usr/src/Servidor

COPY . /usr/src/Servidor

EXPOSE 1099

CMD java -Djava.security.policy=policy.all StartServer
