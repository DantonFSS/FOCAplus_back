# FOCA+ Backend

Backend da aplicação FOCA+, uma plataforma educacional desenvolvida em Spring Boot para gerenciamento de cursos, disciplinas, avaliações e sessões de estudo.

## 📋 Sobre a Aplicação

O FOCA+ é uma plataforma completa para estudantes gerenciarem sua vida acadêmica. A aplicação permite:

- **Gerenciamento de Cursos**: Criação e organização de cursos com períodos e disciplinas
- **Disciplinas**: Gerenciamento de disciplinas com horários, docentes e avaliações
- **Avaliações**: Sistema de avaliações com notas conceituais e numéricas
- **Tarefas**: Criação e gerenciamento de tarefas com colaboração entre usuários
- **Sessões de Estudo**: Registro e acompanhamento de sessões de estudo
- **Sistema de Pontuação**: Gamificação através de XP (experiência) por disciplina
- **Amizades**: Sistema de conexão entre usuários para colaboração
- **Autoavaliação**: Sistema de perguntas e respostas para autoavaliação
- **Autenticação**: Sistema completo de autenticação com JWT e refresh tokens

## 🔗 Frontend

O frontend da aplicação está disponível em: [https://foc-aplus-front.vercel.app/](https://foc-aplus-front.vercel.app/)

## 🛠️ Tecnologias

- **Java 17**
- **Spring Boot 3.2.8**
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados
- **JWT** - Autenticação baseada em tokens
- **Lombok** - Redução de boilerplate
- **SpringDoc OpenAPI** - Documentação da API (Swagger)
- **Maven** - Gerenciamento de dependências

## 📦 Estrutura do Projeto

```
src/main/java/com/focados/foca/
├── config/                    # Configurações (Security, CORS, JWT, Swagger)
├── modules/                   # Módulos da aplicação
│   ├── assessments/          # Módulo de avaliações
│   ├── courses/              # Módulo de cursos
│   ├── disciplines/          # Módulo de disciplinas
│   ├── friendships/          # Módulo de amizades
│   ├── periods/              # Módulo de períodos
│   ├── rewards/              # Módulo de recompensas
│   ├── score/                # Módulo de pontuação
│   ├── selfEvaluation/       # Módulo de autoavaliação
│   ├── studySessions/        # Módulo de sessões de estudo
│   ├── tasks/                # Módulo de tarefas
│   └── users/                # Módulo de usuários
└── shared/                   # Utilitários compartilhados
```

## 🚀 Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- PostgreSQL 12+

### Configuração

1. Clone o repositório:
```bash
git clone <repository-url>
cd FOCAplus-main
```

2. Configure as variáveis de ambiente no arquivo `application.yml` ou através de variáveis de ambiente:

```yaml
POSTGRES_URL: jdbc:postgresql://localhost:5432/db_foca
POSTGRES_USER: postgres
POSTGRES_PASSWORD: postgres
JWT_SECRET: your-secret-key
JWT_EXPIRATION: 3600000
JWT_REFRESH_EXPIRATION: 86400000
WEBAPI_PORT: 8080
```

3. Execute a aplicação:

```bash
mvn spring-boot:run
```

Ou usando Docker:

```bash
docker-compose up
```


## 🔐 Autenticação

A aplicação utiliza JWT (JSON Web Tokens) para autenticação. Os endpoints de autenticação estão disponíveis em `/api/v1/auth/`:

- `POST /api/v1/auth/register` - Registro de novo usuário
- `POST /api/v1/auth/login` - Login
- `POST /api/v1/auth/refresh` - Renovação de token
- `POST /api/v1/auth/logout` - Logout

## 📝 Endpoints Principais

### Usuários
- `GET /api/v1/users` - Listar todos os usuários
- `GET /api/v1/users/me` - Obter dados do usuário autenticado

### Cursos
- `GET /api/v1/courses` - Listar cursos do usuário
- `POST /api/v1/courses` - Criar novo curso
- `PUT /api/v1/courses/{id}` - Atualizar curso
- `DELETE /api/v1/courses/{id}` - Deletar curso

### Disciplinas
- `GET /api/v1/discipline-instances` - Listar disciplinas
- `POST /api/v1/discipline-instances` - Criar disciplina
- `PUT /api/v1/discipline-instances/{id}` - Atualizar disciplina
- `DELETE /api/v1/discipline-instances/{id}` - Deletar disciplina

### Avaliações
- `GET /api/v1/assessments` - Listar avaliações
- `POST /api/v1/assessments` - Criar avaliação
- `PUT /api/v1/assessments/{id}` - Atualizar avaliação
- `DELETE /api/v1/assessments/{id}` - Deletar avaliação

### Tarefas
- `GET /api/v1/tasks` - Listar tarefas
- `POST /api/v1/tasks` - Criar tarefa
- `PUT /api/v1/tasks/{id}` - Atualizar tarefa
- `DELETE /api/v1/tasks/{id}` - Deletar tarefa

## 🐳 Docker

A aplicação inclui suporte para Docker. Use o `docker-compose.yml` para executar a aplicação com PostgreSQL:

```bash
docker-compose up -d
```

## 📄 Licença

Este projeto é parte do FOCA+.

