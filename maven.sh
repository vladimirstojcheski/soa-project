#!/bin/bash

NEXUS_USERNAME="admin"
NEXUS_URL="http://localhost:8090/repository/maven-releases/"

MVN="mvn"
MVN_CLEAN="$MVN clean"
MVN_INSTALL="$MVN install"
MVN_DEPLOY="$MVN deploy"
grep -r "product-catalog-0.0.1.jar" .
echo "Current working directory: $(pwd)"
echo "Contents of parent directory:"
find / -type d -name "product"
PROJECT_DIR="./product\ catalog/src/main/java/mk/ukim/finki/soa/productcatalog/model"

cd "$PROJECT_DIR" || exit

echo "Building the project..."
$MVN_CLEAN && $MVN_INSTALL

echo "Deploying the artifact to Nexus..."
$MVN_DEPLOY -Dmaven.test.skip=true -DaltDeploymentRepository=nexus::default::"$NEXUS_URL" -DrepositoryId=nexus -Durl="$NEXUS_URL" -Dusername="$NEXUS_USERNAME"
