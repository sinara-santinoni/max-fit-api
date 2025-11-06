# MaxFit API - Backend Java Spring Boot

Sistema de gerenciamento de treinos, alunos e personal trainers desenvolvido com Spring Boot.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Maven**

## 📁 Estrutura do Projeto
```
src/main/java/com/maxfit/
├── config/           # Configurações (CORS)
├── controller/       # Controllers REST
├── dto/
│   ├── request/      # DTOs de entrada
│   └── response/     # DTOs de saída
├── exception/        # Tratamento de exceções
├── model/            # Entidades JPA
├── repository/       # Repositories JPA
└── service/          # Lógica de negócio
```

## 🔧 Configuração

### Variáveis de Ambiente

Configure a variável de ambiente:
```properties
DATABASE_URL=jdbc:postgresql://host:port/database?user=username&password=password
```

## 🏗️ Build e Execução

### Build com Maven
```bash
mvn clean install
```

### Executar localmente
```bash
mvn spring-boot:run
```

## 📋 Endpoints da API

### Autenticação
- `POST /api/cadastro` - Cadastrar usuário
- `POST /api/login` - Login

### Alunos
- `GET /api/alunos-disponiveis` - Listar alunos sem personal
- `GET /api/alunos-do-personal/{id}` - Listar alunos do personal
- `PUT /api/vincular-aluno` - Vincular aluno ao personal
- `PUT /api/remover-aluno/{id}` - Desvincular aluno

### Treinos
- `POST /api/treinos` - Cadastrar treino
- `GET /api/treinos/{alunoId}` - Listar treinos do aluno

### Progresso
- `GET /api/progresso/{alunoId}` - Buscar progresso
- `POST /api/progresso` - Cadastrar progresso
- `PUT /api/progresso/{id}` - Atualizar progresso

### Desafios
- `GET /api/desafios` - Listar todos os desafios
- `GET /api/desafios/{alunoId}` - Listar desafios do aluno
- `POST /api/desafios` - Criar desafio
- `PUT /api/desafios/{id}/concluir` - Concluir desafio
- `DELETE /api/desafios/{id}` - Excluir desafio
- `POST /api/desafios/{id}/participar` - Participar de desafio

### Diário
- `GET /api/diarios/{alunoId}` - Listar diários
- `POST /api/diarios` - Registrar entrada no diário

## 🚀 Deploy

Configure a variável `DATABASE_URL` no seu ambiente de deploy (Render, Heroku, etc.)

## 📝 Licença

Este projeto está sob a licença MIT.