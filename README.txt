Welcome to Skill Mapper

Authors: Sahaj Arora (100961220), Luke Daschko (100976007), Jennifer Franklin (100315764), 

-----------------------------------
CONFIGURATION INSTRUCTIONS
-----------------------------------

1. Start your Mongo database
2. Create a database named project.
3. Using Studio 3T import three database collections using the import feature:
	3.1 equivalence collection using the file project.equivalence.csv
	3.2 relatedSkills collection using the file project.relatedSkillsFull.csv
	3.3 skills collection using the file project.skillsCorrected.csv
4. This is a maven project that is also a dynamic web module project configure Eclipse accordingly
5. In the ProjectClient/project/src/providers/api-service/api-service.ts on line 15 the port that the server is running on may be changed
	apiUrl = "http://localhost:8080/COMP4601-Project/rest/project"


-----------------------------------
SERVER INSTRUCTIONS
-----------------------------------

1. Right click your project then choose Maven -> Update Project before running the project
2. From the Web perspective click on the Servers tab, select Tomcat v7.0 and right click. Choose the add and remove... menu option. Be certain that the COMP4601-Project is added to the server.
3. Start the server

-----------------------------------
WEB CLIENT LAUNCH INSTRUCTIONS
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

1. From within the web client select a skill.
2. Click on "Find Relevant Skills (Database)" button.
3. View the related skills and their relevance in the Chart that is presented.