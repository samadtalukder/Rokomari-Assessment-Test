# Rokomari Assessment Test #
The repository has contains the Rokomari Live Test, where an Android project is created and implement their features which were mentioned in the document.

Here is the screenshots of the application:
<img src="https://github.com/samadtalukder/Rokomari-Assessment-Test/blob/master/requirement/01.login_screen.jpg" alt="" data-canonical-src="https://github.com/samadtalukder/Rokomari-Assessment-Test/blob/master/requirement/01.login_screen.jpg" width="200" height="400" />  
<img src="https://github.com/samadtalukder/Rokomari-Assessment-Test/blob/master/requirement/02.sign_up_screen.jpg" alt="" data-canonical-src="https://github.com/samadtalukder/Rokomari-Assessment-Test/blob/master/requirement/02.sign_up_screen.jpg" width="200" height="400" />  
<img src="https://github.com/samadtalukder/Rokomari-Assessment-Test/blob/master/requirement/03.book_home_screen.jpg" alt="" data-canonical-src="https://github.com/samadtalukder/Rokomari-Assessment-Test/blob/master/requirement/03.book_home_screen.jpg" width="200" height="400" />  
<img src="https://github.com/samadtalukder/Rokomari-Assessment-Test/blob/master/requirement/04.book_details_screen.jpg" alt="" data-canonical-src="https://github.com/samadtalukder/Rokomari-Assessment-Test/blob/master/requirement/04.book_details_screen.jpg" width="200" height="400" />  

## Application Flow

1. At first login screen will be appeared
2. If you haven't an account you must be create an account or you have an account you put your username and password if login success then Home Page screen will be appeared.
3. Every successfully login user get an access token. 
4. In Home screen two type category books will show.
5. Clicking on book item user will see the details of the books.
5. In the book details screen you will see the purchase button. While you clicked it you purchase this books if you have enough balance.
  


## Technical Description

Here is the list of technologies are used to build this application:

1. <b>Kotlin</b>
2. <b>MVVM Architecture</b>
3. <b> Retrofit, OkHttp</b> : To fetch their books data from API, I have used the network library which is Retrofit.
4. <b>Kotlin Coroutine</b> : To reduce the main thread task we can divide the task in many thread asychronously using the Kotlin Coroutine using `lifecycle` scope. 
5. <b>Jetpack Component - Pagging 3</b> : Why pagging is required this application, because the book list has a massive amount of data and If I wanted to fetch these data at a single time it will take huge amount of time. So that pagination is required, the jetpack component which is Pagging3 is one of the most used jetpack component to perform the paginated data in the `RecyclerView`. It also better with Kotlin Coroutine and Live Data. For that need to create Pagging adapter where we can detect the Success and Error state.
