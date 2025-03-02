# 使用 OpenJDK 17 Slim 版本作為基底映像
FROM openjdk:17-jdk-slim

# 設定工作目錄
WORKDIR /app

# 複製 Maven 打包後的 JAR 檔案（確認 JAR 檔名）
COPY target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar

# 設定環境變數，Render 會動態提供 PORT
ENV PORT=8080
ENV JAVA_OPTS="-Dserver.port=${PORT}"

# 暴露服務埠（給 Docker Compose 本機測試用）
EXPOSE 8080

# 啟動應用程式
CMD ["sh", "-c", "java $JAVA_OPTS -jar demo-0.0.1-SNAPSHOT.jar"]