# Server Setup

To create an engine and levels on Sonolus, a server must first be created to host level files.
This guide will detail how to set up a local web server which will host created levels.

## Setting up a local server

### Windows
#### IIS Setup
This step describes how to set up Internet Information Services (IIS),
which comes included in Windows installations, though generally not enabled by default.
Note that administrator privileges will needed for this step and the next. 

1. Open Control Panel.
2. Under "Programs", select "Turn Windows features on or off". A prompt should appear.
3. Find "Internet Information Services" and check the checkbox.
4. Click OK to dismiss the prompt and allow features to install. Reboot if prompted.
5. In a browser navigate to [http://localhost](http://localhost) and ensure that a webpage opens.

#### Sonolus Server Setup
1. IIS creates a directory `C:\inetpub\wwwroot` by default, where hosted files are stored.
2. To start, the Sonolus server files may be downloaded from [https://github.com/NonSpicyBurrito/sonolus-wiki/releases.](https://github.com/NonSpicyBurrito/sonolus-wiki/releases.)
Download `server.zip` under assets from the latest version.
3. Create a subdirectory under `C:\inetpub\wwwroot` and name it sonolus.
4. Extract the contents of `server.zip` into the directory created in step 3.
5. Open the command prompt, either by searching `cmd` in the start menu or
opening the run menu with ++win+r++ and typing in `cmd`.
6. Type in `ipconfig` and hit enter. Find the IPv4 address under Ethernet or Wireless LAN
depending on which type of connection is in use and keep note of it.
7. Open sonolus on a mobile device and ensure that it is connected to the same network as the server.
Add a server with address `http://[IP from step 6]/sonolus` and connect to it.
Ensure both example levels are playable.