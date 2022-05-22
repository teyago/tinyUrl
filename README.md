# Tiny Url service

---
**How to install and run:**

* git clone https://github.com/teyago/tinyUrl
* go to src\main\resources
* configure application.properties

**cmd:**

* mvn spring-boot:run

---
**How to use:**

**Postman**:

_POST_

    http://localhost:8080/tinyurl/generate

JSON body:

    {

        "url": "https://www.google.com/",
    
        "expirationDate": "2022-10-10 10:10"

    }

will return a short link (alias) in the response

"expirationDate" is optional, by default expiration date is current time + 10 minutes

format is "yyyy-MM-dd HH:mm"

_GET_

    http://localhost:8080/tinyurl/alias*

will redirect you to the original url

_GET_

    http://localhost:8080/tinyurl/alias*/info/

will give you info about url such as: number of click, original url, alias, creation and expiration dates

_DELETE_

    http://localhost:8080/tinyurl/delete

JSON body:

    {
    
        "alias": alias*
    
    }

*alias from generate, for example: "454aa203"

---
Also, every 10 second the program checks for expired URLs due the scheduler