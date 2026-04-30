# 🏢 CondoPay API

> API REST para gerenciamento de condomínios — controle de usuários, reservas de áreas comuns e autenticação JWT com Spring Boot.

---

## 📌 Sobre o Projeto

O **CondoPay** é uma API desenvolvida para facilitar a gestão condominial. O sistema permite que o **síndico** cadastre moradores e áreas comuns, enquanto os **moradores** podem fazer reservas dessas áreas de forma prática e segura.

---

## 🛠️ Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 4.0.5 |
| Spring Security + OAuth2 Resource Server | — |
| Spring Data JPA | — |
| SpringDoc OpenAPI (Swagger UI) | 3.0.2 |
| MapStruct | 1.5.5.Final |
| Lombok | — |
| PostgreSQL | 14 |
| Docker / Docker Compose | — |
| JUnit 5 + Mockito | — |

---

## 🏗️ Arquitetura

```
condopay/
├── controller/        # Camada de entrada — endpoints REST
├── service/           # Regras de negócio
├── database/
│   ├── entity/        # Entidades JPA (UsuarioModel, ReservaModel, AreaComumModel)
│   ├── entity/enums/  # Enums (UsuarioRole, StatusEnum)
│   └── repository/    # Interfaces JPA Repository
├── dto/
│   ├── request/       # DTOs de entrada
│   └── response/      # DTOs de saída
├── mapper/            # Interfaces MapStruct
├── security/
│   ├── config/        # SecurityConfig (JWT RSA)
│   └── model/         # UserAuthenticated (UserDetails)
└── exception/
    └── handler/       # GlobalExceptionHandler, exceções customizadas
```

### Modelo de Dados

```
UsuarioModel  1 ──── N  ReservaModel  N ──── 1  AreaComumModel
```

---

## ✅ Funcionalidades

### 👤 Usuário
- Cadastrar novo usuário (apenas Síndico)
- Listar todos os usuários (apenas Síndico)
- Buscar usuário por ID (apenas Síndico)
- Consultar dados do próprio perfil autenticado

### 🏊 Área Comum
- Cadastrar área comum (apenas Síndico)
- Listar todas as áreas comuns
- Buscar área comum por ID
- Deletar área comum (apenas Síndico)

### 📅 Reserva
- Criar reserva em uma área comum (Morador)
- Listar reservas do usuário autenticado
- Detalhar reserva por ID
- Cancelar reserva

---

## 🌐 Endpoints

### 🔐 Autenticação

| Método | Rota | Descrição | Acesso |
|--------|------|-----------|--------|
| `POST` | `/authenticate` | Login — retorna JWT | Público |

### 👤 Usuários `/v1/usuario`

| Método | Rota | Descrição | Acesso |
|--------|------|-----------|--------|
| `POST` | `/v1/usuario` | Cadastrar usuário | `SINDICO` |
| `GET` | `/v1/usuario` | Listar usuários | `SINDICO` |
| `GET` | `/v1/usuario/{id}` | Buscar usuário por ID | `SINDICO` |
| `GET` | `/v1/usuario/me` | Dados do usuário logado | `SINDICO`, `MORADOR` |

### 🏊 Áreas Comuns `/v1/areas`

| Método | Rota | Descrição | Acesso |
|--------|------|-----------|--------|
| `POST` | `/v1/areas` | Cadastrar área comum | `SINDICO` |
| `GET` | `/v1/areas` | Listar áreas comuns | `SINDICO`, `MORADOR` |
| `GET` | `/v1/areas/{id}` | Buscar área por ID | `SINDICO`, `MORADOR` |
| `DELETE` | `/v1/areas/{id}` | Deletar área comum | `SINDICO` |

### 📅 Reservas `/v1/reserva`

| Método | Rota | Descrição | Acesso |
|--------|------|-----------|--------|
| `POST` | `/v1/reserva` | Criar reserva | `MORADOR` |
| `GET` | `/v1/reserva` | Listar minhas reservas | `MORADOR` |
| `GET` | `/v1/reserva/{id}` | Detalhar reserva | `MORADOR` |
| `PATCH` | `/v1/reserva/{id}/cancelar` | Cancelar reserva | `MORADOR` |

---

## 📖 Documentação Interativa (Swagger UI)

Com a aplicação rodando, acesse:

```
http://localhost:8081/swagger-ui.html
```

A documentação é gerada automaticamente pelo **SpringDoc OpenAPI 3.0.2** e exibe todos os endpoints com seus schemas de request/response.

---

## 🔐 Autenticação

A API utiliza **JWT com chaves RSA** (assimétrico).

### Fluxo

```
POST /authenticate
Body: { "email": "...", "senha": "..." }

↓ Retorna:
{ "token": "eyJhbGci..." }
```

O token deve ser enviado no header de todas as requisições protegidas:

```
Authorization: Bearer <token>
```

