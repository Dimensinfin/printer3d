# STEPS TO FOLLOW ON DEVELOPMENT.
1. Write the specification on the OpenApi documentation file.
2. Validate the OpenApi specification
3. Create on or more scenarios on the acceptance test feature file.
4. Create one Trello card for each new scenario to iterate
4. Compile the scenario. If there are steps not valid create the code for each them
5. After the step creation commit the code using the step as the documentation.
6. Check the new endpoint first starting the application in Debug mode
6. Add the endpoint to the list of registered endpoints at Postman
7. Save the Postman configuration
8. Create the application Docker container and start it
8. Record the Acceptance test to run.
8. Run the **RunAcceptanceTests** configuration.
9. Commit the code with the scenario title.
10. Do steps 4 through 12 for each new scenario
10. Push development brach to origin
10. Change to the **test-backend** branch to start doing tests
10. Run all the tests with the **Printer3D-backend [unit test]** configuration.
10. Fix all defects.
10. Run unit tests with code coverage.
11. Generate code coverage report.
12. Line level should be > 80%
13. Remove most sonar defects.
14. Commit changes under the **test** branch.
15. Validate the CI configuration before uploading the commits to origin.
