# Sistema de Análise de Votos

Este projeto é um sistema de análise de votos que permite visualizar e consultar informações detalhadas sobre candidatos, sessões eleitorais e regiões. Ele oferece funcionalidades como a análise do desempenho de candidatos em sessões específicas, geração de PDF com informações de um candidato, e a visualização de rankings de votos por bairro, sessão e zona eleitoral.

## Funcionalidades

O sistema oferece as seguintes funcionalidades através de um menu interativo:

1. **Analisar Candidato**: Exibe as sessões e bairros mais votados para um candidato específico.
2. **Consultar por Sessão**: Exibe o ranking de candidatos com base em uma sessão eleitoral e zona específica.
3. **Consultar por Região**: Exibe o ranking de candidatos mais votados em um bairro específico.
4. **Gerar PDF do Candidato**: Gera um arquivo PDF com informações detalhadas sobre um candidato específico.
5. **Sair**: Encerra o programa.

## Estrutura do Código

### 1. **Pacote `org.example.View`**

Este pacote contém a classe `View`, responsável por interagir com o usuário e exibir os dados processados. Ela utiliza o serviço de candidatos (`CandidatoService`) para exibir os rankings de votos e sessões mais votadas.

#### Métodos principais:
- **menuPrincipal**: Exibe o menu principal e gerencia a navegação do usuário pelas opções.
- **analisarCandidato**: Exibe as sessões e bairros mais votados para um candidato específico.
- **analisarSessao**: Exibe o ranking de candidatos para uma determinada sessão e zona eleitoral.
- **analisarRegiao**: Exibe o ranking de candidatos mais votados em um bairro específico.
- **gerarPDFdoCandidato**: Gera um PDF com informações detalhadas de um candidato.

### 2. **Pacote `org.example.Service`**

Contém as lógicas de negócios relacionadas à análise de votos e candidatos.

#### `CandidatoService`
- **votosPorSessao**: Calcula o total de votos de um candidato em uma determinada sessão.
- **rankCandidatosPorSessao**: Retorna o ranking de candidatos em uma sessão e zona eleitoral específica.
- **colegioMaisVotadasDeUmCandidato**: Identifica os colégios eleitorais onde um candidato recebeu mais votos.
- **bairrosMaisVotadasDeUmCandidato**: Exibe os bairros onde um candidato recebeu mais votos.
- **rankCandidatoVotadoBairro**: Exibe o ranking de candidatos com base em votos em um bairro específico.
- **retornaCandidato**: Retorna um candidato com base no número fornecido.
- **listaBairros**: Lista todos os bairros das sessões eleitorais.

#### `SessaoService`
- **listaBairros**: Retorna a lista de bairros das sessões eleitorais sem repetição.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal.
- **Stream API**: Para manipulação de coleções de forma eficiente.
- **GeradorPDF**: Utilizado para gerar relatórios PDF.
- **Coleções Java**: `List`, `Map`, `Set`, etc., para armazenamento e manipulação dos dados.
  
## Possíveis Melhorias Futuras

- Adicionar funcionalidades para exportar dados em outros formatos, como CSV.
- Implementar persistência de dados em um banco de dados para armazenar informações sobre candidatos, sessões e votos.
- Melhorar a interface para permitir uma navegação mais intuitiva.

## 🙋 Desenvolvedores:

🔴 [@oalleeN](https://github.com/oalleeN) - **Alan Filho**

⭕  [@lucascodebr20](https://github.com/lucascodebr20) - **Lucas Campos**

🔴 [@Kahmori](https://github.com/Kahmori) - **Karine Amorim**

⭕  [@throv](https://github.com/throv) - **Thaís Vieira**

🔴 [@nessartk](https://github.com/nessartk) - **Vanessa Rutkoski**
