# Java Data Analysis Library

[![CI](https://github.com/hugorouillard/java-dataframe/actions/workflows/ci.yml/badge.svg)](https://github.com/hugorouillard/java-dataframe/actions/workflows/ci.yml)
[![codecov](https://codecov.io/gh/hugorouillard/java-dataframe/graph/badge.svg?token=LAYFTJTRZQ)](https://codecov.io/gh/hugorouillard/java-dataframe)
[![License](https://img.shields.io/github/license/hugorouillard/java-dataframe)](https://github.com/hugorouillard/java-dataframe/blob/main/LICENSE)


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
- [X] Environment setup (Maven, JUnit, JaCoCo)
- [X] Working CI pipeline with tests and coverage

### Library Features
- [X] Implementation of `Series` and `DataFrame`
- [X] CSV file reading
- [X] Data display methods
- [X] Selection by index, label, and condition
- [X] Basic statistics and advanced operations

### Deployment & Publishing
- [X] Maven package publishing
- [X] Javadoc deployment to GitHub Pages
- [ ] Docker image with demonstration scenario
- [ ] Cloud deployment via Terraform and Ansible
