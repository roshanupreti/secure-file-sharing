build:
	mvn clean install -DskipTests

deploy:
	docker-compose down --remove-orphans
	docker-compose up -d