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
Login e cadastro, CRUD de clientes, cadastro de automóveis, criação e consulta de pedidos, avaliação de pedidos por agentes, contrato de crédito: agente registra valor e banco de um pedido.

## Prints

![Diagrama de classes versao 1](docs/classes.png)
![Diagrama de classes versao 2](docs/classesatualizadas.png)
![Diagrama de classes versao final](docs/classesdiagramafinal.png)
![Diagrama de casos de uso](docs/casosdeuso.png)
![Diagrama de pacotes](docs/pacotes.png)
![Print da tela do cliente](docs/telacliente.png)
![Print da tela de login](docs/telalogin.png)

## Limitações

- O contrato de crédito existe só na API (sem tela no front-end).
- Agentes (empresas e bancos) são representados apenas pelo perfil de login, sem entidade própria.
- O cadastro permite escolher o perfil AGENTE livremente; em produção isso seria restrito.
- O usuário ainda não está vinculado à entidade Cliente.
- O front-end edita um rendimento por cliente, embora o back-end aceite até 3.


# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Oxc](https://oxc.rs)
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/)

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.
