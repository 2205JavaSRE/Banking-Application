#!/bin/bash
echo "Beginning Jenkins Migration"

echo "extracting tar"
tar -xvzf jenkins-migrate.tgz

echo "stopping jenkins"
sudo service jenkins stop

echo "migrating jenkins config"
sudo rm -R /var/lib/jenkins
sudo mv jenkins /var/lib
sudo chown -R jenkins:jenkins /var/lib/jenkins

echo "migration complete - starting jenkins"
sudo service jenkins start

echo "DONE"