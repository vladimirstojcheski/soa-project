#!/bin/bash

# Database connection parameters
DB_HOST="database"  # Assuming the PostgreSQL service name in Docker Compose is 'database'
DB_PORT="5432"
DB_NAME="postgres"
DB_USER="postgres"
DB_PASSWORD="sysadm"

# SQL script to create schemas
SQL_SCRIPT=$(cat <<EOF
CREATE SCHEMA IF NOT EXISTS product_catalog;
CREATE SCHEMA IF NOT EXISTS order_management;
EOF
)

# Execute SQL script
PGPASSWORD="$DB_PASSWORD" psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -c "$SQL_SCRIPT"
