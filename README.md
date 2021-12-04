# Aqueduct

Aqueduct is a Java library for graph theory.

# Quick start
Before digging into deeper details, let's begin with a quick example that builds a graph and run a BFS on it.

# How to contribute
To contribute, just submit a pull request. The pull request should necessarily resolves an issue. Feel free to create an issue if your pull request does not solve an existing issue. Keep in mind that:
* The project uses [Qulice](https://www.qulice.com/) 0.18.19
* Pull requests has a [travis](https://github.com/HDouss/aqueduct/blob/master/.travis.yml) build check, and a coveralls test coverage check
* Coveralls check succeeds if coverage is at least 85%, and if the coverage does not drop from the last check by more than 5%
* If the two checks succeeds and code review comments (if any) are resolved, the pull request will be labeled by [`tomerge`](https://github.com/HDouss/aqueduct/labels/tomerge). This will trigger a GitHub [workflow](https://github.com/HDouss/aqueduct/blob/master/.github/workflows/merge-pr.yml)
* The pull request merging GitHub workflow will:
  * Checkout your branch
  * Merge it locally (inside the container running the workflow) with master branch
  * Perform a build (`mvn clean install`)
  * If the build succeeds the PR is merged into master branch
  * This guarantees that the master branch is always in a succeeding build state
