<p align="center">
<img width="1700" height="550" alt="fitIA-logo-escura" src="https://github.com/user-attachments/assets/46e60e25-4d84-4a7c-9eeb-6b982c62b6e6" />
</p>

<p align="center">
  FitIA — Plataforma de Treinos Personalizados
</p>


O **FitIA** é uma aplicação backend desenvolvida em Java com Spring Boot, voltada à personalização e ao acompanhamento de treinos físicos. O sistema permite organizar informações do usuário, registrar metas e estruturar planos de treino de acordo com seus objetivos.

O projeto tem como proposta evoluir para uma plataforma que utilize inteligência artificial para oferecer recomendações personalizadas e auxiliar na análise da evolução física.

**Status:** Em desenvolvimento.

## Sobre o projeto

O FitIA foi desenvolvido com foco na construção de uma API REST, aplicando conceitos de desenvolvimento backend, persistência de dados relacionais, autenticação, autorização e organização da lógica de negócio em camadas.

A aplicação busca centralizar informações relevantes para o acompanhamento dos objetivos físicos, permitindo que os dados sejam utilizados na personalização de treinos e no acompanhamento do progresso.

## Funcionalidades

### Usuários e segurança
- Cadastro e gerenciamento de usuários.
- Autenticação e controle de acesso.
- Utilização de Spring Security e recursos relacionados a OAuth 2.0 e JWT.
- Diferenciação de permissões entre usuários e administradores.

### Perfil físico
- Registro de informações físicas do usuário.
- Definição do objetivo físico.
- Registro do nível de experiência.
- Configuração da frequência semanal de treinos.
- Definição do tempo disponível para treinamento.
- Registro dos equipamentos disponíveis.

### Metas
- Cadastro e gerenciamento de metas pessoais.
- Definição de descrição, data de início e prazo.
- Registro e acompanhamento do progresso.
- Associação das metas ao usuário responsável.

### Planos de treino
- Organização de planos de treino.
- Cadastro e relacionamento de exercícios.
- Estruturação de treinos de acordo com os objetivos do usuário.
- Possibilidade de considerar experiência, frequência semanal e equipamentos disponíveis na personalização.

### Evolução do projeto
- Integração de inteligência artificial para recomendações personalizadas.
- Análise da evolução física com base nos dados registrados.
- Acompanhamento semanal e mensal do progresso.
- Expansão das funcionalidades relacionadas a planos alimentares.

As funcionalidades descritas como evolução representam objetivos do projeto e não necessariamente estão todas implementadas.

## Tecnologias utilizadas

| Tecnologia | Aplicação |
|---|---|
| Java 21 | Linguagem de programação |
| Spring Boot 4.1.1 | Framework principal |
| Spring Web MVC | Construção dos endpoints REST |
| Spring Data JPA | Persistência e acesso aos dados |
| Hibernate | Mapeamento objeto-relacional |
| PostgreSQL | Banco de dados relacional |
| Spring Security | Autenticação e autorização |
| OAuth 2.0 / JWT | Recursos de segurança e tokens |
| Bean Validation | Validação de dados |
| SpringDoc OpenAPI | Documentação interativa da API |
| Maven | Gerenciamento de dependências e build |
| Spring WebFlux | Suporte a funcionalidades de comunicação reativa |

## Arquitetura

O backend segue uma organização em camadas, separando as responsabilidades da aplicação.

- **Controller:** recebe as requisições HTTP e disponibiliza os endpoints da API.
- **Service:** concentra as regras de negócio e a lógica da aplicação.
- **Repository:** realiza operações de consulta e persistência no banco de dados.
- **Models:** representam as entidades e os dados do domínio.
- **DTOs:** permitem estruturar os dados recebidos e retornados pela API, conforme os endpoints implementados.
- **Security:** reúne os recursos de autenticação e controle de acesso.

Essa separação facilita a manutenção, a evolução e os testes da aplicação.

## Modelo de domínio

