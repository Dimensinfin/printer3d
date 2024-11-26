#!/bin/zsh

# - Define deploy environment
PROJECT_REPOSITORY=https://github.com/Dimensinfin/printer3d.git
PROJECT_NAME=printer3d
TARGET_BRANCH=${1:-develop}
FRONTEND_DEPLOY_DIR=../Printer3d-frontend/.deploy
BACKEND_DEPLOY_DIR=../Printer3d-backend/.deploy

# Define the file to store the last deployed commit ID
COMMIT_ID_FILE=".lastCommitId-${TARGET_BRANCH}"

# Fetch the latest commit ID from the develop branch
LATEST_COMMIT_ID=$(git rev-parse origin/${TARGET_BRANCH})

# Check if the commit ID file exists and read the last deployed commit ID
if [ -f "$COMMIT_ID_FILE" ]; then
	LAST_COMMIT_ID=$(head -n 1 "${COMMIT_ID_FILE}")
else
	LAST_COMMIT_ID=""
fi

# Compare the latest commit ID with the last deployed commit ID
echo "Validating deploy configuration..."
echo "TARGET_BRANCH->"${TARGET_BRANCH}
echo "LAST_COMMIT_ID->"${LAST_COMMIT_ID}
echo "LATEST_COMMIT_ID->"${LATEST_COMMIT_ID}
if [ "$LATEST_COMMIT_ID" = "$LAST_COMMIT_ID" ]; then
	echo "No changes in the develop branch. Skipping deployment."
else
	echo "Changes detected in the develop branch. Proceeding with deployment..."

	# Create new image and deploy to Kubernetes
	cd ${BACKEND_DEPLOY_DIR}
	echo "Moving to directory ${BACKEND_DEPLOY_DIR}"
	./deploy-backend.zsh
	if [[ $? -ne 0 ]]; then
		echo "Error building Docker image."
		exit 1
	fi

	# Update the last deployed commit ID
	echo "$LATEST_COMMIT_ID" >"../../$COMMIT_ID_FILE"

	echo "Deployment completed."
fi

function deployBackend() {
	deploy-backend.zsh
	if [[ $? -ne 0 ]]; then
		echo "Error building backend."
		exit 1
	fi
	echo "Backend deployment completed."
}
