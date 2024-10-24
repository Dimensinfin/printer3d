# RUN ACCEPTANCE TESTS VALIDATED
# Run all validated tests to check that dependency chnages do not break the application

# - Define environment
nvm use printer3d
npm install

# - Run tests
npm run acceptance:cypress:D01
npm run acceptance:cypress:D02
npm run acceptance:cypress:D04
npm run acceptance:cypress:D05
npm run acceptance:cypress:D09
npm run acceptance:cypress:D11
npm run acceptance:cypress:D12
