#!/bin/zsh

# - Set the path locations from the script place
# Get the directory of the currently running script
# Move one level up from the script's directory
local BASE_DIR="$(dirname "$(dirname "$(readlink -f "$0")")")"

figlet "P3D-deploy"

# Environment routes
export JAVA_HOME=/Users/adam/Library/Java/JavaVirtualMachines/azul-11.0.23/Contents/Home
export APPLICATION_DIR=${BASE_DIR}
local APPLICATION_DEPLOY_DIR=${APPLICATION_DIR}/.deploy

# - Define deploy environment
local TARGET_BRANCH=${1:-develop}

function getLastCommit() {
	echo "Checking if deployment has changed..."
	local targetBranch="$1"
	echo "  targetBranch->"$targetBranch
	# Define the file to store the last deployed commit ID
	local commitFilePath=${APPLICATION_DEPLOY_DIR}"/.currentCommitId-$targetBranch"
	echo "Getting commit identifications to detect branch change..."
	# Fetch the latest commit ID from the develop branch
	local lastCommitId=$(git rev-parse origin/$targetBranch)
	# Check if the commit ID file exists and read the last deployed commit ID
	local currentCommitId="<not defined>"
	if [ -f "$commitFilePath" ]; then
		currentCommitId=$(head -n 1 "$commitFilePath")
	fi
	echo "  currentCommitId->"$currentCommitId
	echo "  lastCommitId->"$lastCommitId
	if [ $currentCommitId = $lastCommitId ]; then
		return 0
	else
		return 1
	fi
}

function updateCommitId() {
	echo "Registering commit completed..."
	local targetBranch="$1"
	local commitFilePath=${APPLICATION_DEPLOY_DIR}"/.currentCommitId-$targetBranch"
	local lastCommitId=$(git rev-parse origin/$targetBranch)
	# Update the last deployed commit ID
	echo $lastCommitId > $commitFilePath
}

function deployBackend() {
	echo "Starting backend deployment..."
	local applicationDir="$1"
	${applicationDir}/Printer3d-backend/.deploy/deploy-backend.zsh
	if [[ $? -ne 0 ]]; then
		echo "Error deploying backend module."
		exit 1
	fi
	echo "Backend deployment completed."
}
function deployBackend() {
	echo "Frontend: on construction"
}

# Main script execution
main() {
	echo "Starting application P3D deployment..."
	echo "APPLICATION_DIR->"${APPLICATION_DIR}
	if getLastCommit ${TARGET_BRANCH}; then
		echo "No changes in the develop branch. Skipping deployment."
	else
		echo "Changes detected in the develop branch. Proceeding with deployment..."
		deployBackend "${APPLICATION_DIR}"
		deployFrontend "${APPLICATION_DIR}"

		updateCommitId "${TARGET_BRANCH}"
		echo "Deployment completed successfully."
	fi
}
# Run the main function
main