Entre as principais entidades do sistema estão:

- `Usuario`: representa os usuários cadastrados.
- `PerfilFisico`: armazena informações físicas e preferências de treinamento.
- `Meta`: representa os objetivos definidos pelo usuário.
- `PlanoTreino`: organiza os planos de treinamento.
- `Exercicio`: representa os exercícios disponíveis.
- `PlanoExercicio`: estabelece o relacionamento entre planos e exercícios.

O modelo utiliza relacionamentos entre entidades para associar os dados e manter sua integridade.

## Regras de negócio

O sistema considera regras como:

- Cada perfil físico deve estar associado a um usuário.
- Um usuário não deve possuir mais de um perfil físico.
- Cada meta deve estar vinculada ao usuário responsável.
- Os planos de treino devem respeitar os relacionamentos definidos no domínio.
- Endpoints protegidos devem exigir autenticação.
- O acesso aos recursos deve respeitar as permissões do usuário autenticado.

A implementação e a cobertura de cada regra dependem do estado atual do código.

Para consultar as regras documentadas, acesse [Regras de Negócio do FitIA](./Regras_de_Negocio_FitIA.md).

## Pré-requisitos

Para executar o projeto localmente, você precisará de:

- Java JDK 21.
- PostgreSQL.
- Git.
- Maven ou o Maven Wrapper incluído no repositório.

## Como executar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/OttoLuis/AppAcademia.git
cd AppAcademia
```

### 2. Configure o banco de dados

Crie um banco de dados PostgreSQL chamado `fitIA`.

```sql
CREATE DATABASE "fitIA";
```

Configure as credenciais e a URL de conexão no arquivo `src/main/resources/application.properties`, de acordo com as propriedades utilizadas pela aplicação.

Exemplo de configuração:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5433/fitIA}
spring.datasource.username=${DB_USERNAME:seu_usuario}
spring.datasource.password=${DB_PASSWORD:sua_senha}
```

Substitua `seu_usuario` e `sua_senha` pelas credenciais do seu ambiente. Verifique também se a porta configurada corresponde à porta do PostgreSQL utilizado.

**Importante:** não publique senhas, chaves privadas, credenciais OAuth ou outros segredos no repositório.

### 3. Execute a aplicação

No macOS ou Linux:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

Alternativamente, gere o arquivo JAR:

```bash
./mvnw clean package
```

Depois, execute o JAR gerado em `target/`, utilizando o nome correspondente ao arquivo produzido pelo Maven.

A aplicação será iniciada na porta configurada no projeto, normalmente `8080`.

## Documentação da API

O projeto utiliza SpringDoc OpenAPI para disponibilizar documentação interativa dos endpoints.

Após iniciar a aplicação, tente acessar:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

Esses endereços dependem da configuração efetiva da aplicação e da disponibilidade dos endpoints de documentação.

## Objetivos técnicos

O desenvolvimento do FitIA envolve a aplicação prática de conhecimentos em:

- Desenvolvimento de APIs REST com Java e Spring Boot.
- Modelagem de entidades e relacionamentos com JPA.
- Persistência de dados com PostgreSQL.
- Organização de aplicações em camadas.
- Implementação de regras de negócio.
- Autenticação e autorização com Spring Security.
- Validação e tratamento de dados.
- Documentação de APIs.
- Integração futura com serviços de inteligência artificial.

## Próximos passos

Entre as possibilidades de evolução do projeto estão:

- Aprimorar a personalização dos planos de treino.
- Expandir o acompanhamento do progresso físico.
- Integrar recursos de inteligência artificial.
- Desenvolver funcionalidades de planos alimentares.
- Ampliar a cobertura de testes automatizados.
- Melhorar a documentação e as estratégias de implantação.

## Autor

**Otto Luis**

- GitHub: [@OttoLuis](https://github.com/OttoLuis)
- Repositório: [AppAcademia — FitIA](https://github.com/OttoLuis/AppAcademia)

---

**Em Desenvolvimento**
