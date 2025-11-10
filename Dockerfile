# 1. Imagem de base
# Vamos usar uma imagem oficial que já tem o Java 17 (o mesmo do seu pom.xml)
FROM eclipse-temurin:17-jre-jammy

# 2. Definir o diretório de trabalho dentro do container
WORKDIR /app

# 3. Copiar o .jar do seu projeto para dentro da imagem
# (Adapte o nome do .jar se for diferente)
COPY target/properties-1.0.0.jar app.jar

# 4. Expor a porta
# O seu application.properties usa a porta 10000
EXPOSE 10000

# 5. Comando para rodar a aplicação
# Este é o comando que será executado quando o container iniciar
CMD ["java", "-jar", "app.jar"]