# aluga-simples
API de aluguel de veículos desenvolvida durante o curso de Quarkus pela plataforma ADA, programa CAIXAVERSO.

---
Para subir o projeto, execute em cada microsserviço:

* **mvn clean install**
* **mvn quarkus:dev**

---

Suba o servidor Keycloak usando o seguinte comando:

`
docker run -p 8083:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io keycloak/keycloak:21.1.1 start-dev
`

Obs: tenha Docker Desktop instalado na sua máquina caso esteja no Windows.