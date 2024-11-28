#!/bin/zsh

# - Set the path locations from the script place
# Get the directory of the currently running script
# Move one level up from the script's directory
local BASE_DIR="$(dirname "$(dirname "$(readlink -f "$0")")")"

# Print the base directory
echo "Base directory: $BASE_DIR"

# You can now use $BASE_DIR to reference files relative to the script's location
# For example, if you have a file named 'config.txt' in the same directory as the script:
CONFIG_FILE="$BASE_DIR/config.txt"

# Set default environment
export ENVIRONMENT=${1:-develop} # Accepts the first argument or defaults to 'develop'
export NAMESPACE=$ENVIRONMENT

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
    ./gradlew clean bootJar
    if [[ $? -ne 0 ]]; then
        echo "Error compiling the application."
        exit 1
    fi
    echo "Generated application..."
    ls -la ./build/libs/*.jar
    echo "Compilation successful."
}

# Function to build the Docker image
build_docker_image() {
    echo "Building the Docker image..."
    echo "IMAGE_NAME->"$IMAGE_NAME
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

# Move to the working directory for processing
move_directory() {
    echo "Current directory: $(pwd)"
    local destination="$1"
    echo "Move to directory $destination"
    cd $destination
    if [[ $? -ne 0 ]]; then
        echo "Error moving to processing directory."
        exit 1
    fi
}

# Main script execution
main() {
    # Export environment variables from .env file
    export_env_variables

    # Move to the working directory for processing
    move_directory ${WORKING_DIR}

    # Compile the application
    compile_application

    # Build the Docker image
    # build_docker_image

    # Tag and push the Docker image
    # tag_and_push_docker_image

    # Deploy to Kubernetes
    deploy_to_kubernetes
}

# Run the main function
# main
