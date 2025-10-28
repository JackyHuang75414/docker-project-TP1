# TP-01
## Question 1-1: Why use the `-e` flag rather than writing environment variables in the Dockerfile?

For security reasons. Writing passwords in the Dockerfile causes them to be stored within the image, which is insecure. Using `-e` allows dynamic setting at runtime.

## Question 1-2: Why are data volumes required?

For data persistence. Without data volumes, data is lost when the container is deleted.

## Question 1-4: Why is multi-stage build required?

To reduce final image size and enhance security. The build stage contains build tools, while the run stage only includes the runtime environment.

## Question 1-5: Why is a reverse proxy required?

To provide a unified access point, enabling load balancing, SSL termination, caching, and other functionalities.

## Question 1-6: Why is Docker Compose important?

It simplifies the management of multi-container applications, enabling one-click startup of all services for streamlined development and deployment.


# TP-02
## Question 2-1: What are TestContainers?
TestContainers is a Java library that supports launching Docker containers during testing. It enables you to utilise genuine databases, message queues, and other services within tests, rather than relying on mock objects.

## Question 2-2: Why are secure secrets required?
Secure secrets safeguard sensitive information such as passwords, API keys, and similar credentials. Hard-coding such information in code poses security risks. GitHub Secrets provide a secure method for storing and accessing these sensitive details.

## Question 2-3: Why is `needs: test-backend` required?
This ensures Docker image builds and pushes only occur after tests pass. Should tests fail, the image for the problematic code will not be built or pushed.

## Question 2-4: Why is it necessary to push the Docker image?
Pushing the Docker image to a registry (such as Docker Hub) enables:
1. Deploying the same image across different environments
2. Implementing continuous deployment
3. Facilitating image sharing and usage among team members
4. Archiving the build artefact
5. Translated with DeepL.com (free version)

# TP-03
## Question3-1: Document your inventory and base commands
### Project initialisation
#### wsl
download wsl:\
`wsl --install
`\
and than, inside the wsl shell, we do the following steps
#### Ansible
download Ansible:\
`sudo apt update && sudo apt upgrade
`\
`sudo apt install ansible -y
`\
`ansible --version
`\
download Apache2:\
`ansible webservers -m apt -a "name=apache2 state=present" --become 
`\
start the service:\
`ansible webservers -m service -a "name=apache2 state=started" --become
`\
`ansible-playbook -i inventories/setup.yml site.yml --ask-vault-pass
`
the password is 000
### Inventory & basic common
#### test link
- `ansible all -i inventories/setup.yml -m ping`
    - the right output need to be some thing like：
```
{yourcloudname}.cloud | SUCCESS => {
"ansible_facts": {
"discovered_interpreter_python": "/usr/bin/python3"
},
"changed": false,
"ping": "pong"
}
```

#### collect the system info
- `ansible all -i inventories/setup.yml -m setup -a "filter=ansible_distribution*"`
    - the right output need to be some thing like：
```
{yourcloudname}.cloud | SUCCESS => {
"ansible_facts": {
"ansible_distribution": "Debian",
"ansible_distribution_file_parsed": true,
"ansible_distribution_file_path": "/etc/os-release",
"ansible_distribution_file_variety": "Debian",
"ansible_distribution_major_version": "12",
"ansible_distribution_minor_version": "12",
"ansible_distribution_release": "bookworm",
"ansible_distribution_version": "12.12",
"discovered_interpreter_python": "/usr/bin/python3"
},
"changed": false
}
```
## Question 3-2: Document your playbook
### Role Structure
* docker: Docker installation and configuration
* network: Docker network creation
* database: PostgreSQL database deployment
* backend: Spring Boot application deployment
* frontend: HTTPD frontend deployment

Execution Sequence: Control dependencies through the order of roles within the playbook

### Environment Variables
Consistent with docker-compose.yml:

Database: POSTGRES_DB=db, POSTGRES_USER=usr, POSTGRES_PASSWORD=pwd

Backend: SPRING_DATASOURCE_URL=jdbc:postgresql://database:5432/db

## Question 3-3: Document your docker_container tasks configuration.
```
- name:  PostgreSQL CONTAINER
  docker_container:
    name: database
    image: huang199864/tp-devops-database:latest
    state: started
    pull: yes
    restart_policy: always
    networks:
      - name: app-network
    env:
      POSTGRES_DB: "{{ db_name }}"
      POSTGRES_USER: "{{ db_user }}"
      POSTGRES_PASSWORD: "{{ db_password }}"
      
- name: Pull front-end image
  docker_image:
    name: "{{ frontend_image }}"
    source: pull

- name: Run front-end container
  docker_container:
    name: frontend
    image: "{{ frontend_image }}"
    state: started
    pull: yes
    restart_policy: always
    networks:
      - name: app-network
    ports:
      - "3000:3000"
    env:
      API_URL: "http://backend:8080"
      
- name: Run backend container
  docker_container:
    name: backend
    image: "{{ backend_image }}"
    pull: yes
    state: started
    restart_policy: always
    networks:
      - name: app-network
    env:
      SPRING_DATASOURCE_URL: "jdbc:postgresql://database:5432/{{ db_name }}"
      SPRING_DATASOURCE_USERNAME: "{{ db_user }}"
      SPRING_DATASOURCE_PASSWORD: "{{ db_password }}"
    ports:
      - "8080:8080"
```
