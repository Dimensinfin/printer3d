# STEPS TO FOLLOW ON DEVELOPMENT.
1. Write the specification on the OpenApi documentation file.
2. Validate the OpenApi specification
3. Create on or more scenarios on the acceptance test feature file.
4. Compile the scenario. If there are steps not valid create the code for each them
5. After the step creation commit the code using the step as the documentation.
6. Run the **RunAcceptanceTests** configuration. If it completes OK.
7. Commit the code with the scenario title.
8. Run all the tests with the **Printer3D-backend [unit test]** configuration.
9. Fix all defects.
10. Run unit tests with code coverage.
11. Generate code coverage report.
12. Line level should be > 80%
13. Remove most sonar defects.
14. Commit changes under the **test** branch.
