# MovieMemoir App
## Server
使用NetBeans搭建[服务端](https://github.com/xxxape/MovieMemoir/tree/master/MovieMemoir)</br>
包括数据的[存储](https://github.com/xxxape/MovieMemoir/tree/master/MovieMemoir/src/java/restws)和[增删改查](https://github.com/xxxape/MovieMemoir/tree/master/MovieMemoir/src/java/service)</br>
<img src="https://raw.githubusercontent.com/xxxape/MovieMemoir/master/img-folder/databse.png" width="400"/>

## Client
使用Android Studio搭建[客户端](https://github.com/xxxape/MovieMemoir/tree/master/MyMovieMemoir)</br>
#### 功能
1. 登录</br>
>[SigninActivity](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/java/com/zzx/mymoviememoir/MainActivity.java)</br>
>[界面](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/res/layout/activity_main.xml)</br>
>应用启动界面，用户通过界面输入用户名和密码，Activity获取界面输入，将用户名和加密后的密码一同发送到服务端，服务端查询用户名和密码是否存在，如果存在返回凭证id（登陆成功），否则返回空。客户端如果接收到凭证id，会再次将凭证id发送到服务端去获取用户所有信息（姓名等），并将当前用户信息保存在静态类中，以便其它组件获取。
2. 注册</br>
>[SignupActivity](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/java/com/zzx/mymoviememoir/user/SignUpActivity.java)</br>
>[界面](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/res/layout/activity_sign_up.xml)</br>
>用户输入用户名（邮箱），密码等一系列个人信息，Activity获取界面输入，并将其打包成Person类，用gson对象将其转换为Json字符串，再用RequestBody类将该Json字符串创建为主体，在创建请求时将该主体传入到post()方法中，再将该请求发送到服务器（发送两次，先添加到Credential表，再添加到Person表），如果服务端返回204代码，表示插入数据库成功，关闭当前Activity，返回到登录页面。</br>
>![登录](https://raw.githubusercontent.com/xxxape/MovieMemoir/master/img-folder/signin.png) 
>![注册](https://raw.githubusercontent.com/xxxape/MovieMemoir/master/img-folder/signup.png)
3. 主页</br>
>主页显示当前用户评分前5的电影列表。通过用户id到服务端查询该用户评分top5的电影，返回一系列电影信息，通过ListView+SimpleAdapter展示该数据。</br>
>使用[Fragment](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/res/layout/activity_main.xml)切换不同页面</br>
>[HomeFragment](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/java/com/zzx/mymoviememoir/fragments/HomeFragment.java)</br>
>[界面](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/res/layout/fragment_home.xml)</br>
>通过点击侧边导航栏切换Fragment，Fragment切换方法：
```Java
FragmentManager fragmentManager = getSupportFragmentManager();
FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
fragmentTransaction.replace(R.id.content_frame, nextFragment);
fragmentTransaction.commit();
```
>![主页](https://raw.githubusercontent.com/xxxape/MovieMemoir/master/img-folder/homepage.png)
>![导航栏](https://raw.githubusercontent.com/xxxape/MovieMemoir/master/img-folder/Navigation.png)
4. 搜索</br>
>输入电影名，通过TMDb API搜索电影并返回电影列表，使用RecyclerView和Adapter展示数据，点击某一条电影行，可以跳转到电影详情界面</br>
>[MovieSearchFragment](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/java/com/zzx/mymoviememoir/fragments/MovieSearchFragment.java)</br>
>[界面](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/res/layout/fragment_movie_search.xml)</br>
5. 电影详情</br>
>显示选中电影的详情，包括电影名、海报、上映时间、地区以及演职员，通过电影id向TMDb API发送请求获取以上具体信息，并一一展示在界面上（演职员表通过ListView+SimpleAdapter展示）。</br>
>>问题：点击添加到心愿单按钮时，需要先在SQLite数据库中查找该电影是否存在，如果存在，提示已添加，并使按钮变灰；如果不存在，则添加。使用原本代码时由于不熟悉Runnable的使用，导致不知道在哪里获取分线程任务结束后查询到的数据。</br>
>>当时解决办法：将数据库操作中的Runnable实现方法全部删除，使用AsyncTask替代。</br>
>>其他解决办法：使用Handler+Message的消息机制，当数据库操作的异步任务结束时，通过Handler发送消息，在Handler类中重写handleMessage方法，在主线程中处理接收到的消息并进行ui操作。</br>
>[MovieViewActivity](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/java/com/zzx/mymoviememoir/movie/MovieView.java)</br>
>[界面](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/res/layout/activity_movie_view.xml)</br>
>![搜索电影](https://raw.githubusercontent.com/xxxape/MovieMemoir/master/img-folder/searchPage.png)
>![电影详情](https://raw.githubusercontent.com/xxxape/MovieMemoir/master/img-folder/movieView.png)
6. 收藏列表
>获取SQLite数据库中心愿单数据，并通过RecyclerView+Adapter展示数据</br>
>[WatchlistFragment](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/java/com/zzx/mymoviememoir/fragments/WatchlistFragment.java)</br>
>[界面](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/res/layout/fragment_watchlist.xml)
7. 添加影评
>输入影评相关信息（观影时间、地点、评论、打分），如果post请求返回204，表示添加成功</br>
>[MemoirAddActivity](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/java/com/zzx/mymoviememoir/memoir/MemoirAddActivity.java)</br>
>[界面](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/res/layout/activity_memoir_add.xml)</br>
>![心愿单](https://raw.githubusercontent.com/xxxape/MovieMemoir/master/img-folder/watchlist.png)
>![添加影评](https://raw.githubusercontent.com/xxxape/MovieMemoir/master/img-folder/addToMemoir.png)
8. 影评记录
>RecyclerView+Adapter展现影评记录，有筛选和排序功能</br>
>>排序：重写Comparator<>中的compare方法，并传入Collections.sort方法中</br>
>>筛选：按条件筛选出新的List对象并传入适配器中</br>
>[MovieMemoirFragment](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/java/com/zzx/mymoviememoir/fragments/MovieMemoirFragment.java)</br>
>[界面](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/res/layout/fragment_movie_memoir.xml)
9. 数据统计
>[ReportFragment](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/java/com/zzx/mymoviememoir/fragments/ReportFragment.java)</br>
>[界面](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/res/layout/fragment_report.xml)
10. 地图界面
>[MapFragment](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/java/com/zzx/mymoviememoir/fragments/MapFragment.java)</br>
>[界面](https://github.com/xxxape/MovieMemoir/blob/master/MyMovieMemoir/app/src/main/res/layout/fragment_map.xml)</br>
>![影评](https://raw.githubusercontent.com/xxxape/MovieMemoir/master/img-folder/memoirPage.png)
>![地图](https://raw.githubusercontent.com/xxxape/MovieMemoir/master/img-folder/map.png)

## API
[The Movie Database (TMDb)](https://developers.themoviedb.org/3/getting-started/introduction)</br>
[Google Maps](https://developers.google.com/maps/documentation/android-sdk/overview)
