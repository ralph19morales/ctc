# Makefile for building and deploying a Ktor app using Docker Compose

APP_NAME=ctc
DOCKER_COMPOSE=docker-compose
COMPOSE_FILE=compose.yaml

.PHONY: build up down logs restart clean

# Build the Ktor app Docker image
build:
	gradlew.bat clean buildFatJar

# Start the app using docker-compose
up: build
	docker compose  up

# Stop the app
down:
	docker compose down

# Show logs
logs:
	$(DOCKER_COMPOSE) -f $(COMPOSE_FILE) logs -f

# Restart the app
restart: down up

# Remove all containers, networks, and images created by up
clean: down
	- docker remove --force $(shell docker ps -aq)
	- docker rmi --force $(shell docker images -q)
	- docker volume prune -f
	- docker network prune -f