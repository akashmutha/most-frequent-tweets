# most-frequent-tweets
It shows Most Frequent tweets among last N number of Tweets Which contains a specific String (Text)


# Query
It uses a search string for checking this text in all the tweets and return specified number of tweets Like: last 100 or 200

# Flow
Splash Screen
For first time it fetches the content from twitter api so there will be a spinner while loading the content.
After first open, every time user opens the app It shows the previously fetched content and then refresh the content for better user experience.

# Refresh
There is a refresh FAB(Floating Action Button) on the main screen. Which refreshes the content in real-time.
It shows a toast Fetching the content, After fetching it says content is refreshed and update the view.

We can also put some discrimination like WhatsApp (Not Implemented in this version, only shows toast messages)
Ex: Background will be changed if it is not the latest content
  Then After refresh we will change the background colour to our main colour
  

