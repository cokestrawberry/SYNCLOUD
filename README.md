# Syncloud
Repository for Syncloud

<hr/>

## Frontend

Frontend is divided into 3 parts, codes / styles / scripts

### codes
  - html codes
  - index : landing page & workspace
  - search : search soundtrack
  - upload : pop-up page for uploading own soundtrack
  - note : see additional informations for each soundtrack

### styles
  - css codes for html codes
  - it includes share(global) / index / search / upload / note

### scripts
  - for pop-up window action.

<hr/>

## Backend

[ 작성 환경 MacOS ventura 13.5.1 , Spring 3.1.3 ver, IntelliJ IDEA ]

DATABASE : Mysql Database ( Mysql workbench 사용 권장 ~ )
application.yml 파일의 spring:datasource에서 url, username, password 변경하면 local에서 돌려볼 수 있다.

HTML file:
resource/template/ 폴더에 html 파일을,  
resource/static/css에 css파일,
js에 javascript 파일,
images에 favicon 파일을 넣고,
controller에서 url에 따라서 html파일 이름을 반환하면 동작을 구현할 수 있다.
(still on working)
