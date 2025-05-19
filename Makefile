# Makefile for building and deploying a Ktor app using Docker Compose

APP_NAME=ctc
DOCKER_COMPOSE=docker-compose
COMPOSE_FILE=docker-compose.yaml

.PHONY: build up down logs restart clean

# Build the Ktor app Docker image
build:
	docker build -t $(APP_NAME):0.0.1 .

# Start the app using docker-compose
up: build
	$(DOCKER_COMPOSE) -f $(COMPOSE_FILE) up -d

# Stop the app
down:
	$(DOCKER_COMPOSE) -f $(COMPOSE_FILE) down

# Show logs
logs:
	$(DOCKER_COMPOSE) -f $(COMPOSE_FILE) logs -f

# Restart the app
restart: down up

# Remove all containers, networks, and images created by up
clean: down
	- docker image rm $(APP_NAME):latest