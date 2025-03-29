# Java Data Analysis Library

## Overview

This project aims to develop a Java library for manipulating tabular data, inspired by the Pandas library in Python.

It is developed as part of a university course, with a strong emphasis on DevOps practices: continuous integration, test coverage, automated delivery, containerization, cloud deployment, and integrated documentation.

## Features

- DataFrame creation:
  - from structured column data
  - from CSV files
- Display:
  - full dataset
  - partial views (`head`, `tail`)
- Selection:
  - rows by index
  - columns by label
  - conditional filtering
- Basic statistics: mean, min, max, etc.
- Data sorting
- Grouping and aggregation (`groupBy` + `aggregate`)
- Demonstration scenario runnable in Docker

## Tech Stack

| Domain           | Tool                              |
|------------------|-----------------------------------|
| Language         | Java                              |
| Build            | Maven                             |
| Testing          | JUnit                             |
| Code Coverage    | JaCoCo                            |
| Version Control  | Git (GitHub)                      |
| CI/CD            | GitHub Actions                    |
| Containerization | Docker                            |
| Cloud Deployment | Google Cloud (Terraform, Ansible) |
| Documentation    | Javadoc, GitHub Pages             |

## Git Workflow

We follow a feature branch workflow:
- **Feature Branches:** develop new features or fixes in separate branches.
- **Pull/Merge Requests:** merge changes into the main branch via pull/merge requests.
- **Code Reviews:** each request is reviewed before merging.
- **CI/CD:** automated tests and quality checks run on every request.

## Continuous Integration

The CI is configured to:
- build the project
- run all tests
- generate code coverage reports
- publish the library and Docker image on success

Status badges are displayed in the repository header:
- CI build status
- test coverage
- published version

## Docker

TODO 

## Project Checklist

### Development Environment
- [ ] Environment setup (Maven, JUnit, JaCoCo)
- [ ] Working CI pipeline with tests and coverage

### Library Features
- [ ] Implementation of `Series` and `DataFrame`
- [ ] CSV file reading
- [ ] Data display methods
- [ ] Selection by index, label, and condition
- [ ] Basic statistics and advanced operations

### Deployment & Publishing
- [ ] Automated Maven publishing
- [ ] Docker image with demonstration scenario
- [ ] Cloud deployment via Terraform and Ansible

### Documentation & Delivery
- [ ] Generated documentation and published site
- [ ] Final delivery on Moodle

