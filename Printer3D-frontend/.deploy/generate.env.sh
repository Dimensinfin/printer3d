echo ">>> Generating environment : $NODE_ENV"
echo '> Source environment'
cat ./config/.env.$NODE_ENV
. ./config/.env.$NODE_ENV
echo '<<<<<<<<<<<< end environment'
