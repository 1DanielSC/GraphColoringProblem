# Minimum Graph Coloring Problem

![GitHub last commit](https://img.shields.io/github/last-commit/1danielsc/GraphColoringProblem)  ![GitHub issues](https://img.shields.io/bitbucket/issues-raw/1danielsc/GraphColoingProblem)  ![Programming language](https://img.shields.io/github/languages/top/1danielsc/GraphColoringProblem)

> Status: Developing ⚠️


## Table of Contents

  - [Description](#1-description)
  - [Requirements](#2-requirements)
  - [Compilation](#3-compilation)
  - [How to run it](#4-how-to-run-it)
  - [Roadmap](#5-roadmap)
  - [License](#6-license)


## 1. Description

The Minimum Graph Coloring is a well-known NP-Hard combinatorial optimization problem that consists on assigning the least number of different colors to the vertices of a graph such that every two adjacent vertices are colored with distinct colors. 

This project aims to find the chromatic number of an undirected graph.

Three heuristic algorithms were implemented, one based on Simulated Annealing metaheuristic, along with two exact algorithms.

The DIMACS graphs are used as instance files. Available [here](https://mat.tepper.cmu.edu/COLOR04/INSTANCES/) and [here](https://unsat.github.io/npbench/graphcoloring.html).

## 2. Requirements
You should have [Java JDK 11](https://www.oracle.com/java/technologies/downloads/#java11) installed on your computer.


## 3. Compilation



You should open the command line and and then open the directory on which you extracted the project.

Being on NP-Hard directory, you should execute the following code on the command line:

- cd src
- cd Classes
- javac *.java

## 4. How to run it

Access the directory _src_ through the command line and then type _java Classes.Application_ and execute it.

### 4.1 Instance File

As soon as you run the program, you'll be asked to type the name of a instance file to compute its chromatic number.

You should write the file name along with ".txt" at the end.

>Example: david.txt

### 4.2 Running exact algorithms

You'll also be asked whether you'd like or not to run the Brute Force and Backtracking exact algorithms.

For those you'd like to run, it is enough to type "yes".

### 4.3 Setting up Simulated Annealing parameters

Before running the heuristic based on Simulated Annealing, you'll be asked to set the initial temperature (T_0), the iterations per temperature (L) and the cooling rate (alpha).

## 5. Roadmap

Features to be added and corrected in the future:

- Change the random neighbor method of Simulated Annealing as it has not improved the solution quality.


## 6. License

[![MIT license](https://img.shields.io/badge/License-MIT-blue.svg)](https://lbesson.mit-license.org/)

[MIT licensed.]("https://github.com/1DanielSC/BinarySearchTree/blob/main/LICENSE")