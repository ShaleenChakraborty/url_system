
PS C:\Users\Shaleen\Downloads\url_shortener\shaleen> git rm --cached -r logs
rm 'logs/url-shortener.log.2026-07-01.0.gz'
PS C:\Users\Shaleen\Downloads\url_shortener\shaleen> git add .gitignore
warning: in the working copy of '.gitignore', LF will be replaced by CRLF the next time Git touches it
PS C:\Users\Shaleen\Downloads\url_shortener\shaleen> git status
On branch main

No commits yet

Changes to be committed:
(use "git rm --cached <file>..." to unstage)
new file:   .gitattributes
new file:   .gitignore
new file:   .mvn/wrapper/maven-wrapper.properties
new file:   mvnw
new file:   mvnw.cmd
new file:   pom.xml
new file:   src/main/java/com/urlshortener/shaleen/ShaleenApplication.java
new file:   src/main/java/com/urlshortener/shaleen/config/OpenApiConfig.java
new file:   src/main/java/com/urlshortener/shaleen/config/RedisConfig.java
new file:   src/main/java/com/urlshortener/shaleen/controller/UrlController.java      
new file:   src/main/java/com/urlshortener/shaleen/dto/AnalyticsResponse.java
new file:   src/main/java/com/urlshortener/shaleen/dto/ErrorResponse.java
new file:   src/main/java/com/urlshortener/shaleen/dto/UrlRequest.java
new file:   src/main/java/com/urlshortener/shaleen/dto/UrlResponse.java
new file:   src/main/java/com/urlshortener/shaleen/entity/Url.java
new file:   src/main/java/com/urlshortener/shaleen/exception/GlobalExceptionHandler.java
new file:   src/main/java/com/urlshortener/shaleen/exception/ShortUrlNotFoundException.java
new file:   src/main/java/com/urlshortener/shaleen/repository/UrlRepository.java      
new file:   src/main/java/com/urlshortener/shaleen/service/UrlService.java
new file:   src/main/java/com/urlshortener/shaleen/service/UrlService_structure.puml  
new file:   src/main/resources/application.properties

PS C:\Users\Shaleen\Downloads\url_shortener\shaleen> 
