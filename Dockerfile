#jenkins打包使用
FROM openjdk
EXPOSE 8080
VOLUME /tmp
ADD yiliao-admin/target/yiliao-admin.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=GMT+8","-jar","/app.jar"]
