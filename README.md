This is a View defined by myself, which is a ProgressBar look like a ball filled with water. 
you can use it like this:
```
<com.demo.sisyphus.myapplication.MyLoader
        android:id="@+id/my_loader"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:color="#2a70e9" //color of water, default is blue
        app:defalt_level="400" //defalt level of water, default is radius/2
        app:radius="300" //radius of ball, default is 50
        app:text_size="15pt" //size of text, default is radius/4
        app:text_color="#DDDDDD" //color of text, default is gray
        app:text="66%"/> //content of text
 ```
