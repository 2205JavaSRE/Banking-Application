# Adapted from the AWS Open Source Blog by Irshad Buchh
provider "aws" {
  region                  = "${var.region}"
  # shared_credentials_file = "~/.aws/credentials"
  # profile                 = "development"
}

