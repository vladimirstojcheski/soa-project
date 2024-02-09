#!/bin/sh

# Wait for PostgreSQL to be ready
while ! pg_isready -h database -p 5432 -U postgres -d postgres; do
  echo "Waiting for PostgreSQL to be ready..."
  sleep 1
done

echo "PostgreSQL is ready. Executing schema creation script."
