FROM scrooge/gradle:jdk11-s390x as build
ADD . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle clean build printProperties


FROM s390x/adoptopenjdk:11.0.8_10-jre-hotspot
RUN apt-get update && \
    apt-get install unzip && \
    apt-get clean
COPY --from=build /home/gradle/project/build/distributions/Sirius.Vault.HSM-0.1.zip .
RUN unzip -q Sirius.Vault.HSM-0.1.zip && \
    rm Sirius.Vault.HSM-0.1.zip && \
    mv Sirius.Vault.HSM-* Sirius.Vault.HSM&& \
    chmod +x Sirius.Vault.HSM/bin/Sirius.Vault.HSM&& \
    sed -i -e 's/\r$//' Sirius.Vault.HSM/bin/Sirius.Vault.HSM

ENTRYPOINT ["/Sirius.Vault.HSM/bin/Sirius.Vault.HSM"]
CMD ["--console"]

EXPOSE 5000
EXPOSE 8070
