#!/bin/zsh

# Set default environment
export ENVIRONMENT=${1:-develop} # Accepts the first argument or defaults to 'develop'
export NAMESPACE=$ENVIRONMENT

# Function to generate the version from gitversion
generate_version() {
    VERSION=$(gitversion /showvariable AssemblySemFileVer 2>/dev/null)
    if [[ $? -ne 0 ]]; then
        echo "Error getting version from gitversion."
        exit 1
    fi
    echo "Version to deploy: ${VERSION}"
    export VERSION=${VERSION}
}

# Function to export environment variables from .env file
export_env_variables() {
    if [[ -f .env ]]; then
        echo "Exporting variables from .env file..."
        source .env
        echo "Environment variables exported."
    else
        echo ".env file not found. Skipping export."
    fi
}

# Function to compile the Spring Boot application
compile_application() {
    echo "Compiling the ${APP_CODE} application..."
    echo "Move to directory ${WORKING_DIR}"
    (cd "${WORKING_DIR}" && ./gradlew clean bootJar)
    if [[ $? -ne 0 ]]; then
        echo "Error compiling the application."
        exit 1
    fi
    echo "Compilation successful."
}

# Function to build the Docker image
build_docker_image() {
    echo "Building the Docker image..."
    echo "IMAGE_NAME->"$IMAGE_NAME
    cd "${WORKING_DIR}"
    echo "Move to directory ${WORKING_DIR}"
    (envsubst <${DEPLOY_DIR}/dockerfile.template >${DEPLOY_DIR}/Dockerfile &&
        docker build -f ${DEPLOY_DIR}/Dockerfile -t ${IMAGE_NAME} .)
    if [[ $? -ne 0 ]]; then
        echo "Error building Docker image."
        exit 1
    fi
    echo "Docker image built successfully."
}

# Function to tag and push the Docker image
tag_and_push_docker_image() {
    echo "Tagging and pushing the Docker image..."
    echo "IMAGE_NAME->"$IMAGE_NAME
    echo "PROJECT_NAME->"$PROJECT_NAME
    echo "TAG->"$TAG
    (docker tag ${IMAGE_NAME} adamantinoo/${TAG} &&
        docker tag ${IMAGE_NAME} ${PROJECT_NAME}/${TAG} &&
        docker push adamantinoo/${TAG})
    if [[ $? -ne 0 ]]; then
        echo "Error pushing the Docker image to the registry."
        exit 1
    fi

    echo "Docker image tagged and pushed successfully."
}

# Function to deploy to Kubernetes
deploy_to_kubernetes() {
    echo "Deploying to Kubernetes..."
    echo "Prepare environment..."
    NAMESPACE=$ENVIRONMENT
    echo "NAMESPACE->"$NAMESPACE
    echo "BACKEND_CONFIG_MAP->"$BACKEND_CONFIG_MAP
    echo "PORT->"$PORT
    echo "EXPOSE_PORT->"$EXPOSE_PORT
    echo "IMAGE_NAME->"$IMAGE_NAME
    echo "TAG->"$TAG
    echo "IMAGE_TAG->"$IMAGE_TAG
    echo "VERSION->"$VERSION

    cd "${WORKING_DIR}"
    echo "Move to directory ${WORKING_DIR}"
    cat ${DEPLOY_DIR}/deployment.template.yaml | envsubst >${DEPLOY_DIR}/deployment.yaml
    kubectl apply -f ./${DEPLOY_DIR}/deployment.yaml
    if [[ $? -ne 0 ]]; then
        echo "Error deploying to Kubernetes."
        exit 1
    fi
    echo "Deployment to Kubernetes successful."
}

# Main script execution
main() {
    # Generate version
    # generate_version

    # Export environment variables from .env file
    export_env_variables

    # # Compile the application
    # compile_application

    # # Build the Docker image
    # build_docker_image

    # # Tag and push the Docker image
    tag_and_push_docker_image

    # Deploy to Kubernetes
    deploy_to_kubernetes
}

# Run the main function
main
