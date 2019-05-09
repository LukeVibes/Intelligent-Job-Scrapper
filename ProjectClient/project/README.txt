Welcome to Skill Mapper

Authors: Sahaj Arora (100961220), Luke Daschko (100976007), Jennifer Franklin (100315764), 

Project: Ionic Project, intended to run as a Browser app.

-----------------------------------
CONFIGURATION INSTRUCTIONS
-----------------------------------

1. In the ProjectClient/project/src/providers/api-service/api-service.ts on line 15 the port that the server is running on may be changed
	apiUrl = "http://localhost:8080/COMP4601-Project/rest/project"

-----------------------------------
LAUNCH INSTRUCTIONS
-----------------------------------

1. Make sure Node is installed on your system. This app has been tested on Node version v8.9.4.
2. Make sure ionic and cordova are installed on your system. It can be installed by running the command "npm install -g ionic cordova".
3. In the terminal, navigate to the root of this project (/ProjectClient/project), and run the following command to download the required modules: "npm install".
4. In the terminal, navigate to the root of this project (/ProjectClient/project), and run the following command to launch this app in a browser: "ionic cordova run browser".
   This command launches a static file server at a URL like "http://localhost:8003/index.html". Your URL might be different than this. You can check the URL in the output within the terminal.
   Look for the line that reads "Static file server running @ ...".
   If the app did not automatically open up in a browser, then open a browser, enter the static file server URL (obtained from the output in terminal) and hit Enter.

-----------------------------------
USAGE
-----------------------------------

1. Select a skill.
2. Click on "Find Relevant Skills" button.
3. View the related skills and their relevance in the Chart that is presented.