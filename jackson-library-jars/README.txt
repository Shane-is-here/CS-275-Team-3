Instructions for creating the proper libraries in NetBeans for the middleware:

1: Download these three jar files, save them to a folder that will not be moving (i.e., a folder in the NetBeans project)
2: When you go to open the project in NetBeans, you will get a pop-up that libraries are missing. Click "Resolve Issues".
3: Click on the error that is saying the library "JSON" is missing.
4: Create a new library called "JSON", and then click "Add JAR files/folder"
5: Select the 3 .jar files that you downloaded, and add them to the library.
6: Repeat steps 3-5 for the missing library "Jackson", making sure to name that libary "Jackson"

After completing these steps, the errors should be gone for good. If not, contact Brenden.
