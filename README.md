# Java Data Analysis Library

[![CI](https://github.com/hugorouillard/java-dataframe/actions/workflows/ci.yml/badge.svg)](https://github.com/hugorouillard/java-dataframe/actions/workflows/ci.yml)
[![codecov](https://codecov.io/gh/hugorouillard/java-dataframe/graph/badge.svg?token=LAYFTJTRZQ)](https://codecov.io/gh/hugorouillard/java-dataframe)
[![Javadoc](https://img.shields.io/badge/docs-javadoc-blue)](https://hugorouillard.github.io/java-dataframe/)
[![License](https://img.shields.io/github/license/hugorouillard/java-dataframe)](https://github.com/hugorouillard/java-dataframe/blob/main/LICENSE)
[![Docker Hub](https://img.shields.io/docker/pulls/hugorouillard/java-dataframe-demo?label=Docker%20Hub)](https://hub.docker.com/r/hugorouillard/java-dataframe-demo)


## Overview

This project aims to develop a Java library for manipulating tabular data, inspired by the [Pandas](https://pandas.pydata.org/) library in Python.

It was developed as part of a university DevOps project (UFR IM2AG, M1 INFO), with a focus on software engineering best practices: testing, code quality, collaboration workflows, automation, delivery pipelines, and documentation.
More details about the course here: [https://tropars.github.io/teaching/#devops](https://tropars.github.io/teaching/#devops)

## Features

- **DataFrame creation**
  - From typed arrays or lists
  - From CSV files (automatic type inference based on first line)

- **Data display**
  - Full display (`toString`)
  - Head display (`displayFirstLines`)
  - Tail display (`displayLastLines`)

- **Selection mechanisms**
  - Select rows by index (`selectRows`)
  - Select row ranges (`selectRowsRange`)
  - Select columns by label (`selectColumns`)
  - Conditional filtering with predicates (`filterRows`)

- **Statistics**
  - Mean, Median, Std, Min, Max for numerical columns (`describe` method)

## Tech Stack

| Domain             | Tool                   |
|--------------------|------------------------|
| Language           | Java 11                |
| Build              | Maven                  |
| Testing            | JUnit                  |
| Code Coverage      | JaCoCo                 |
| Coverage Reporting | Codecov                |
| Version Control    | Git (GitHub)           |
| CI/CD              | GitHub Actions         |
| Containerization   | Docker                 |
| Image Hosting      | Docker Hub             |
| Docs Deployment    | GitHub Pages (Javadoc) |

## Git Workflow

We used a strict Git feature branch workflow:
- All contributions are made through **feature branches**
- Each branch is integrated via **Pull Requests**
- Reviews are mandatory for merging
- The CI pipeline runs on every PR to validate build and tests

## Continuous Integration / Delivery

Our GitHub Actions pipeline performs:
- Build and unit tests (`mvn verify`)
- Coverage report generation with JaCoCo
- Upload and analysis of test coverage with [Codecov](https://codecov.io/gh/hugorouillard/java-dataframe)
- Javadoc generation and publishing to [GitHub Pages](https://hugorouillard.github.io/java-dataframe/)
- Maven package publishing
- Docker image publishing and deployment to [Docker Hub](https://hub.docker.com/r/hugorouillard/java-dataframe-demo), including a demonstration scenario

## Docker

A Docker image is automatically built and published to [Docker Hub](https://hub.docker.com/r/hugorouillard/java-dataframe-demo) via GitHub Actions.

It contains a demonstration scenario using real League of Legends champion statistics.

### Build Locally

```bash
docker build -t java-dataframe-demo .
docker run --rm java-dataframe-demo
```

### Or pull from Docker Hub
```
docker pull hugorouillard/java-dataframe-demo
docker run --rm hugorouillard/java-dataframe-demo
```

## Feedback

This project allowed us to:
- Practice CI/CD automation in a real setting
- Understand Maven project structure and plugins
- Learn Docker, especially for demonstration and delivery
- Gain confidence in collaboration workflows (PRs, reviews)
- Appreciate the value of documentation (Javadoc, CI badges)

Challenges we faced:
- Debugging GitHub Actions and Docker publishing flows
- Picking tools, especially for CI/CD because there are so many available options.
