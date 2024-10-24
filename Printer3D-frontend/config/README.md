# CONFIGURATION SCRIPTS
Configuration is done setting environment variables that a re used to be replaced on scritps placeholders that will then provide the runtime configuration for any environment. The source of the configuration is a set of 'export' variables that define the environment boundaries.

After running the environment setting we can then transform templates to deployment files, like the Kubernetes deployment or the application configurtion.

There is a configuration file for each of the environments:
* **local** - when running the environment on local and using mocks to test during development.
* **develop** - the configuration for pre-production deployements that is used for quality testing.
* **production** - the production configuration.
