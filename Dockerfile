FROM ghcr.io/graalvm/graalvm-ce:ol8-java17

ADD build/distributions/mancala-0.1.tar /
WORKDIR /mancala-0.1

CMD ["bin/mancala"]
