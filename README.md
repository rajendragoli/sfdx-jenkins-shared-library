# sfdx-jenkins-shared-library

Provides [building blocks](/vars) for Jenkins pipeline builds to avoid duplication of boilerplate including both code and data references.
For some background information, see e.g. [Share a standard Pipeline across multiple projects with Shared Libraries](https://jenkins.io/blog/2017/10/02/pipeline-templates-with-shared-libraries/)

## Why

The two aims of this library are:

* To avoid the duplication of 150+ lines of `Jenkinsfile` logic so a reliable pattern can be applied and maintained.

  This is accomplished by providing custom pipeline steps that hide some of the detail.
  A default pipeline that sits on top of these steps is also provided.
  
* To make the process of testing against various org configurations - e.g. person Accounts turned on or Platform Encryption turned on - simple.

  When multiple `project-scratch-def.json` files are provided that match a regular expression, parallel builds are done
  using scratch orgs created from the files.

## Prerequsities

Requires the [Salesforce SFDX CLI](https://developer.salesforce.com/docs/atlas.en-us.sfdx_setup.meta/sfdx_setup/sfdx_setup_install_cli.htm) to be installed where jenkins is running.

Requires https://wiki.jenkins.io/display/JENKINS/Credentials+Binding+Plugin.

Requires cleanup plugin too.

## Pipeline

A ready-made pipeline is available. To use it, your `Jenkinsfile` should look like this:
```
#!groovy
@Library('sfdx-jenkins-shared-library')
import com.claimvantage.jsl.Help
import com.claimvantage.jsl.Package

buildPackagePipeline(
    help: new Help('cx', '33226968', 'extras-help'),
    packages: [
        new Package('Claims v14.4', '04t2J000000AksW', env.'cve.package.password.v12'),
        new Package('Absence v14.1', '04t0V000000xDzW', env.'cvab.package.password.v12')
    ]
)
```
Edit the Help and Package details to reflect the specific project.

To build a package that has no help and does not depend on other packages the `Jenkinsfile` simplifies to this:
```
#!groovy
@Library('sfdx-jenkins-shared-library')_

buildPackagePipeline()
```
Note the added, required underscore.

The available value names are:

* _glob_

  The matching pattern used to find the `project-scratch-def.json` files. Each matched file results in a separate parallel build.
  The default value is "config/project-scratch-def.*.json"; this assumes that an extra part will be insered into the file names.

* _help_

  A simple bean object that holds the values needed to extract, process, and commit the help.
  When left out, no help processing is done.

* _packages_

  And array of simple bean objects holding the values needed to install existing managed package versions.
  When left out, no packages installations are done.

## Steps
