#!/bin/zsh

# - Remove commit identifier
TARGET_BRANCH=${1:-develop}
echo "Clearing deploy status for environment: $TARGET_BRANCH"
local commitFilePath=".currentCommitId-$TARGET_BRANCH"
echo "<clear>" >$commitFilePath
