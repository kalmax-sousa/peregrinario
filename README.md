# Peregrinário

Aplicativo de Diário de Viagens desenvolvido para o Trabalho Final da disciplina de Desenvolvimento de Software para Dispositivos Móveis na UFC Quixadá, 2024.2.

**Dupla**:<br>
Francisco Vitor Gomes Castro <br>
Kalmax dos Santos souza

## Funcionalidades Principais

### 1. Gerenciamento de Conteúdo em uma Viagem (detalhes) (<span style="color:green">Implementado</span>)
- O usuário poderá visualizar as informações de uma viagem:
  - Nome do destino
  - Descrição
  - Datas da viagem
  - Imagens
  - Áudios
  - Status de Favorita
- (<span style="color:yellow">Planejamento</span>) O usuário deve poder editar/adicionar elementos na timeline de uma viagem:
  - Fotos com descrição e localização
  - Áudios gravados ou carregados
- (<span style="color:yellow">Planejamento</span>) O usuário deve visualizar os elementos na timeline organizados por ordem cronológica.

### 2. Visualização de Viagens (<span style="color:green">Implementado</span>)

- O usuário deve poder visualizar as viagens cadastradas em formato de cards, contendo:
  - Nome do destino
  - Datas da viagem
  - Descrição
  - 1 Foto destaque
- O usuário deve poder pesquisar viagens por destino.
- O usuário deve poder visualizar viagens favoritas.
- (<span style="color:yellow">Planejamento</span>) O usuário deve poder pesquisar viagens por data.

### 3. Menu Três Pontinhos (<span style="color:green">Implementado</span>)

-  Menu contendo a navegação entre as telas: 
   - Home
   - Favoritos
   - Configurações
   - Ajuda
- (<span style="color:yellow">Planejamento</span>) Navegação adicional: 
  - Perfil de usuário
  - Login
  

### 4. Tela de Configurações (<span style="color:green">Implementado</span>)

- Mudança de temas de cor (Claro e Escuro)
- Habilitar e desabilitar notificações
- Redefinir preferências

### 5. Tela de Suporte (<span style="color:green">Implementado</span>)

- Simulação de ajuda (FAQ)
- Simular envio de dúvidas por formulário (dispara notificação)

### 6. Cadastro de Viagens (<span style="color:yellow">Planejamento</span>)

- O usuário deve ser capaz de cadastrar uma nova viagem informando:
  - Nome do destino
  - Data de início 
  - Data do término
  - Lista de <span style="color:orange">**Registros** (Imagens e Áudios com descrição e localização)</span> 
  - Marcar como favorita

### 7. Tela de Perfil (<span style="color:red">Não implementado</span>)

- O usuário deve visualizar:
  - Foto do perfil
  - Estatísticas de viagens realizadas no ano
  - Classificação de acordo com o número de viagens realizadas.

### 8. Mapa Interativo (<span style="color:red">Não implementado</span>)

- O usuário deve poder abrir uma visualização de mapa mostrando os locais já visitados.
- Deve haver um botão para acessar o mapa (local a definir).

### 9. Login (<span style="color:green">Implementado</span>)

- O usuário deve poder realizar login com o Google.
- O usuário deve poder realizar login com o email e senha.
- O usuário deve poder realizar logout.
- O usuário deve poder se registrar com nome, email e senha.
- O usuário deve poder pedir email de recuperação.

### 10. Bottom Navigation (<span style="color:green">Implementado</span>)

- O usuário deve poder navegar entre as telas de Home, Favoritos, pesquisa e Perfil.
- O usuário deve poder criar uma nova viagem.

## Estrutura do Projeto

O projeto foi desenvolvido utilizando o framework Jetpack Compose com uma organização semelhante à demonstrada em sala de aula e em atividades anteriores.

O aplicativo usa um arquivo NavGraph que centraliza e define a navegação entre as telas usando um componente de top bar com um menu de três pontinhos.

A tela inicial (Home) contém uma barra de pesquisa para pesquisar viagens e uma lista de cards (Componente de card de viagem) com as viagens cadastradas. Os cards possuem um botão que permite navegar para a tela de detalhes e outro capaz de marcar a viagem como favorita.

### Estrutura de pastas

![alt text](img.png)

## Divisão de Trabalho

Durante o decorrer do trabalho, a dupla definiu tarefas individuais e também em conjunto, como o planejamento da estrutura do projeto. 

### Francisco Vitor Gomes Castro
- Configuração de Ambiente e implementação inicial
- Gerenciamento de Conteúdo em uma Viagem (Detalhes)
- Visualização de Viagens
- Tela de favoritos
- Menu Três Pontinhos (implementação)
- Paleta de cores e personalização (implementação inicial)
- Armazenamento de preferências com Jetpack DataStore (implementação)
- Progresso e feedback visual com progress bar's (implementação)
- Animações com jetpack compose animations nas telas e elementos interativos (implementação)
- Agendamento de notificação com BroadcastReceiver (implementação)

### Kalmax dos Santos Sousa
- Levantamento de requisitos
- Menu Três Pontinhos (complementação)
- Paleta de cores e personalização (complementação)
- Tela de Configurações (alternância de temas e notificações)
- Tela de Suporte (simulação de ajuda e envio de dúvidas)
- Gravação de video demonstração (Etapa 1)
- Banco de dados local com ROOM Database (Implementação)
- Firebase Realtime Database para salvar viagens (Implementação)
- Firebase user e google social Authentication (Implementação)
- Aplicando Sensibilidade ao contexto ao detectar conexão a internet (Implementação)
