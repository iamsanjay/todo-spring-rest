# todo-spring-rest
Basically I wrote this app to augment my knowledge of Spring which includes multiple libraries such as Spring Data, Spring Web and Spring Security. As of now, you already have an idea what this app is about? As the name says, It's a TODO app. Currently, there is no frontend; only backend is implemented and that is exposed via rest services. In following table, you will see all the relavant endpoints.

|Method|URL|Description|
|------|---|-----------|
|GET|/api/v1/todos/| To get the list of all Todos|
|PUT|/api/v1/todos/{ID}| To update a TODO|
|Post|/api/v1/todos/| To create a TODO|
|DELETE|/api/v1/todos/{ID}| To delete a TODO|

## Best naming practice 
So I gave an interview for some company and they have asked me to implement some app in Spring. I build it and they have rejected me. I know it's a sad story. Anyways lots of takeaways from that interview and one of them was issue with naming the endpoints.

What I delivered.

|Method|URL|Description|
|------|---|-----------|
|GET|/api/v1/get_todos/| To get the list of all Todos|

Have you seen any issue with above naming? 
First of all, let's understand what is verb? *Verb are the word that shows an action*
In my case, I have HTTP method GET which is verb in my case and it says that this endpoint will fetch me something, right? But what I did next is that I named endpoints "get_todos" and that I believe creates "duplicacy". And then I read on internet somewhere I will add the linnk at the bottom that always use plural noun to name your endpoints and HTTP method to represent the action.

So What was expected?
|Method|URL|Description|
|------|---|-----------|
|GET|/api/v1/todos/| To get the list of all Todos|

## Beautiful UI to get info about those endpoints (Swagger UI)
Now I was only searching for best naming conventions but then this article also talked about configuring swagger ui for your rest services. People who do not understand swagger like me :) it is...hmmm what is Swagger? Let's see what internet says. Also whenever you see something in italic in this article that mean I copied it from internet.

*Swagger UI allows anyone — be it your development team or your end consumers — to visualize and interact with the API’s resources without having any of the implementation logic in place. It’s automatically generated from your OpenAPI (formerly known as Swagger) Specification, with the visual documentation making it easy for back end implementation and client side consumption.*

So basically let's say you have hundred endpoints, now that is too many I do not think that is a good idea to have so many in one class. How about we have something in double digit. Now If some other vendor, company, humun or machine, needs access to those endpoints. Then your manager will email you to create documentation for those endpoints and you hate it -- I know. It's booooooring. And then you have to cancel your evening plan and write this documentation. As you are writing that documentation, even you are exploring some new endpoints. You are like, Woaaah! Our app can do this as well. Though you are the one who implemented it 6 months ago.


How about there is some spell who do this automatically and expose it via one of the endpoints? WOW is this possible. Yes it is. Whaaaaaat. Wait! Now it's time for background music. 

La La La Lala
La La La Lala

Reader come back. Focus on the task at hand, which I am not really sure what is. Anyways through Swagger UI, we can expose one endpoints which will render info regarding the endpoints. 
One of the best benefits that I see is that now you do not even have to update the documentation as the software changes (Actually not true). Swagger library, I believe they have changed the name -- I like Swagger more, wil read our endpoints and then render that info on UI. But description of endpoints given by developer by using one of the annotation. So that you may need to update if something about that API changed because that's purely documentation, nothing else.

To see Live demo, go to https://swagger.io/tools/swagger-ui/ and click Live demo. I would have attach the Live demo URL directly, It's just Live demo has weird URL like generated URL and I do not want that if people start visting this repo and they find corrupted links. That will be disappointing. 

I think enough text for Swagger.

## References

Best Practices for Rest API https://codeburst.io/spring-boot-rest-microservices-best-practices-2a6e50797115




