# **Transação API-Desafio técnico Itaú**

Este projeto é uma API REST para gerenciar transações e calcular estatísticas das transações realizadas nos últimos 60 segundos. A API foi desenvolvida com **Java** e **Spring Boot**.

## **Variáveis de Ambiente**

Para rodar esta aplicação, você precisa ter instalado:
- **Java**: JDK 21 ou superior
- **Gradle**: Versão 7.0 ou superior
- **Git**: Para clonar o repositório
- **Docker (opcional)**: Caso queira rodar a aplicação em um container

## **Como Configurar o Projeto**

### 1. Clone o Repositório
```bash
git clone <URL_DO_REPOSITORIO>
cd api-transacoes
```

### 2. Compile o Projeto
```bash
./gradlew build
```

### 3. Execute o Projeto
```bash
./gradlew bootRun
```

### 4. Como Rodar em um Container (Opcional)
./docker build -t api-transacoes ."

#### 4.1. Crie a Imagem Docker
Certifique-se de que o **Docker** está instalado e execute:
```bash
docker build -t api-transacoes .
```

#### 4.2. Execute o Container
```bash
docker run -p 8080:8080 api-transacoes
```

## **Documentação da API**

### **Criar uma Transação**
```http
POST /transacao
```
**Body (JSON)**
```json
{
  "valor": 100.50,
  "dataHora": "2025-03-04T12:00:00Z"
}
```

| Parâmetro   | Tipo            | Descrição                                      |
|------------|----------------|----------------------------------------------|
| `valor`    | `BigDecimal`    | **Obrigatório**. O valor da transação.        |
| `dataHora` | `OffsetDateTime` | **Obrigatório**. Data e hora da transação.   |

---

### **Excluir Todas as Transações**
```http
DELETE /transacao
```

---

### **Obter Estatísticas das Transações**
```http
GET /estatistica
```

| Parâmetro           | Tipo      | Descrição                                           |
|--------------------|---------|---------------------------------------------------|
| `intervaloSegundos` | `integer` | **Opcional**. Padrão = 60 segundos.                 |

