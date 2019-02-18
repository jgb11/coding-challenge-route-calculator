NAMESPACE?=jgb
PROJECT_NAME?=coding-challenge-route-calculator

DOCKER_IMG_NAME_TMP?=${NAMESPACE}/${PROJECT_NAME}:tmp
DOCKER_IMG_NAME_LATEST?=${NAMESPACE}/${PROJECT_NAME}:latest
DOCKER_IMG_NAME_TAG?=${NAMESPACE}/${PROJECT_NAME}:${PROJECT_VERSION}

DOCKER?=sudo docker
MVN?=mvn

.PHONY: default
default: build

.PHONY: install
install:
	${MVN} -f ../pom.xml clean package
	cp ../target/${PROJECT_NAME}-${PROJECT_VERSION}.jar .
	${MVN} -f ../pom.xml dockerfile:build

.PHONY: build
build: install
	${DOCKER} build -t ${DOCKER_IMG_NAME_TMP} .

.PHONY: tag
tag: build
	${DOCKER} tag ${DOCKER_IMG_NAME_TMP} ${DOCKER_IMG_NAME_TAG}
	${DOCKER} tag ${DOCKER_IMG_NAME_TMP} ${DOCKER_IMG_NAME_LATEST}

.PHONY: clean
clean:
	rm -rf *.jar
	cd ../ && ${MVN} clean

.PHONY: rmi
rmi:
	-${DOCKER} rmi ${DOCKER_IMG_NAME_TMP} ${DOCKER_IMG_NAME_TAG} ${DOCKER_IMG_NAME_LATEST}
