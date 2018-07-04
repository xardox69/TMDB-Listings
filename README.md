# TMDB Listings

A simple Android application which shows TV Shows and Movies listings from TMDB.


 This app stands on the principles of [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) 
 
 
 It's based on the **MVP**  adding a domain layer between the presentation layer and repositories,
 splitting the app in three layers:
 
 **MVP:** Model View Presenter pattern.
 
 
 **Domain:** Holds all business logic. The domain layer starts with classes named use cases used by the application
 presenters. These use cases represent all the possible actions a developer can perform from the presentation layer.
  
 
 **Repository:** Repository pattern.
 
  ![Image](https://raw.githubusercontent.com/wiki/googlesamples/android-architecture/images/mvp-clean.png)
 
 The application consists of four UI screen:
 
 
 **Movies Screen**
 
 
 Shows the movies listings. `MoviesFragment.java` is responsible for showing movies listings.
 `com.dubizzle.app.movies` package contains classes used to show movies.
 
 
 **Tv Shows Screen** 
 
 Shows the Tv Shows listings. `TvShowsFragment.java` is responsible for showing movies listings.
 `com.dubizzle.app.tvshows` package contains classes used to show TV Shows.
 
 **Detail Screen**
 
 Shows details of the selected item. `DetailActivity.java` is responsible for showing details of the screen.
 'com.dubizzle.app.details' package contains all the related classes.
 
 **Filter Screen**
 
 Shows the filters for date. `FilterActivity.java` is responsible for showing the filter screen.
 The `filter` package contains the files related to this.
 
 **Libraries**
*  [Retrofit2](http://square.github.io/retrofit/) for Rest API communication.
*  [Glide](https://github.com/bumptech/glide) for image loading
*  [Mockito](http://site.mockito.org) for mocking in test 
 
 
 
 


# Unit Testing
Unit testing of all the presenter is done  as well as the instrumentation tests.
  ![Code Coverage](https://drive.google.com/open?id=1rWF4XBKuM1z2flrQPpv_6Ca7bvnacOj2)



# Feedback
For feedback and quering, please email at khalid.usman7@gmail.com.

