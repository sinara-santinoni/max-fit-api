# ----- Estágio 1: O Construtor (Builder) -----
# Usamos uma imagem que já tem Maven e o JDK 17
FROM maven:3.9-eclipse-temurin-17 AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia o pom.xml e baixa as dependências (isso acelera builds futuros)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o resto do código-fonte e constrói o projeto
COPY src ./src
RUN mvn clean package -DskipTests

# ----- Estágio 2: O Corredor (Runner) -----
# Começamos de uma imagem limpa, apenas com o JRE (menor)
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copia o .jar que foi construído no Estágio 1
COPY --from=builder /app/target/properties-1.0.0.jar app.jar

# Expõe a porta que o Render vai usar (ele lê a ${PORT})
EXPOSE 10000

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]