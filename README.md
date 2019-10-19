# การเขียน Spring-boot WebFlux + Nuxt.js 

### 1. ทำการสร้าง spring-boot project    
`pom.xml` add dependencies ดังต่อไปนี้ 
```xml
...
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-webflux</artifactId>
  </dependency>

  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactId>
  </dependency>
...
```

### 2. config main class 
สำหรับ start/run spring-boot 
```java
...
@SpringBootApplication
@ComponentScan(basePackages = "com.pamarin")
public class AppStarter {
    public static void main(String[] args) {
        SpringApplication.run(AppStarter.class, args);
    }
}
```

### 3. เขียน controller 
เนื่องจากเราจะใช้ Nuxt.js ใน mode SPA (Single Page Application)   
เราจะต้องเขียน router ให้รองรับกับ nuxt (vue) route ดังนี้
```java
@Controller
public class IndexController {

    @GetMapping({"", "/"})
    public Mono<String> index(final Model model) {
        return Mono.just("index");
    }

    @GetMapping({
        "/{path:[^\\.]*}", 
        "/{path1:^(?!oauth).*}/{path2:[^\\.]*}",
        "/{path1:[^\\.]*}/{path2:[^\\.]*}/{path3:[^\\.]*}",
        "/{path1:[^\\.]*}/{path2:[^\\.]*}/{path3:[^\\.]*}/{path4:[^\\.]*}",
        "/{path1:[^\\.]*}/{path2:[^\\.]*}/{path3:[^\\.]*}/{path4:[^\\.]*}/{path5:[^\\.]*}",
        "/{path1:[^\\.]*}/{path2:[^\\.]*}/{path3:[^\\.]*}/{path4:[^\\.]*}/{path5:[^\\.]*}/{path6:[^\\.]*}"
    })
    public Mono<String> forward(final Model model) {
        return index(model);
    }
}

```
### 4. สร้าง Nuxt project ที่ root maven project
run คำสั่งดังต่อไปนี้
```shell
$ npx create-nuxt-app nuxtjs
```
- nuxtjs คือ ชื่อ folder  
  
กรอกข้อมูลต่าง ๆ ลงไปดังนี้
  
- Project name ...ชื่อโปรเจ็คที่เราจะตั้ง
- Project description ...คำอธิบายโปรเจ็ค
- Author name ...ชื่อผู้แต่ง/ผู้เขียน
- Choose the package manager ...เลือก package manager ในที่นี้ผมจะเลือกใช้ `yarn` ครับ
- Choose UI framework ...เลือก UI framework ในที่นี้ผมจะเลือก `None` (เดี๋ยวค่อยหามาใส่เองทีหลัง)  
- Choose custom server framework ...เลือก server framework ในที่นี้ก็เลือก `None` เพราะ server เราใช้ spring-boot อยู่แล้ว  
- Choose Nuxt.js modules ...เลือก Nuxt.js modules ในที่นี้ผมเลือกเป็น `Axios` ครับ
- Choose linting tools ...เลือก Linting tools ผมเลือกใช้ `ESLint`   
- Choose test framework ...เลือก test framework ผมเลือกใช้ `Jest`  
- Choose rendering mode ...เลือก rendering mode ผมเลือกเป็น `Single Page Application`   
- Choose development tools ...เลือก development tools ตอนนี้มีให้เลือกอันเดียวคือใช้ `jsconfig.json`  
  
จากนั้นรอให้ yarn install package ต่างๆ ให้ (อย่าลืมต่อ internet น่ะ)  

### 5. ลบไฟล์ .git ใน folder nuxtjs ออก
ให้ไปใช้ .git ที่ root maven project แทน

### 6. ย้าย .gitignore ใน folder nuxtjs 
ไปไว้ที่ root mavn project จากนั้น แก้ทุก ignore ให้เติม prefix `nuxtjs/`  
```
nuxtjs/logs
nuxtjs/*.log
nuxtjs/npm-debug.log*
nuxtjs/yarn-debug.log*
nuxtjs/yarn-error.log*
...
...
```
จากนั้นเพิ่ม ignore ดังต่อไปนี้เข้าไป  
```
target/
src/main/resources/static/  
```


### 7. แก้ไฟล์ nuxtjs/nuxt.config.js  
แก้ dist path ให้ชี้ไปที่ `../src/main/resources/static`
```
export default {
  mode: 'spa',
  generate: {
    dir: '../src/main/resources/static'
  },
  ...
  ...
```

### 8. ลอง build nuxt.js    
```
$ cd nuxtjs
yarn build  
```
จะพบว่ามีการสร้าง index.html ไว้ที่ `src/main/resources/static`

### 9. ลอง run spring-boot 
```
$ mvn spring-boot:run ที่ root maven project 
```
ก็เป็นอันเสร็จเรียบร้อย  

# Note

จะต้อง install node.js และ yarn ก่อนน่ะ    
node.js สามารถ download ได้ที่ [https://nodejs.org/en/download/](https://nodejs.org/en/download/)  
ส่วน yarn สามารถ install ผ่าน npm ได้ดังนี้

```
$ npm install yarn -g 
```

กรณีถ้ามี package บางตัว require python สามารถ download ได้จาก [https://www.python.org/downloads/](https://www.python.org/downloads/)  
ลองสังเกต version ว่าเป็น version อะไร  ปกติจะเป็น `Python 2.7.16`  


### การใช้งาน yarn

ใช้สำหรับ dev [http://localhost:3000](http://localhost:3000)
```
$ cd nuxtjs
$ yarn dev
```

ใช้สำหรับ build production
```
$ cd nuxtjs
$ yarn build  
```

ใช้สำหรับ test
```
$ yarn test  
```









