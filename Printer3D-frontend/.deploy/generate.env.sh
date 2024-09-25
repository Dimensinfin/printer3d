echo "----- Generating environment : $NODE_ENV"
echo '> Source environment'
cat ./config/.env.$NODE_ENV
echo '<<<<<<<<<<<< end environment'
. ./config/.env.$NODE_ENV
echo '> Current environment'
# printenv | sort
echo '<<<<<<<<<<<< end environment'
cat ./.deploy/config.template.json | envsubst > ./config/default.json
echo '> New configuration'
cat ./config/default.json
echo '<<<<<<<<<<<< end configuration'
