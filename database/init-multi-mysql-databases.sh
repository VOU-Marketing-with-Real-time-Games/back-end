#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e
# Treat unset variables as an error
set -u

function create_databases() {
    database=$1
    password=$2
    echo "Creating user and database '$database' with password '$password'"
    if ! mysql -u root -p"$MYSQL_ROOT_PASSWORD" <<-EOSQL; then
        CREATE USER '$database'@'%' IDENTIFIED BY '$password';
        CREATE DATABASE $database;
        GRANT ALL PRIVILEGES ON $database.* TO '$database'@'%';
        FLUSH PRIVILEGES;
EOSQL
        echo "Error creating user and database '$database'"
    fi
}

function run_sql_script() {
    database=$1
    script=$2
    echo "Running SQL script '$script' on database '$database'"
    if ! mysql -u root -p"$MYSQL_ROOT_PASSWORD" $database < "$script"; then
        echo "Error executing SQL script '$script' on database '$database'"
        mysql -u root -p"$MYSQL_ROOT_PASSWORD" $database < "$script" 2>&1
    fi
}


# Read and process the MYSQL_MULTIPLE_DATABASES environment variable
if [ -n "$MYSQL_MULTIPLE_DATABASES" ]; then
    echo "Multiple database creation requested: $MYSQL_MULTIPLE_DATABASES"
    for db in $(echo $MYSQL_MULTIPLE_DATABASES | tr ',' ' '); do
        user=$(echo $db | awk -F":" '{print $1}')
        pswd=$(echo $db | awk -F":" '{print $2}')
        if [[ -z "$pswd" ]]; then
            pswd=$user
        fi
        echo "Creating database '$user' with password '$pswd'"
        create_databases $user $pswd
        sql_file="/docker-entrypoint-initdb.d/sql/${user}.sql"
        if [ -f "$sql_file" ]; then
            run_sql_script $user "$sql_file"
        else
            echo "No SQL script found for database '$user'"
        fi
    done
    echo "All databases created successfully!"
fi

mysql -u root -p"$MYSQL_ROOT_PASSWORD" <<-EOSQL
CREATE USER 'replica_user'@'%' IDENTIFIED BY 'replica_pass';
GRANT REPLICATION SLAVE ON *.* TO 'replica_user'@'%';
FLUSH PRIVILEGES;
CREATE USER 'monitor_user'@'%' IDENTIFIED BY 'monitor_password';
GRANT SHOW DATABASES, SHOW STATUS, SHOW VARIABLES ON *.* TO 'monitor_user'@'%';
EOSQL
