# BirthdayTracker

This project contains source code and supporting files for a serverless application that is deployed with the SAM CLI.

**The application stores names of people with their date of birth in a table called birthdayTracker and then retrieves:**  
**1. Date of birth of a person and the age  when we give input as name.**  
**2. Displays BirthdayTracker object with birthdays on particular month/day.**  

**Technologies used:**  
**Java,Lombok,Google Guice,Junit-Mockito,Maven,API Gateway,AWS Lambda ,DynamoDB,IntelliJ,Docker and Git.**  

It includes the following files and folders.

- BirthdayTrackerFunction/src/main/java/com/birthdaytracker/InsertBirthdayDateLambda- Code for the application's Lambda function which is used to insert data to birthdayTracker table.
- BirthdayTrackerFunction/src/main/java/com/birthdaytracker/GetDateOfBirthLambda - Code for the application's Lambda function which is used to retrieve date of birth and age of a person.
- BirthdayTrackerFunction/src/main/java/com/birthdaytracker/GetNamesPerMonthLambda - Code for the application's Lambda function which is used to retrieve BirthdayTracker object which has name,dateofbirth,month,date.
- BirthdayTrackerFunction/src/test/java/com/birthdaytracker/ - Contains Unit Tests for BirthdayTrackerFunction. 
- template.yaml - A template that defines the application's AWS resources.

The application uses several AWS resources, including Lambda functions and an API Gateway API. These resources are defined in the `template.yaml` file in this project. We can update the template to add AWS resources through the same deployment process that updates our application code.

I have used  [IntelliJ](https://docs.aws.amazon.com/toolkit-for-jetbrains/latest/userguide/welcome.html) an integrated development environment (IDE) to build and test our application.

## Deploy the sample application

The Serverless Application Model Command Line Interface (SAM CLI) is an extension of the AWS CLI that adds functionality for building and testing Lambda applications. It uses Docker to run our functions in an Amazon Linux environment that matches Lambda. It can also emulate our application's build environment and API.

To use the SAM CLI, you need the following tools.
 
* SAM CLI - [Install the SAM CLI](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html)
* Java11 - [Install the Java 11](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html)
* Maven - [Install Maven](https://maven.apache.org/install.html)
* Docker - [Install Docker community edition](https://hub.docker.com/search/?type=edition&offering=community)
* Git - [Install Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

## Deploy from your local machine

Before you start, run the following command to make sure you're in the correct AWS account (or configure as needed):

```bash
aws configure
```

```bash
sam build
sam deploy --guided
```

The first command will build the source of your application.
The SAM CLI installs dependencies defined in `BirthdayTrackerFunction/pom.xml`, creates a deployment package, and saves it in the `.aws-sam/build` folder.

The second command will package and deploy our application to AWS, with a series of prompts:

* **Stack Name**: The name of the stack to deploy to CloudFormation. This should be unique to your account and region, and a good starting point would be something matching your project name.
* **AWS Region**: The AWS region you want to deploy your app to.(Our application was tested in region us-west-2).
* **Confirm changes before deploy**: If set to yes, any change sets will be shown to you before execution for manual review. If set to no, the AWS SAM CLI will automatically deploy application changes.
* **Allow SAM CLI IAM role creation**: Many AWS SAM templates, create AWS IAM roles required for the AWS Lambda function(s) included to access AWS services. By default, these are scoped down to minimum required permissions. To deploy an AWS CloudFormation stack which creates or modified IAM roles, the `CAPABILITY_IAM` value for `capabilities` must be provided. If permission isn't provided through this prompt, to deploy this example you must explicitly pass `--capabilities CAPABILITY_IAM` to the `sam deploy` command.
* **Save arguments to samconfig.toml**: If set to yes, your choices will be saved to a configuration file inside the project, so that in the future you can just re-run `sam deploy` without parameters to deploy changes to your application.

We can find our API Gateway Endpoint URL in the output values displayed after deployment.

The SAM CLI reads the application template to determine the API's routes and the functions that they invoke. The `Events` property on each function's definition includes the route and method for each path.Yaml property changes for inserting data to birthdayTracker table  are provided below.

```yaml
      Events:
        BirthdayTracker:
          Type: Api
          Properties:
            Path: /create
            Method: post
```
Yaml property changes for retrieving date of birth and age from birthdayTracker table are provided below.
```yaml
 Events:
        BirthdayTracker:
          Type: Api 
          Properties:
            Path: /getDateOfBirth/{name}
            Method: get
```
Yaml property changes for retrieving BirthdayTracker object from birthdayTracker table based on month and date are provided below.
```yaml
Events:
        BirthdayTracker:
          Type: Api
          Properties:
            Path: /getNamesPerMonth/{month}/{date}
            Method: get
```
            
            

## Add a resource to your application
The application template uses AWS Serverless Application Model (AWS SAM) to define application resources. AWS SAM is an extension of AWS CloudFormation with a simpler syntax for configuring common serverless application resources such as functions, triggers, and APIs. For resources not included in [the SAM specification](https://github.com/awslabs/serverless-application-model/blob/master/versions/2016-10-31.md), you can use standard [AWS CloudFormation](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/aws-template-resource-type-ref.html) resource types.

## Fetch, tail, and filter Lambda function logs

To simplify troubleshooting, SAM CLI has a command called `sam logs`. `sam logs` lets you fetch logs generated by your deployed Lambda function from the command line. In addition to printing the logs on the terminal, this command has several nifty features to help you quickly find the bug.

`NOTE`: This command works for all AWS Lambda functions; not just the ones you deploy using SAM.

```bash
BirthdayTracker$ sam logs -n BirthdayTrackerFunction --stack-name BirthdayStack --tail
```

You can find more information and examples about filtering Lambda function logs in the [SAM CLI Documentation](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-logging.html).

## Unit tests

Tests are defined in the `BirthdayTrackerFunction/src/test` folder in this project.

```bash
BirthdayTracker$ cd BirthdayTrackerFunction
BirthdayTrackerFunction$ mvn test
```

## Cleanup

To delete the sample application that you created, use the AWS CLI. Assuming you used the stack name BirthdayStack, you can run the following:

```bash
aws cloudformation delete-stack --stack-name BirthdayStack
```

## Resources

See the [AWS SAM developer guide](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/what-is-sam.html) for an introduction to SAM specification, the SAM CLI, and serverless application concepts.

Next, you can use AWS Serverless Application Repository to deploy ready to use Apps that go beyond hello world samples and learn how authors developed their applications: [AWS Serverless Application Repository main page](https://aws.amazon.com/serverless/serverlessrepo/)