### Roles disponíveis

| Role | Descrição |
|------|-----------|
| `SINDICO` | Administrador do condomínio — acesso total |
| `MORADOR` | Morador — acesso a reservas e consultas |

### Configuração das chaves RSA

Adicione no `application.properties`:

```properties
jwt.public.key=classpath:app.pub
jwt.private.key=classpath:app.key
```

---

## 📐 Regras de Negócio

### Reservas
- Não é possível criar duas reservas para a mesma área comum no mesmo período (validação de conflito de horário)
- A data de fim não pode ser anterior à data de início
- Apenas reservas com status `CONFIRMADO` podem ser canceladas
- O valor cobrado é definido automaticamente com base no `valorReserva` da área comum

### Usuários
- Não é permitido cadastrar dois usuários com o mesmo e-mail — retorna `409 Conflict`
- Senhas são armazenadas com hash **BCrypt**

---

## 🚀 Como Executar

### Pré-requisitos

- Java 17+
- Maven 3.8+
- Docker e Docker Compose (recomendado)

---

### ▶️ Opção 1 — Docker Compose (recomendado)

Sobe o banco PostgreSQL **e** a aplicação Spring Boot juntos:

```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/condopay.git
cd condopay

# Gerar as chaves RSA (necessário antes do build)
openssl genrsa -out src/main/resources/app.key 2048
openssl rsa -in src/main/resources/app.key -pubout \
            -out src/main/resources/app.pub

# Subir todos os containers
docker compose up --build
```

| Container | Descrição | Porta |
|---|---|---|
| `condopay_db_postgres` | PostgreSQL 14 | `5433` (host) → `5432` (container) |
| `condopay_app_spring` | Spring Boot App | `8081` |

Para parar:
```bash
docker compose down
```

> Os dados do banco ficam persistidos no volume Docker `postgres_data`, mesmo após `docker compose down`.

---

### ▶️ Opção 2 — Executar localmente (sem Docker para a app)

Sobe apenas o banco via Docker e roda a aplicação direto na máquina:

```bash
# 1. Subir somente o banco
docker compose up postgres -d

# 2. Gerar as chaves RSA
openssl genrsa -out src/main/resources/app.key 2048
openssl rsa -in src/main/resources/app.key -pubout \
            -out src/main/resources/app.pub

# 3. Executar a aplicação
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8081`.

---

### ⚙️ Configuração — `application.properties`

As propriedades suportam **substituição por variáveis de ambiente**, o que permite configurar sem alterar o arquivo:

```properties
# Banco de dados
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5433/condopay_db}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:admin}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:admin}

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Servidor
server.port=8081

# JWT (chaves RSA)
jwt.private.key=classpath:app.key
jwt.public.key=classpath:app.pub
```

> **Atenção:** os arquivos `app.key` e `app.pub` devem existir em `src/main/resources/` antes de compilar.

---

## 🧪 Testes

Os testes unitários cobrem a camada de serviço utilizando **JUnit 5** e **Mockito**.

```bash
# Executar todos os testes
./mvnw test
```

### Cobertura atual — `UsuarioService`

| Cenário | Status |
|---------|--------|
| Cadastrar usuário com sucesso | ✅ |
| Cadastrar usuário com e-mail duplicado | ✅ |
| Listar todos os usuários | ✅ |
| Buscar usuário por ID com sucesso | ✅ |
| Buscar usuário por ID não encontrado | ✅ |
| Retornar dados do usuário logado (JWT) | ✅ |
| Usuário logado não encontrado | ✅ |

---

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/vb/condopay/
│   │   ├── controller/
│   │   │   ├── UsuarioController.java
│   │   │   ├── AreaComumController.java
│   │   │   └── ReservaController.java
│   │   ├── service/
│   │   │   ├── UsuarioService.java
│   │   │   ├── AreaComumService.java
│   │   │   └── ReservaService.java
│   │   ├── database/
│   │   │   ├── entity/
│   │   │   │   ├── UsuarioModel.java
│   │   │   │   ├── AreaComumModel.java
│   │   │   │   ├── ReservaModel.java
│   │   │   │   └── enums/
│   │   │   │       ├── UsuarioRole.java
│   │   │   │       └── StatusEnum.java
│   │   │   └── repository/
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   └── response/
│   │   ├── mapper/
│   │   ├── security/
│   │   │   ├── config/SecurityConfig.java
│   │   │   └── model/UserAuthenticated.java
│   │   └── exception/
│   │       └── handler/
│   │           ├── GlobalExceptionHandler.java
│   │           ├── ConflitoException.java
│   │           └── NaoEncontradoException.java
│   └── resources/
│       ├── application.properties
│       ├── app.key
│       └── app.pub
└── test/
    └── java/com/vb/condopay/
        └── service/
            └── UsuarioServiceTest.java
```

---

## 📄 Licença

Este projeto está sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.
