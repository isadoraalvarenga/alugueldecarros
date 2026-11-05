# Sistema de Aluguel de Carros

Projeto da disciplina DIAW - PUC Minas (Laboratório 02).

## Tecnologias
Java 25, Spring Boot 4.0.x, Maven, React + Vite, Supabase (PostgreSQL), JWT.

## Arquitetura
MVC no back-end (controller, service, repository, model), front-end em React consumindo a API REST. Diagramas em `docs/`.

## Como rodar
Back-end (na raiz): defina `application-local.properties` com a URL, usuário e senha do Supabase e `jwt.secret`, e rode:
`.\mvnw spring-boot:run "-Dspring-boot.run.profiles=local"`

Front-end: `cd frontend`, `npm install`, `npm run dev` (http://localhost:5173).

## Funcionalidades
Login e cadastro, CRUD de clientes, cadastro de automóveis, criação e consulta de pedidos, avaliação de pedidos por agentes.

## Prints

![Diagrama de classes](docs/classes.png)
![Diagrama de casos de uso](docs/casosdeuso.png)
![Diagrama de pacotes](docs/pacotes.png)
![Print do cadastro](docs/cadastro.png)
![Print da tela principal](docs/telap.png)

## Limitações
Qualquer usuário pode se cadastrar como agente; o usuário não está vinculado à entidade Cliente; contrato de crédito ainda não foi implementado.


# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Oxc](https://oxc.rs)
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/)

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.
