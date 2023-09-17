# Player Version Checker Plugin

## Overview

The Player Version Checker Plugin is designed for Minecraft servers to ensure that players are using an up-to-date client version. If a player connects with an outdated client, they will receive alerts prompting them to update. These alerts will persist until the player updates their client or chooses to dismiss the messages.

## Features

- **Version Check**: The plugin checks the protocol version of the connecting client against a list of allowed versions.
- **Persistent Alerts**: Players with outdated clients will receive continuous alerts until they take action.
- **User Control**: Players have the option to dismiss the alert messages if they choose not to update immediately.
- **Customizable Messages**: Server administrators can customize the alert messages through the plugin's configuration.

## Installation

1. First, build the project from source (see the "Build" section for instructions).
2. Once built, place the compiled `.jar` file into your server's `plugins` directory.
3. Restart your server.
4. Configure the plugin as needed by editing the generated configuration file.

## Build

If you wish to build the plugin from source, follow these steps:

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Run the following command to build the plugin using Gradle:
```gradle
build gradle
```
4. Upon successful build, the compiled `.jar` file can be found in the `build/libs` directory.

**Note**: This project does not use the `shadowJar` plugin, so a standard Gradle build is sufficient.

## Usage

Players with outdated clients will automatically receive alert messages upon connecting to the server. They can choose to update their client or click on the provided message to dismiss the alerts.

## Support

For any issues, questions, or feature requests, please refer to the plugin's GitHub repository or contact the plugin's developer.
