FROM openjdk

RUN mkdir -p /usr/src/Cliente
WORKDIR /usr/src/Cliente

COPY . /usr/src/Cliente

EXPOSE 1099

CMD java -Djava.security.policy=policy.all StartClient
