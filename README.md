# Desenvolvimento de Software Orientado a Objetos (Unified Health System)

<p align="center"><img src="src/frontend/assets/logo.png" width="250px"></p>

Este repositório trata-se de um trabalho de **Desenvolvimento de Software Orientado a Objetos (DOOC5)** do **Instituto Federal de Ciências e Tecnologia de São Paulo (IFSP) - Campus Piracicaba**. Este grupo composto pelos integrantes desenvolvedores Aldemir Humberto Soares Neto, Isabelle Caroline de Carvalho Costa, João Pedro Carpanezi dos Santos e Murilo Azevedo Jacon. O projeto foi desenvolvido utilizando Java com os frameworks Spring Boot, Hibernate e JPA e banco de dados MySQL, e juntos compõe um conjunto de APIs Restful com a proposta de um sistema unificado de saúde para todo o país, permitindo que em qualquer cidade que a pessoa visite um hospital todo seu histórico médico possa ser resgatado.

## Instalação com Docker

**1º)** Clone o repositório
```
git clone https://github.com/jpcarpanezi/dooc-health-system.git
```

**2º)** Execute o arquivo YAML do Docker Compose em seu terminal<br>

**Atenção:** Caso as portas 8080 ou 3306 estejam em uso será necessário alterar as portas no arquivo YAML, ou liberar para que as requisições sejam feitas.

```
docker-compose up -d
```

**Extra)** Acesse localhost:8080 para visualizar um front-end de atalho para as requisições da API

## Licença 

Este projeto é um trabalho de cunho acadêmico, voltado para conhecimento de estrutura de dados e sem fins lucrativos. Olhe o arquivo de <a href="https://github.com/jpcarpanezi/dooc-health-system/blob/master/LICENSE" target="_blank">LICENSE</a> para direitos e limitações (MIT).
