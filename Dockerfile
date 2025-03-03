# 載入 openjdk:17-jdk-alpine 作為基底鏡像
FROM openjdk:17-jdk-alpine

# 在容器安裝 Maven
RUN apk add --no-cache maven

# 設定工作目錄
WORKDIR /app

# 複製 Maven 配置文件和項目描述文件
COPY pom.xml .
COPY src/ ./src/

# 設定環境變數（Render 會自動幫你帶入 Environment Variables）
ENV POSTGRES_URL=postgresql://linepaypostgresql_user:B2Hy4IIJzPoqm8wOfLV3J9yDS6QH0B2E@dpg-cv25tc2n91rc73blcfc0
ENV POSTGRES_USERNAME=inepaypostgresql_user
ENV POSTGRES_PASSWORD=B2Hy4IIJzPoqm8wOfLV3J9yDS6QH0B2E

# 執行 Maven 打包
RUN mvn clean package -DskipTests

# 檢查生成的 JAR 檔案
RUN ls -al /app/target

# 設定運行 JAR 檔案的命令
CMD ["java", "-jar", "/app/target/demo-0.0.1-SNAPSHOT.jar"]
