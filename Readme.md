# Relatório de Desenvolvimento: App de Estudos Mobile

## 📱 Visão Geral do Projeto
Este projeto foi desenvolvido durante a disciplina de **Tópicos em Desenvolvimento Mobile**, focado na criação de um ecossistema funcional para gestão de viagens e gastos.

## 🛠️ Tecnologias e Recursos Utilizados

*   **Intents:** Utilizadas para a navegação fluida entre as diferentes telas (Activities) do aplicativo.
*   **SQLite:** Implementado para o armazenamento persistente de dados estruturados e de maior volume.
*   **SharedPreferences:**
    *   Gerenciamento de sessão (login do usuário).
    *   Recurso de "Lembrar Usuário", preenchendo automaticamente os campos de login e senha para melhorar a experiência do usuário (UX).

## 📂 Estrutura do Aplicativo

### Tela de Menu
Após a autenticação, o usuário é direcionado para um menu principal contendo quatro opções:
1.  **Novo Gasto** (Em desenvolvimento)
2.  **Nova Viagem** (Funcional)
3.  **Minhas Viagens** (Funcional)
4.  **Gastos** (Em desenvolvimento)

### Funcionalidades Implementadas

#### 1. Nova Viagem
*   Tela programada para capturar e salvar os dados de novos roteiros diretamente no banco de dados local.

#### 2. Minhas Viagens
*   **Consulta:** Realiza a leitura dos dados salvos no SQLite.
*   **Exibição:** Apresenta as viagens em um componente de lista (`ListView` ou `RecyclerView`).
*   **Interatividade (Delete):** Implementada a funcionalidade de clique no item para remoção lógica e física (exclui o registro do banco de dados e atualiza a interface em tempo real).

---

> **Nota Técnica:** O fluxo de remoção ao clicar no item é uma forma eficiente de gerenciar o banco de dados via interface, garantindo que a lista visual esteja sempre em sincronia com o SQLite.