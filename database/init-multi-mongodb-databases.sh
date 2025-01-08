#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e
# Treat unset variables as an error when substituting
set -u

function create_mongo_database() {
    database=$1
    password=$2
    echo "Creating database '$database' with user '$database' and password '$password'"
    mongo --username "$MONGO_INITDB_ROOT_USERNAME" --password "$MONGO_INITDB_ROOT_PASSWORD" <<EOF
use $database;
db.createUser({
  user: "$database",
  pwd: "$password",
  roles: [ { role: "readWrite", db: "$database" } ]
});
EOF
}

# Parse the environment variable
if [ -n "$MONGO_MULTIPLE_DATABASES" ]; then
  echo "Multiple database creation requested: $MONGO_MULTIPLE_DATABASES"
  for db in $(echo $MONGO_MULTIPLE_DATABASES | tr ',' ' '); do
    user=$(echo $db | awk -F":" '{print $1}')
    pswd=$(echo $db | awk -F":" '{print $2}')
    if [[ -z "$pswd" ]]; then
      pswd=$user
    fi
    echo "Creating database for user: $user with password: $pswd"
    create_mongo_database "$user" "$pswd"
  done
fi
