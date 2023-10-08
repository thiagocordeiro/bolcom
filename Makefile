configure:
	npm run build --prefix .frontend
	rm -rf src/main/resources/dist
	cp -R .frontend/dist src/main/resources/dist
	rm -rf build
	./gradlew build

up:
	docker-compose up -d --build
	make logs

logs:
	docker-compose logs -f

down:
	docker-compose down

test:
	./gradlew test
